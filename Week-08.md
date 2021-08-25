# 第八周作业
利用 Reactor Mono API 配合 Reactive Streams Publisher 实现，让 Subscriber 实现能够获取到数据，可以参考一下代码：

```java
SimplePublisher publisher = new SimplePublisher(); Mono.from(publisher) .subscribe(new BusinessSubscriber(5));

for (int i = 0; i < 5; i++) {

publisher.publish(i);

}
```


## 完成情况说明
详情见 org.geektimes.reactive.reactor.MonoDemo 中的 demoMonoPublisher 方法，将原来的  Mono.from(publisher) 改成了  Mono.fromDirect(publisher)

通过断点调试源码发现，当使用 Mono.from 方法时，Subscriber 会被包装到 reactor.core.publisher.MonoNext.NextSubscriber 这个类中，并调用该类的 onNext 方法。

在该类的 onNext 方法中，会调用 Subscription 的 onCancel 方法和实际的 subscriber 的 onComplete 方法并将该包装类的 done 属性置为 true ，接下来再调用该包装类的 onNext 方法的时候，就不会去调用预实际 subscriber 的 onNext 方法了

具体源码如下：
```java
@Override
public void onNext(T t) {
    if (done) {
        Operators.onNextDropped(t, actual.currentContext());
        return;
    }
    
    //调用 subscription 的 cancel
    s.cancel();
    // 调用实际 subscriber 的 onNext 方法
    actual.onNext(t);
    // 内部会将 done 属性置为 true，默认为 false
    // 然后去调用 actual.onComplete 方法
    onComplete();
}
```

如果使用 Mono.fromDirect 方法时，原始 Subscriber 会被包装成 StrictSubscriber ，该方法中的 onNext 方法会直接调用原始的 subscriber ，
在判断是否调用 onComplete 的判断条件 WIP.decrementAndGet(this) != 0 中，WIP 在之前的判断中会置为 1，所以到该判断的时候会减 1 ，置为0，所以 if 条件一直都不会进入

具体源码如下：
```java
@Override
public void onNext(T t) {
    // WIP 默认是 0，所以 if 判断成立，WIP.compareAndSet 会将值置为 1
    // 为接下来的 if (WIP.decrementAndGet(this) != 0) 做准备
    if (WIP.get(this) == 0 && WIP.compareAndSet(this, 0, 1)) {
        actual.onNext(t);
        // 由于上面的 if 判断将 WIP 置为 1 了，所以在减了 1 之后
        // WIP 变为 0 ，改条件一直不成立
        if (WIP.decrementAndGet(this) != 0) {
            Throwable ex = Exceptions.terminate(ERROR, this);
            if (ex != null) {
                actual.onError(ex);
            } else {
                actual.onComplete();
            }
        }
    }
}
```