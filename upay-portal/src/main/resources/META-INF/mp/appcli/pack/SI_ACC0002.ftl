"body" : {
       "V_ACCT_NO":"${vAcctNo}",
       "V_ACCT_STAT":"${vAcctStat}",
       "STOP_STAT":"${stopStat}",
       "FRZ_STAT":"${frzStat}",
       "SET_FLAG":"${setFlag}",
       "ACCT_NAME":"${acctName}",
       "ACCT_OTHER_NAME":"${acctOtherName}",
       "CCY":"${CCY}",
       "FRZ_BAL":"${frzBal}",
       "ACCT_BAL":"${acctBal}",
       "LAST_BAL":"${lastBal}",
       "CUT_BAL":"${cutBal}",
       "EXT_TIME":"${extTime}",
       "LAST_CHG_TIME":"${lastChgTime}",
       "LAST_TIME":"${lastTime}",
       "DAC":"${DAC}",
       "OPEN_TIME":"${openTime}",
       "USE_BAL":"${useBal}",  
       "CARD_NUM":"${cardNum}",
       "ROUTE_CODE":"${routeCode}",
       "ROWS":[
        <#list bindCardList as item>
         <#if !item_has_next>
         {
                  "BIND_CHNL_ID":"${item.bindChnlId}",
                  "V_ACCT_NO":"${item.vacctNo}",
                  "DEFAULT_FLAG":"${item.defaultFlag}",
                  "BIND_ACC_TYPE":"${item.bindAcctType}",
                  "V_BIND_BANK_FLAG":"${item.vbindBankFlag}",
                  "V_BIND_BANK_CODE":"${item.vbindBankCode}",
                  "V_ACCT_BANK_NAME":"${item.vbindBankName}",
                  "V_BIND_ACCT_NO":"${item.vbindAcctNo}",
                  "V_BIND_OPEN_CODE":"${item.vbindOpenCode}",
                  "BIND_STAT":"${item.bindStat}",
                  "V_BIND_FLAG":"${item.vbindFlag}",
                  "THIRD_AUTH_CHNL":"${item.thirdAuthChnl}",
                  "TRANS_FER_VERIFY_AMT":"${item.transferVerifyAmt}",
                  "TRANSFER_VERIFY_DATE":"${item.transferVerifyDate}",
                  "UNBIND_CHNL_ID":"${item.unbindChnlId}",
                  "UNBIND_REASON_FLAG":"${item.unbindReasonFlag}",                 
                  "BANK_BIN_FLAG":"${item.bankBinFlag}",
                  "LOGO_CLASS":"${item.logoClass}",
                  "LOGO_NAME":"${item.logoName}"
         }
          <#else>
                 {
                   "BIND_CHNL_ID":"${item.bindChnlId}",
                  "V_ACCT_NO":"${item.vacctNo}",
                  "DEFAULT_FLAG":"${item.defaultFlag}",
                  "BIND_ACC_TYPE":"${item.bindAcctType}",
                  "V_BIND_BANK_FLAG":"${item.vbindBankFlag}",
                  "V_BIND_BANK_CODE":"${item.vbindBankCode}",
                  "V_ACCT_BANK_NAME":"${item.vbindBankName}",
                  "V_BIND_ACCT_NO":"${item.vbindAcctNo}",
                  "V_BIND_OPEN_CODE":"${item.vbindOpenCode}",
                  "BIND_STAT":"${item.bindStat}",
                  "V_BIND_FLAG":"${item.vbindFlag}",
                  "THIRD_AUTH_CHNL":"${item.thirdAuthChnl}",
                  "TRANS_FER_VERIFY_AMT":"${item.transferVerifyAmt}",
                  "TRANSFER_VERIFY_DATE":"${item.transferVerifyDate}",
                  "UNBIND_CHNL_ID":"${item.unbindChnlId}",
                  "UNBIND_REASON_FLAG":"${item.unbindReasonFlag}",                
                  "BANK_BIN_FLAG":"${item.bankBinFlag}",
                  "LOGO_CLASS":"${item.logoClass}",
                  "LOGO_NAME":"${item.logoName}"
                 },
                 </#if>
              </#list>
               ]
}