<#macro xmlRoot>
<?xml version="1.0" encoding="UTF-8"?>
<service>
	<SYS_HEAD>
		<SvcCd>${svcCd}</SvcCd>
		<SvcScn>${svcScn}</SvcScn>      
		<CnsmSysId>${cnsmSysId}</CnsmSysId>   
		<PrvdSysId>${prvdSysId}</PrvdSysId>   
		<CnsmSysSeqNo>${cnsmSysSeqNo}</CnsmSysSeqNo>
		<PrvdSysSeqNo>${prvdSysSeqNo}</PrvdSysSeqNo>
		<SrcSysSeqNo>${srcSysSeqNo}</SrcSysSeqNo> 
		<Mac>${mac}</Mac>         
		<TranDt>${tranDt}</TranDt>      
		<TranTm>${tranTm}</TranTm>      
		<TranRetSt>${tranRetSt}</TranRetSt>  
		<RetMsgArryList>
		<RetMsgArry>
		<RetCd>${retCd}</RetCd>
		<RetMsg>${retMsg}</RetMsg>
		</RetMsgArry>
		</RetMsgArryList>  
		<PrvdSysSvrId>${prvdSysSvrId}</PrvdSysSvrId>
		<FileFlg>${fileFlg}</FileFlg>
	</SYS_HEAD>
	<APP_HEAD> 
		<TlrNo>${tlrNo}</TlrNo>      
		<BranchId>${branchId}</BranchId>   
		<TlrPswd>${tlrPswd}</TlrPswd>     
		<TlrLvl>${tlrLvl}</TlrLvl>     
		<TlrTp>${tlrTp}</TlrTp>       
		<TlrVrfyMode>${tlrVrfyMode}</TlrVrfyMode> 
	</APP_HEAD>
	<#include "${transCode?trim}.ftl" />
</service>
</#macro>

<#-- 对于非必输字段用此函数,如果值为空则不需要拼标签,用这个函数不用每个都写if,
用法${funTagView("标签名", "${值!''}")}
 -->
<#function funTagView tag value><#if value?? && "${value}" != ""><#return "<${tag}>${value}</${tag}>"></#if><#return ""></#function>

