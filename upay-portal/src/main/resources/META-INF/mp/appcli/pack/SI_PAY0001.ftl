"body" : {

        "ROWS":[
        <#list channelLogoSearchDtoList as item>
         <#if !item_has_next>
         {
                  "LOGO_ID":"${item.logoId}",
                  "LOGO_NAME":"${item.logoName}",
                  "LOGO_CLASS":"${item.logoClass}",
                  "CHANNEL_TRANSLMT":"${item.channelTranslmt}",

         }
          <#else>
                 {
                  "LOGO_ID":"${item.logoId}",
                  "LOGO_NAME":"${item.logoName}",
                  "LOGO_CLASS":"${item.logoClass}",
                  "CHANNEL_TRANSLMT":"${item.channelTranslmt}",
                 },
                 </#if>
              </#list>
               ]
		}
       
