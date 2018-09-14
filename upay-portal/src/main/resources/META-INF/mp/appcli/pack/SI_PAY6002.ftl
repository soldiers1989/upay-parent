"body" : {  
       "ROWS":[
<#list transList as bind>
    <#if !bind_has_next>
       {
                "STL_DATE":"${bind.stlDate?string("yyyy-MM-dd")}",
                "STL_BATCH_NO":"${bind.stlBatchNo}",
                "MER_NO":"${bind.merNo}",
                "SEC_MER_NO":"${bind.secMerNo}",
                "PAY_AMT":"${bind.payAmt}",
       			"PAY_FEE":"${bind.payFee}",
       			"REV_AMT":"${bind.revAmt}",
       			"REV_FEE":"${bind.revFee}",
       			"BAR_AMT":"${bind.barAmt}",
       			"STAT":"${bind.stat}"
       }
    <#else>
       {
                "STL_DATE":"${bind.stlDate?string("yyyy-MM-dd")}",
                "STL_BATCH_NO":"${bind.stlBatchNo}",
                "MER_NO":"${bind.merNo}",
                "SEC_MER_NO":"${bind.secMerNo}",
                "PAY_AMT":"${bind.payAmt}",
       			"PAY_FEE":"${bind.payFee}",
       			"REV_AMT":"${bind.revAmt}",
       			"REV_FEE":"${bind.revFee}",
       			"BAR_AMT":"${bind.barAmt}",
       			"STAT":"${bind.stat}"
       }
    </#if>
</#list>
       ]
}