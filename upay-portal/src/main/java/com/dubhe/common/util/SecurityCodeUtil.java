package com.dubhe.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.web.context.support.WebApplicationContextUtils;













import com.dubhe.common.properties.PropertiesUtil;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.BasePo;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommunicationConstants;

/**
 * @author weizhao.dong 图形验证码获取
 */
public class SecurityCodeUtil extends HttpServlet {

	private Logger logger = Logger.getLogger(SecurityCodeUtil.class);

	
	private IDipperCached cache;

	private static final long serialVersionUID = -5813134629255375160L;

	private int width = 200;
	private int height = 45;
	private int codeCount = 4;
	private int xx = 0;
	private int fontHeight;
	private int codeY;

	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'K',
			 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
			'Z', '2', '3', '4', '5', '6', '7', '8', '9' };

	public void init() throws ServletException {

		String strWidth = this.getInitParameter("width");
		String strHeight = this.getInitParameter("height");
		String strCodeCount = this.getInitParameter("codeCount");
		cache = (IDipperCached) WebApplicationContextUtils
				.getWebApplicationContext(this.getServletContext()).getBean(
						"DIPPER_REDIS_CLIENT");

		try {
			if (strWidth != null && strWidth.length() != 0) {
				width = Integer.parseInt(strWidth);
			}
			if (strHeight != null && strHeight.length() != 0) {
				height = Integer.parseInt(strHeight);
			}
			if (strCodeCount != null && strCodeCount.length() != 0) {
				codeCount = Integer.parseInt(strCodeCount);
			}
		} catch (NumberFormatException e) {
			logger.error("", e);
		}

		xx = width / (codeCount + 1);
		fontHeight = height - 2;
		codeY = height - 4;

	}

	/**
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws java.io.IOException
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {

		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gd = buffImg.createGraphics();

		Random random = new Random();

		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		Font font = new Font("宋体", Font.PLAIN, fontHeight);

		gd.setFont(font);

		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, width - 1, height - 1);

		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;

		// 随机生成验证码
		int allCodesCount = codeSequence.length;
		for (int i = 0; i < codeCount; i++) {

			String strRand = String.valueOf(codeSequence[random
					.nextInt(allCodesCount)]);

			red = random.nextInt(200);
			green = random.nextInt(200);
			blue = random.nextInt(200);

			gd.setColor(new Color(red, green, blue));
			gd.drawString(strRand, (i * xx) + xx / 2, codeY);

			randomCode.append(strRand);
		}

		// 画干扰线
		gd.setColor(new Color(red, green, blue));
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(30);
			int yl = random.nextInt(30);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		String chnlId = req.getParameter(CommunicationConstants.CHNL_ID);// 渠道号
        String verifyKey = req.getParameter(CommunicationConstants.VERIFY_KEY);//随机验证码KEY
        if(StringUtils.isBlank(verifyKey)){
            logger.error("渠道为"+chnlId+"时未上送随机验证码key");
            return;
        }
        
//        cache.set(CacheConstants.VALIDATE_CODE.concat(verifyKey),
//                randomCode.toString(),((BigDecimal)ParmsContext.getParmByKey(CmparmConstants.VALIDATE_CODE_TIMEOUT)).intValue() * 60);
        int validateCodeTimeout = Integer.valueOf(PropertiesUtil.getProperty("VALIDATE_CODE_TIMEOUT"));
        cache.set(CacheConstants.VALIDATE_CODE.concat(verifyKey),
                randomCode.toString(),validateCodeTimeout*60);
//        String timeOut = cache.get(CmparmConstants.VALIDATE_CODE_TIMEOUT);
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		resp.setContentType("image/jpeg");

		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

}
