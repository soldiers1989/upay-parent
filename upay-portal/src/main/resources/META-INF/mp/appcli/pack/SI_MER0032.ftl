"body" : {
"MERLIST":[
        <#list merList as bind>
            <#if !bind_has_next>
            {
            "MER_NO":"${bind.merNo}",
            "MER_NAME":"${bind.merName}",
            "CONTACT_MOBILE":"${bind.contactMobile}",
            "CONTACT_EMAIL":"${bind.contactEmail}",
            "USER_ID":"${bind.userId}",
            "ALIAS_NAME":"${bind.aliasName}",
            "MER_STATE":"${bind.merState}"
            }
            <#else>
            {
            "MER_NO":"${bind.merNo}",
            "MER_NAME":"${bind.merName}",
            "CONTACT_MOBILE":"${bind.contactMobile}",
            "CONTACT_EMAIL":"${bind.contactEmail}",
            "USER_ID":"${bind.userId}",
            "ALIAS_NAME":"${bind.aliasName}",
            "MER_STATE":"${bind.merState}"
            },
            </#if>
        </#list>
],
"USRLIST":[
<#list usrList as bind>
    <#if !bind_has_next>
    {
    "USER_ID":"${bind.userId}",
    "MOBILE":"${bind.mobile}",
    "USER_NAME":"${bind.userName}",
    "COM_EMAIL":"${bind.comEmail}",
    "REG_TYPE":"${bind.regType}"
    }
    <#else>
    {
    "USER_ID":"${bind.userId}",
    "MOBILE":"${bind.mobile}",
    "USER_NAME":"${bind.userName}",
    "COM_EMAIL":"${bind.comEmail}",
    "REG_TYPE":"${bind.regType}"
    },
    </#if>
</#list>
]
}