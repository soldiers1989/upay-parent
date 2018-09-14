"body" : {  
       "ROWS":[
       <#list usrList as bind>
       <#if !bind_has_next>
       {
           "MER_NAME":"${bind.MER_NAME}",
           "USER_ID":"${bind.USER_ID}",
           "MOBILE":"${bind.MOBILE}",
           "USER_NAME":"${bind.USER_NAME}",
           "REG_TYPE":"${bind.REG_TYPE}",
           "COM_EMAIL":"${bind.COM_EMAIL}",
           "MER_NO":"${bind.MER_NO}"
       }
       <#else>
           {
       "MER_NAME":"${bind.MER_NAME}",
       "USER_ID":"${bind.USER_ID}",
       "MOBILE":"${bind.MOBILE}",
       "USER_NAME":"${bind.USER_NAME}",
       "REG_TYPE":"${bind.REG_TYPE}",
       "COM_EMAIL":"${bind.COM_EMAIL}",
       "MER_NO":"${bind.MER_NO}"
       },
       </#if>
       </#list>
       ]
}