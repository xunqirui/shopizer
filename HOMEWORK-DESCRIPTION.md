# 第六节作业
参考 com.salesmanager.shop.tags.CommonResponseHeadersT ag 实现⼀个⾃定义的 Tag，将 Hard Code 的 Header 名值 对，变为属性配置的⽅式，类似于：
```xml
<tag> 
    <name>common-response-headers</name> 
    <tagclass>com.salesmanager.shop.tags.CommonResponseH eadersTag</tag-class> 
    <body-content>empty</body-content>

    <attribute> 
        <name>Cache-Control</name> 
        <required>false</required> 
        <rtexprvalue>no-cache</rtexprvalue> 
        <type>java.lang.String</type> 
    </attribute> 
    <attribute> 
        <name>Pragma</name> 
        <required>false</required> 
        <rtexprvalue>no-cache</rtexprvalue> 
        <type>java.lang.String</type> 
    </attribute> 
    <attribute> 
        <name>Expires</name> 
        <required>false</required> 
        <rtexprvalue>-1</rtexprvalue> 
        <type>java.lang.Long</type> 
    </attribute> 
</tag>
```
1. 实现自定义标签
2. 编写自定义标签 tld 文件
3. 部署到 Servlet 应用


## 作业完成情况说明
在 shopizer-tags.tld 中增加了一个叫 active-headers 的 tag，
对应实现类为 com.salesmanager.shop.tags.ActiveHeadersTag，
在 catalogLayout.jsp 文件里增加了一个 <sm:active-headers test="123123"/>，通过属性方式动态注入值，在 ActiveHeadersTag 中放入到 responseHeader 里，不知道该方法是否满足作业要求