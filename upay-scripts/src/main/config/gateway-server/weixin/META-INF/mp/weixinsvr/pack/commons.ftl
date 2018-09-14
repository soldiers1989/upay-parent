<#macro xmlRoot>
<?xml version="1.0" encoding="UTF-8"?>
<xml>
  <#include "${transCode?trim}.ftl" />
</xml>
</#macro>

<#-- 对于非必输字段用此函数,如果值为空则不需要拼标签,用这个函数不用每个都写if,
用法${funTagView("标签名", "${值!''}")}
 -->
<#function funTagView tag value><#if value?? && "${value}" != ""><#return "<${tag}>${value}</${tag}>"></#if><#return ""></#function>

