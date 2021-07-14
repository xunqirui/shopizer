# 第二周作业
## 作业描述
在 my-configuration 基础上，实现 ServletRequest 请求参 数的 ConfigSource（MicroProfile Config），提供参考：

Apache Commons Configuration 中的 org.apache.commons.configuration.web.ServletRequestC onfiguration。

## 完成情况
作业内容在 org.geektimes.configuration.microprofile.config.source.servlet.ServletRequestConfigSource 类中实现，因为 ServletRequest 获取 value 存在多值情况，所以通过 "," 将数据进行分割