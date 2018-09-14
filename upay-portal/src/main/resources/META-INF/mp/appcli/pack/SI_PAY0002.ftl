"body" : {
        "ROWS":[
        <#list channelLeafSearchDtoList as item>
         <#if !item_has_next>
         {			
         		  "CHANNEL_ID":"${item.channelId}",
                  "CHANNEL_NAME":"${item.channelName}",
                  "CHANNEL_LEAF":"${item.channelLeaf}",
                  "CHANNEL_URL":"${item.channelUrl}",
                  "CHANNEL_FLAG":"${item.channelFlag}",
                  "APP_CHANNEL_URL":"${item.appChannelUrl}",
                  "CHANNEL_LOGO":"${item.channelLogo}"
         }
          <#else>
                 {
                  "CHANNEL_ID":"${item.channelId}",
                  "CHANNEL_NAME":"${item.channelName}",
                  "CHANNEL_LEAF":"${item.channelLeaf}",
                  "CHANNEL_URL":"${item.channelUrl}",
                  "CHANNEL_FLAG":"${item.channelFlag}",
                  "APP_CHANNEL_URL":"${item.appChannelUrl}",
                  "CHANNEL_LOGO":"${item.channelLogo}"
                 },
                 </#if>
              </#list>
               ]
		}
       
