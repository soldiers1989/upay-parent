<#if errCode =='0000000000'>
  <return_code>SUCCESS</return_code>
  <#else>
  <return_code>FAIL</return_code>
  </#if>
  <#if errCode =='0000000000'>
  <return_msg>OK</return_msg>
  <#else>
  <return_msg>${errMsg}</return_msg>
</#if>
   
    
   