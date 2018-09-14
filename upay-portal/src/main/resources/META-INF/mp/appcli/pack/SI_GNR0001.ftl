"body" : {  
       "ROWS":[
       <#list areaCodeList as bind>
       <#if !bind_has_next>
       { 
                "AREA_CODE":"${bind.areaCode}",
                "AREA_NAME":"${bind.areaName}"
       }
       <#else>
       {
                "AREA_CODE":"${bind.areaCode}",
                "AREA_NAME":"${bind.areaName}"
       },
       </#if>
       </#list>
       ]
}