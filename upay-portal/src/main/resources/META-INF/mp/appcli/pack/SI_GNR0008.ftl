"body" : {

        "ROWS":[
        <#list rows as item>
         <#if !item_has_next>
         {
                  "MENU_ID":"${item.menuId}",
                  "MENU_NAME":"${item.menuName}",
                  "MENU_TYPE":"${item.menuType}",
                  "LEAF":"${item.leaf}",             
                  "PARENT_ID":"${item.parentId}",
                  "ICON_CLS":"${item.iconCls}",
                  "EXPANDED":"${item.expanded}",
                  "MENU_ADDR":"${item.menuAddr}",
                  "SORT_NO":"${item.sortNo}",
                  "REMARK":"${item.remark}",
                  "ICON":"${item.icon}",
                  "REMOVE_FLAG":"${item.removeFlag}",
                  "REMOVE_TIME":"${item.removeTime}",
                  "CREATE_TIME":"${item.createTime}",
                  "CREATE_USER_ID":"${item.createUserId}"

         }
          <#else>
                 {
                  "MENU_ID":"${item.menuId}",
                  "MENU_NAME":"${item.menuName}",
                  "MENU_TYPE":"${item.menuType}",
                  "LEAF":"${item.leaf}",             
                  "PARENT_ID":"${item.parentId}",
                  "ICON_CLS":"${item.iconCls}",
                  "EXPANDED":"${item.expanded}",
                  "MENU_ADDR":"${item.menuAddr}",
                  "SORT_NO":"${item.sortNo}",
                  "REMARK":"${item.remark}",
                  "ICON":"${item.icon}",
                  "REMOVE_FLAG":"${item.removeFlag}",
                  "REMOVE_TIME":"${item.removeTime}",
                  "CREATE_TIME":"${item.createTime}",
                  "CREATE_USER_ID":"${item.createUserId}"
                 },
                 </#if>
              </#list>
               ]
		}