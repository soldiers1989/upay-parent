package com.dubhe.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import com.dubhe.common.util.G4Constants;
import com.dubhe.common.util.G4Utils;
/**
 * 通用转发页面
 * 
 * @author weizhao.dong
 *
 */
public class ForWardController extends AbstractController {

	public ModelAndView forward(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = request.getParameter("path");
        if(G4Utils.isNotEmpty(path)){
        	path=G4Constants.path+path;
        }else{
        	path="404";
        }
        
		ModelAndView mav = new ModelAndView(path,super.getPraramsAsDto(request));
		return mav;
	}

}
