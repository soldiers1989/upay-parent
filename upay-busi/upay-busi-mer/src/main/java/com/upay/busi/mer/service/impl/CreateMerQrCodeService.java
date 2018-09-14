/**
 * 
 */
package com.upay.busi.mer.service.impl;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.mer.service.dto.CreateMerQrCodeDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.QRCode;

/**生成微信公众号商户二维码
 * @author zhanggr
 *
 */
public class CreateMerQrCodeService extends AbstractDipperHandler<CreateMerQrCodeDto> {

    @Override
    public CreateMerQrCodeDto execute(CreateMerQrCodeDto createMerQrCodeDto, Message msg) throws Exception {
        
        String filePath = createMerQrCodeDto.getFilePath();
        String merNo = createMerQrCodeDto.getMerNo();
        if(StringUtils.isBlank(filePath)){
            filePath = System.getProperty("user.home");
        }
        filePath = filePath.concat("/"+merNo+".png");
        if(StringUtils.isBlank(merNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户号");
        }
        String content = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx288339422065bc01&redirect_uri=http%3a%2f%2fy1592w7818.iask.in%2fWechat%2findex.html%3fbusNo%3d"+merNo+"&response_type=code&scope=snsapi_base&state=State&connect_redirect=1#wechat_redirect";
        QRCode qrCode = new QRCode();
        qrCode.createGnrQROfWinxinMer(filePath, content);
        return createMerQrCodeDto;
    }

}
