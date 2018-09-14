package test;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.upay.busi.gnr.service.dto.ViewPicListQueryDto;
import com.upay.busi.gnr.service.impl.ViewPicListQueryServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ViewPicListQueryServiceTest {
	  	@Resource
	  	ViewPicListQueryServiceImpl viewImpl;
	    
	    @Test
	    public void execute() throws Exception{
			ViewPicListQueryDto viewPicListQueryDto = new ViewPicListQueryDto();
			viewPicListQueryDto.setChnlId("02");
			viewPicListQueryDto.setPageIndex(1);
			viewPicListQueryDto.setCurrentNum(1);
			
			 HashMap<String,Object> map=new HashMap<String,Object>();
		        map.put("actNo", "31325");
		        map.put("mobile", "13551354785");
		        Message m =
		                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory
		                    .createSimpleMessageInstance(), MessageFactory.createSimpleMessage(
		                    new HashMap<String, Object>(), map), FaultFactory.create(
		                    Constants.ResponseCode.SUCCESS, ""));
			viewPicListQueryDto = viewImpl.execute(viewPicListQueryDto, m);
			System.out.println(viewPicListQueryDto);
	    }
}
