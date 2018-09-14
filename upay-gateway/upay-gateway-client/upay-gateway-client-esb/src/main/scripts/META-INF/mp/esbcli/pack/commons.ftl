<#macro xmlRoot>
<?xml version="1.0" encoding="UTF-8"?>
<service>
	<SYS_HEAD>
		<SvcCd>${svcCd}</SvcCd>
		<SvcScn>${svcScn}</SvcScn>      
		<CnsmSysId>UPP</CnsmSysId>
		<SrcSysId>UPP</SrcSysId>
		<SrcSysSeqNo>UPP</SrcSysSeqNo> 
		<FileFlg>${fileFlg}</FileFlg>
		<TranDt>${machineDate}</TranDt>      
		<TranTm>${machineTime}</TranTm>
		<CnsmSysSeqNo>${bizSerialNo}</CnsmSysSeqNo>
		<ChnlTp>${channelId!"74"}</ChnlTp>
	</SYS_HEAD>
	<APP_HEAD> 
		<TlrNo>TYUPAY</TlrNo>      
		<BranchId>10100</BranchId>
	</APP_HEAD>
	<BODY>
		<#include "${transCode?trim}.ftl" />
	</BODY>
</service>
</#macro>

<#-- 对于非必输字段用此函数,如果值为空则不需要拼标签,用这个函数不用每个都写if,
用法${funTagView("标签名", "${值!''}")}
 -->
<#function funTagView tag value><#if value?? && "${value}" != ""><#return "<${tag}>${value}</${tag}>"></#if><#return ""></#function>

