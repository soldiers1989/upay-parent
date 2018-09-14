<#macro head>

"syshead" : {
   "rsp_code" : "${outCode!""}",
   "rsp_msg" : "${outMsg!""}",
   <#if sign?? && sign != ''>
	 "sign":"${sign!""}",
   </#if>
   "charset":"${charset!""}",
   "chnlId":"${chnlId!""}",
   "serviceVersion":"${serviceVersion!""}",
   <#if signType?? && signType != ''>
   "signType":"${signType!""}"
   </#if>
}
</#macro>