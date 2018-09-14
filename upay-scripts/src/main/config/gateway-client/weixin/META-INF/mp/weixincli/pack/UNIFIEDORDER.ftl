  <body>${body}</body>
  <detail>${detail}</detail>
  <attach>${attach}</attach>
  <out_trade_no>${outTradeNo}</out_trade_no>
  <fee_type>${feeType}</fee_type>
  <total_fee>${totalFee}</total_fee>
  <spbill_create_ip>${spbillCreateIp}</spbill_create_ip>
  <time_start>${timeStart}</time_start>
  <time_expire>${timeExpire}</time_expire>
  <goods_tag>${goodsTag}</goods_tag> 
  <notify_url>${notifyUrl}</notify_url>
  <trade_type>${tradeType}</trade_type>
  <limit_pay>${limitPay}</limit_pay>
  <sub_openid>${subOpenid}</sub_openid>
  <#if tradeType == "JSAPI"><openid>${openId}</openid></#if> 
  <#if tradeType == "NATIVE"><product_id>${productId}</product_id></#if> 
  
