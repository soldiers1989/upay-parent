<#macro xmlRoot>
<?xml version="1.0" encoding="GBK"?>
<YXBankData>
  <ReqData>
        <TranCode>610006</TranCode>
		<SMSCode>8051</SMSCode>
		<ServiceCode>1201</ServiceCode>
		<TermDate>${termDate}</TermDate>
		<TermTime>${termTime}</TermTime>
		<BranchNo>${branchNo}</BranchNo>
		<ReqParam>
        <#include "${tranCode}.ftl" />
        </ReqParam>
  </ReqData>
</YXBankData>
</#macro>

<#macro Head>
<TransHeader>
    <TransCode>${_TRAN_CODE}</TransCode>
    <TransDate>${transDate}</TransDate>
    <TransTime>${transTime}</TransTime>
    <XDSerialNo>${sysSeq}</XDSerialNo>
    <BranchId></BranchId>
    <OperatorId></OperatorId>
    <BusiSerialNo>${sysSeq}</BusiSerialNo>
    <TransType>${transType}</TransType>
    <ChannelId>${channelId}</ChannelId>
</TransHeader>
</#macro>