"body" : {
		 "ROWS":[
        <#list feeKindList as item>
         <#if !item_has_next>
         {			
         		  "FEE_CODE":"${item.feeCode}",
                  "FEE_NAME":"${item.feeName}",
                  "FEE_MODE":"${item.feeMode}",
                  "FIX_FEE":"${item.fixFee}",
                  "RATION_FEE":"${item.rationFee}",
                  "HIGH_FEE":"${item.highFee}",
                  "LOW_FEE":"${item.lowFee}",
                  "MEMO":"${item.memo}"
         }
          <#else>
                 {
                  "FEE_CODE":"${item.feeCode}",
                  "FEE_NAME":"${item.feeName}",
                  "FEE_MODE":"${item.feeMode}",
                  "FIX_FEE":"${item.fixFee}",
                  "RATION_FEE":"${item.rationFee}",
                  "HIGH_FEE":"${item.highFee}",
                  "LOW_FEE":"${item.lowFee}",
                  "MEMO":"${item.memo}"
                 },
                 </#if>
              </#list>
               ]
		}