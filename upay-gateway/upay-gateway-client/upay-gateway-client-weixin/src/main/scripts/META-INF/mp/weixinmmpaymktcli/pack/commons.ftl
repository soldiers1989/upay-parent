<#macro xmlRoot>
<?xml version="1.0" encoding="UTF-8"?>
<xml>
  <nonce_str>${nonceStr}</nonce_str>
  <sign>${sign}</sign>
  <mch_billno>${mchBillno}</mch_billno>
  <mch_id>${mchId}</mch_id>
  <sub_mch_id>${subMchId}</sub_mch_id>
  <#include "${tranCode?trim}.ftl" />
</xml>
</#macro>

<#-- 对于非必输字段用此函数,如果值为空则不需要拼标签,用这个函数不用每个都写if,
用法${funTagView("标签名", "${值!''}")}
 -->
<#function funTagView tag value><#if value?? && "${value}" != ""><#return "<${tag}>${value}</${tag}>"></#if><#return ""></#function>

