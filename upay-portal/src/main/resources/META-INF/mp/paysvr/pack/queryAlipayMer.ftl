<subMerchantId>${subMerchantId!""}</subMerchantId>
<indirectLevel>${indirectLevel!""}</indirectLevel>
<merName>${merName!""}</merName>
<aliasName>${aliasName!""}</aliasName>
<servicePhone>${servicePhone!""}</servicePhone>
<categoryId>${categoryId!""}</categoryId>
<alipaySource>${alipaySource!""}</alipaySource>
<businessLicense>${businessLicense!""}</businessLicense>
<businessLicenseType>${businessLicenseType!""}</businessLicenseType>

<cantactInfoList>
<#list cantactInfo as ci>
<cantactInfo>
	<userName>${ci.userName!""}</userName>
	<phone>${ci.phone!""}</phone>
	<mobile>${ci.mobile!""}</mobile>
	<email>${ci.email!""}</email>
	<contactType>${ci.contactType!""}</contactType>
	<idCardNo>${ci.idCardNo!""}</idCardNo>
</cantactInfo>
</#list>
</cantactInfoList>

<addressInfoList>
<#list addressInfo as ai>
<addressInfo>
	<provinceCode>${ai.provinceCode!""}</provinceCode>
	<cityCode>${ai.cityCode!""}</cityCode>
	<districtCode>${ai.districtCode!""}</districtCode>
	<address>${ai.address!""}</address>
	<longitude>${ai.longitude!""}</longitude>
	<latitude>${ai.latitude!""}</latitude>
	<addressType>${ai.addressType!""}</addressType>
</addressInfo>
</#list>
</addressInfoList>

<bankcardInfoList>
<#list bankcardInfo as bi>
<bankcardInfo>
	<cardNo>${bi.cardNo!""}</cardNo>
	<cardName>${bi.cardName!""}</cardName>
</bankcardInfo>
</#list>
</bankcardInfoList>

<payCodeInfoList>
<#list payCodeInfo as pi>
<payCodeInfo>
	<payCode>${pi.payCodeInfoUrl!""}</payCode>
</payCodeInfo>
</#list>
</payCodeInfoList>

<logonIdList>
<#list logonId as li>
<logonId>
	<alipayLogonId>${li.alipayLogonId!""}</alipayLogonId>
</logonId>
</#list>
</logonIdList>

<memo>${memo!""}</memo>