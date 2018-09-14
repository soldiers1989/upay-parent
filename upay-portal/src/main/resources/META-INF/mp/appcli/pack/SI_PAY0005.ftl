"body" : {  
       "V_ACCT_NO":"${vAcctNo}",
       "ROWS":[
       <#list bindCardList as bind>
       <#if !bind_has_next>
       { 
                "ROUTE_CODE":"${bind.routeCode}",
                "V_BIND_BANK_NAME":"${bind.vbindBankName}",
                "BANK_BIN_FLAG":"${bind.vbindBankFlag}",
       		    "BANK_CARD_NO":"${bind.vbindAcctNo}",
       		    "LOGO_PATH":"${bind.logoClass}",
       		    "BIND_ACCT_TYPE":"${bind.bindAcctType}"
       }
       <#else>
       {
                "ROUTE_CODE":"${bind.routeCode}",
                "V_BIND_BANK_NAME":"${bind.vbindBankName}",
                "BANK_BIN_FLAG":"${bind.vbindBankFlag}",
       		    "BANK_CARD_NO":"${bind.vbindAcctNo}",
       		    "LOGO_PATH":"${bind.logoClass}",
       		    "BIND_ACCT_TYPE":"${bind.bindAcctType}"
       },
       </#if>
       </#list>
       ]
}