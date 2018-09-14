{
    "activityId": "${activityId}",
    "userCodes":[
    <#list userCodes as userCode>
    "${userCode}"
    <#if userCodes._index+1 != userCodes?size>
    ,
    </#if>
    </#list>
    ],
    "transTime": ${transTime},
    "transactionID": "${transactionId}",
    "channelId": "${channelId}",
    "signature": "${signature}"
}