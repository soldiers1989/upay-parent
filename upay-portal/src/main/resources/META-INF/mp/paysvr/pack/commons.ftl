<#assign timestamp="${.now?string('yyyyMMddHHmmss')}" >
<#macro xmlRoot>
<@XmlDeclareUtf8/>
<?xml version="1.0" encoding="UTF-8"?>
<epay>
<@Head/>
<#include "${transCode?trim}.ftl"/>
</epay>
</#macro>
<#macro Head>
<transCode>${transCode}</transCode>
<serviceVersion>1.0</serviceVersion>
<charset>UTF-8</charset>
<#if signType?? && signType != ''>
<signType>${signType}</signType>
</#if>
<#if sign?? && sign != ''>
<sign>${sign}</sign>
</#if>
<rspCode>${outCode}</rspCode>
<rspMsg>${outMsg}</rspMsg>
</#macro>