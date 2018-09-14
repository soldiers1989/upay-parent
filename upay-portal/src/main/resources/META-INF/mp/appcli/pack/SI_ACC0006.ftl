"body" : {  
       "ROWS":[
       <#list transList as bind>
       <#if !bind_has_next>
       { 
                "ORDER_NO":"${bind.orderNo}",
                "ORDER_NAME":"${bind.orderName}",
                "ORDER_TIME":"${bind.orderTime}",
                "TRANS_AMT":"${bind.transAmt}",
                "TRANS_TYPE":"${bind.transType}",
       			"ORDER_STAT":"${bind.orderStat}",
       			"MER_NO":"${bind.merNo}",
       			"MER_NAME":"${bind.merName}",
                 "MER_FEE_AMT":"${bind.merFeeAmt}",
                 "FEE_AMT":"${bind.feeAmt}"
       }
       <#else>
       {
                "ORDER_NO":"${bind.orderNo}",
                "ORDER_NAME":"${bind.orderName}",
                "ORDER_TIME":"${bind.orderTime}",
                "TRANS_AMT":"${bind.transAmt}",
                "TRANS_TYPE":"${bind.transType}",
       			"ORDER_STAT":"${bind.orderStat}",
       			"MER_NO":"${bind.merNo}",
       			"MER_NAME":"${bind.merName}",
                 "MER_FEE_AMT":"${bind.merFeeAmt}",
                 "FEE_AMT":"${bind.feeAmt}"
       },
       </#if>
       </#list>
       ]
}