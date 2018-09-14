"body" : {  
       "ROWS":[
       <#list viewPicList as bind>
       <#if !bind_has_next>
       { 
                "PIC_LINK":"${bind.picLink}"
       }
       <#else>
       {
                "PIC_LINK":"${bind.picLink}"
       },
       </#if>
       </#list>
       ]
}