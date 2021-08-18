# 第七周作业
描述 Spring 校验注解

org.springframework.validation.annotation.Validated 的⼯ 作原理，它与 Spring Validator 以及 JSR-303 Bean Validation @javax.validation.Valid 之间的关系

## 解答
@Validated 的工作原理

通过 org.springframework.validation.beanvalidation.MethodValidationPostProcessor 类，该类是 Spring 提供的基于方法的 JSR 的校验的核心处理器，该方法会创建一个 Advisor，里面包含了一个 Advice ，
在该类中有一个 createMethodValidationAdvice 方法，创建了用于校验使用的 Advice ，即 org.springframework.validation.beanvalidation.MethodValidationInterceptor，通过其来处理方法级别的参数校验。

@Validated 是 JSR-303 的一个扩展，它提供了分组的功能，而使用 @Valid 了话只能使用默认分组

目前研究的尚浅，只能写出这一些内容，后续有深入研究之后再做补充