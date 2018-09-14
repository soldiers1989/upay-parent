<returnCode>${returnCode!""}</returnCode>
<returnMsg>${returnMsg!""}</returnMsg>
<mchBillno>${mchBillno!""}</mchBillno>
<mchId>${mchId!""}</mchId>
<detailId>${detailId!""}</detailId>
<status>${status!""}</status>
<sendType>${sendType!""}</sendType>
<hbType>${hbType!""}</hbType>
<totalNum>${totalNo!""}</totalNum>
<totalAmount>${totalAmount!""}</totalAmount>
<reason>${reason!""}</reason>
<sendTime>${sendTime!""}</sendTime>
<refundTime>${refundTime!""}</refundTime>
<refundAmount>${refundAmount!""}</refundAmount>
<wishing>${wishing!""}</wishing>
<remark>${remark!""}</remark>
<actName>${actName!""}</actName>
<hblist>
<#list hblist as hb>
	<hbinfo>
	<openid>${hb.openid!""}</openid>
	<amount>${hb.amount!""}</amount>
	<rcvTime>${hb.rcvTime!""}</rcvTime>
	</hbinfo>
</#list>
</hblist>
	