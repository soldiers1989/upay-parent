<#assign timestamp="${.now?string('yyyyMMddHHmmss')}" >
<#macro xmlRoot>
    <@XmlDeclareUtf8/>
<epay>
    
        <#include "${tranCode?trim}.ftl"/>
    
</epay>
</#macro>

<#macro XmlDeclareUtf8>
<?xml version="1.0" encoding="UTF-8"?>
</#macro>

<#macro Head>
<head>
    <transCode>${transCode}</transCode>
    <serviceVersion>${serviceVersion}</serviceVersion>
    <charset>${charset}</charset>
    <signType>${signType}</signType>
    <sign>${sign}</sign>
</head>
</#macro>