"body" : {  
       "ROWS":[
       <#list logoList as bind>
       <#if !bind_has_next>
       { 
                "LOGO_ID":"${bind.logoId}",
                "LOGO_NAME":"${bind.logoName}",
                "LOGO_CLASS":"${bind.logoClass}"
       }
       <#else>
       {
                "LOGO_ID":"${bind.logoId}",
                "LOGO_NAME":"${bind.logoName}",
                "LOGO_CLASS":"${bind.logoClass}"
       },
       </#if>
       </#list>
       ]
}