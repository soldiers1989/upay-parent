<orderDate>${orderDate!""}</orderDate>
<count>${count!""}</count>
<totTransAmt>${totTransAmt!""}</totTransAmt>
<items>
<#list reconciliationDocDownloadDtoList as item>
<item>
<orderNo>${item.orderNo!""}</orderNo>
<oriOrderNo>${item.oriOrderNo!""}</oriOrderNo>
<merNo>${item.merNo!""}</merNo>
<secMerNo>${item.secMerNo!""}</secMerNo>
<transAmt>${item.txnAmt!""}</transAmt>
<outerOrderNo>${item.merOrder!""}</outerOrderNo>
<CURR>${item.curr!""}</CURR>
<transStat>${item.orderStat!""}</transStat>
<transType>${item.transType!""}</transType>
<merFee>${item.merFeeAmt!""}</merFee>
<secMerFeeCharge></secMerFeeCharge>
</item>
</#list>
</items>

