"body" : {
		"ROWS":[
        <#list merReltypeList as item>
         <#if !item_has_next>
         {			
         		  "RELTYPE_NAME":"${item.reltypeName}",
                  "RELTYPE_ID":"${item.reltypeId}"
         }
         	<#else>
         		{
                  "RELTYPE_NAME":"${item.reltypeName}",
                  "RELTYPE_ID":"${item.reltypeId}"
                 },
                 </#if>
              </#list>
         		]
		}