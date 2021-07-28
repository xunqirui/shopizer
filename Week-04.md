# 第四周作业
将 my-interceptor 工程代码增加 JDK 动态代理，将 @BulkHead 等注解标注在接口上，实现方法拦截。

步骤： 
1. 通过 JDK 动态代理实现类似 InterceptorEnhancer 的代码

2. 实现 JDK 动态代理方法的 InvocationContext

## 完成情况
已完成

类似 InterceptorEnhancer 实现：org.geektimes.interceptor.proxy.JavaProxyInterceptorEnhancer

jdk 动态代理方法的 InvocationContext 实现：org.geektimes.interceptor.JavaProxyInvocationContext

测试类：
org.geektimes.interceptor.proxy.JavaProxyInterceptorEnhancerTest