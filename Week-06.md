# 第六周作业
增加一个注解名为 @ConfigSources，使其能够关联多个 @ConfigSource，并且在 @ConfigSource 使用 Repeatable，可以对比参考 Spring 中 @PropertySources 与 @PropertySource，并且文字说明 Java 8 @Repeatable 实现 原理。

完成描述：
创建了 @ConfigSources 并进行了相关的测试，见 

org.geektimes.configuration.microprofile.config.annotation.ConfigSourcesTest 

org.geektimes.configuration.microprofile.config.annotation.RepeatableConfigSourceTest 

@Repeatable 说明：经过测试类查看其字节码，发现使用 @Repeatable 的注解，其实在编译之后会转换成 @Repeatable 中的 value 的模式，也就是作业中的 @ConfigSources 的方式，所以只是一个语法糖而已