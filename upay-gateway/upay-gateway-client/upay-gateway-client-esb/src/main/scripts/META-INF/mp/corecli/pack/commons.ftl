<#macro xmlRoot>
<?xml version="1.0" encoding="UTF-8"?>
<epay_host>
  <req>
    <transcode>${tranCode}</transcode>
    <machine_date>${machineDate}</machine_date>
    <machine_time>${machineTime}</machine_time>
    <biz_date>${bizDate}</biz_date>
    <biz_serialno>${bizSerialNo}</biz_serialno>
    <ChannelId>${channelId!"74"}</ChannelId>
    <Brc>10100</Brc>
    <Teller>TYUPAY</Teller>
    <#include "${tranCode}.ftl" />
  </req>
</epay_host>
</#macro>
