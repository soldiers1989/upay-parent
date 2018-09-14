{
    "couponCodes":[
    <#list couponCodes as couponCode>
    "${couponCode}"
    <#if couponCodes._index+1 != couponCodes?size>
    ,
    </#if>
    </#list>
    ],
    "transTime": "${transTime}",
    "transactionID": "${transactionId}",
    "channelId": "${channelId}",
    "signature": "${signature}"
}