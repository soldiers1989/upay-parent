/**
 * 
 */
package com.upay.commons.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.pactera.dipper.commons.exception.ExInfo;
import com.upay.commons.dict.AppCodeDict;

/**
 * java操作二维码
 * 
 * @author chen.nie
 *
 */
public class QRCode {

	private int width = 200;//图片宽度

	private int height = 200;//图片长度

	private Map<String, Object> generateContent;//二维码内容

	private String pictureFormat = "png";//生成图片格式

	public QRCode() {
	}

	public QRCode(Map<String,Object> content){
		this.generateContent = content;
	}

	public QRCode(int width, int height, Map<String, Object> content) {
		this.width = width;
		this.height = height;
		this.generateContent = content;
	}

	public QRCode(int width, int height, Map<String, Object> content, String pictureFormat) {
		this.width = width;
		this.height = height;
		this.generateContent = content;
		this.pictureFormat = pictureFormat;
	}

	/**
	 * 生成二维码
	 * @param filePath
	 * @throws Exception
	 */
	public void generate(String filePath) throws Exception {
		Assert.notEmpty(generateContent, "二维码生成内容不能为空");
		JSONObject jsonObject = new JSONObject(generateContent);
		String jsonString = jsonObject.toJSONString();
		createQR(filePath, jsonString);
	}

	public void createGnrQR(String filePath) throws Exception {
		Assert.notEmpty(generateContent, "二维码生成内容不能为空");
		StringBuilder stringBuilder = new StringBuilder("http://172.18.88.78:8088/upay-portal/pay/?");
		Iterator<Map.Entry<String, Object>> it = generateContent.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		stringBuilder = stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
		createQR(filePath, stringBuilder.toString());
	}
	
	/*
	 *生成微信公众号支付 商户二维码
	 * */
	public void createGnrQROfWinxinMer(String filePath,String content) throws Exception {
	    if(StringUtils.isBlank(content)){
	        ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "生成二维码内容");
	    }
	    if(StringUtils.isBlank(filePath)){
	        ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "图片存储路径");
	    }
        StringBuilder stringBuilder = new StringBuilder(content);
        createQR(filePath, stringBuilder.toString());
    }
	
	
	

	/**
	 * 将url生成二维码返回Base64编码
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String createQR(String url) throws Exception {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url,
				BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, pictureFormat, bos);
		byte[] bytes = bos.toByteArray();
		String img = StringUtils.deleteWhitespace(Base64.encodeBase64String(bytes));
		return String.format("data:image/png;base64,%s", img);
	}

	private void createQR(String filePath, String jsonString) throws Exception {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(jsonString,
				BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		Path path = FileSystems.getDefault().getPath(filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, pictureFormat, path);// 输出图像
	}

	/**
	 * 读取二维码内容
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> readQR(String path) throws Exception {
		BufferedImage image = ImageIO.read(new File(path));
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
		Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
		Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
		JSONObject content = JSONObject.parseObject(result.getText());
		Map<String,Object> rst = new HashMap<String, Object>();
		for (String key : content.keySet()) {
			rst.put(key, content.get(key));
		}
		return rst;
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> content = new HashMap<>();
		content.put("merNo", "100002");
		content.put("transCode", "pay");
		QRCode qrCode = new QRCode(content);
		String userHome = System.getProperty("user.home");
		String filePath = userHome.concat("/IdeaProjects/ts-bank/test.png");
		qrCode.createGnrQR(filePath);

		QRCode qrCode1 = new QRCode();
		String qr = qrCode1.createQR("http://172.18.88.78:8088/upay-portal/pay/");
		System.out.println(qr);
	}

	public Map<String, Object> getGenerateContent() {
		return generateContent;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
