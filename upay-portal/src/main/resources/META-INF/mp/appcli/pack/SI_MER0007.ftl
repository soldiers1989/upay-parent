"body" : {
		 "ROWS":[
        <#list feeAssList as item>
         <#if !item_has_next>
         {			
         		  "ASS_CODE":"${item.assCode}",
                  "ASS_NAME":"${item.assName}",
                  "ASS_ID":"${item.assId}",
                  "ASS_KIND":"${item.assKind}",
                  "ASS_TYPE":"${item.assType}",
                  "ASS_RATE":"${item.assRate}",
                  "FIX_AMT":"${item.fixAmt}"
         }
          <#else>
                 {
                  "ASS_CODE":"${item.assCode}",
                  "ASS_NAME":"${item.assName}",
                  "ASS_ID":"${item.assId}",
                  "ASS_KIND":"${item.assKind}",
                  "ASS_TYPE":"${item.assType}",
                  "ASS_RATE":"${item.assRate}",
                  "FIX_AMT":"${item.fixAmt}"
                 },
                 </#if>
              </#list>
               ]
		}