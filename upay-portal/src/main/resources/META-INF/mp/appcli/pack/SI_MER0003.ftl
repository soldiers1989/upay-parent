"body" : {
		"ROWS":[
        <#list merBaseInfoList as item>
         <#if !item_has_next>
         {			
         		  "MER_NO":"${item.merNo}",
                  "MER_NAME":"${item.merName}",
                  "MER_WITHOUT_PWD_SIGN":"${item.merWithoutPwdSign}",
                  "PAY_OPEN_FLAG":"${item.payOpenFlag}",
                  "MER_BUSI_TYPE":"${item.merBusiType}",
                  "CONTACT":"${item.contact}",
                  "CONTACT_TEL":"${item.contactTel}",
                  "CONTACT_MOBILE":"${item.contactMobile}",
                  "CONTACT_EMAIL":"${item.contactEmail}",
                  "MER_TEL":"${item.merTel}",
                  "MER_FAX":"${item.merFax}",
                  "MER_ADDR":"${item.merAddr}",
                  "MER_POSTAL_CODE":"${item.merPostalCode}",
                  "WEBSITE_CODE":"${item.websiteCode}",
                  "WEBSITE_NAME":"${item.websiteName}",
                  "WEBSITE_DOMAIN":"${item.websiteDomain}",
                  "WEBSITE_SCOP":"${item.websiteScop}",
                  "COMPANY_NAME":"${item.companyName}",
                  "EGAL_PERSON_NAME":"${item.egalPersonName}",
                  "EGAL_PERSON_ID_TYPE":"${item.egalPersonIdType}",
                  "EGAL_PERSON_ID_NO":"${item.egalPersonIdNo}",
                  "COMPANY_ID_TYPE":"${item.companyIdType}",
                  "COMPANY_ID_NO":"${item.companyIdNo}",
                  "ORG_DEPT_NO":"${item.orgDeptNo}",
                  "BUSI_LICENSE_ID":"${item.busiLicenseId}",
                  "OPEN_DATE":"${item.openDate}",
                  "MER_STATE":"${item.merState}",
                  "SUB_MCH_ID":"${item.subMchId}"
         }
         	<#else>
         		{
                  "MER_NO":"${item.merNo}",
                  "MER_NAME":"${item.merName}",
                  "MER_WITHOUT_PWD_SIGN":"${item.merWithoutPwdSign}",
                  "PAY_OPEN_FLAG":"${item.payOpenFlag}",
                  "MER_BUSI_TYPE":"${item.merBusiType}",
                  "CONTACT":"${item.contact}",
                  "CONTACT_TEL":"${item.contactTel}",
                  "CONTACT_MOBILE":"${item.contactMobile}",
                  "CONTACT_EMAIL":"${item.contactEmail}",
                  "MER_TEL":"${item.merTel}",
                  "MER_FAX":"${item.merFax}",
                  "MER_ADDR":"${item.merAddr}",
                  "MER_POSTAL_CODE":"${item.merPostalCode}",
                  "WEBSITE_CODE":"${item.websiteCode}",
                  "WEBSITE_NAME":"${item.websiteName}",
                  "WEBSITE_DOMAIN":"${item.websiteDomain}",
                  "WEBSITE_SCOP":"${item.websiteScop}",
                  "COMPANY_NAME":"${item.companyName}",
                  "EGAL_PERSON_NAME":"${item.egalPersonName}",
                  "EGAL_PERSON_ID_TYPE":"${item.egalPersonIdType}",
                  "EGAL_PERSON_ID_NO":"${item.egalPersonIdNo}",
                  "COMPANY_ID_TYPE":"${item.companyIdType}",
                  "COMPANY_ID_NO":"${item.companyIdNo}",
                  "ORG_DEPT_NO":"${item.orgDeptNo}",
                  "BUSI_LICENSE_ID":"${item.busiLicenseId}",
                  "OPEN_DATE":"${item.openDate}",
                  "MER_STATE":"${item.merState}",
                  "SUB_MCH_ID":"${item.subMchId}"
                 },
                 </#if>
              </#list>
         		]
		}