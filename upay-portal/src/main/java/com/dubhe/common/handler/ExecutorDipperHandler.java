package com.dubhe.common.handler;

import com.dubhe.common.constants.ReqRspConstants;
import com.dubhe.common.executor.ExecutorContext;
import com.dubhe.common.executor.ExecutorDTO;
import com.dubhe.common.util.G4Utils;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.presys.cp.server.DefaultServerDipperHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author dongweizhao
 */
public class ExecutorDipperHandler extends DefaultServerDipperHandler {

    private Logger logger = LoggerFactory.getLogger(ExecutorDipperHandler.class);

    /**
     * 交易前处理
     */
    private ExecutorContext preTrasationExecutors;

    @Override
    public Message handle(Message m) throws Exception {
        Map<String, Object> bodys = (Map<String, Object>) m.getTarget().getBodys();
        ExecutorDTO executorDTO = new ExecutorDTO();
        executorDTO.setBody((Map<String, Object>) m.getTarget().getBodys());
        try {
            preTrasationExecutors.execute(executorDTO);
        } catch (Exception e) {
            logger.error("执行前处理异常,[{}]", e);
            bodys.put(getErrorCodeName(),
                    ReqRspConstants.RSP_CODE_FAIL);
            bodys.put(getErrorMsgName(),
                    ReqRspConstants.RSP_CODE_FAIL_DESC);
            return m;
        }
        if(ReqRspConstants.RSP_CODE_SUCCESS.equals(executorDTO.getRspCode())){
            return super.handle(m);
        }else{
            bodys.put(getErrorCodeName(), executorDTO.getRspCode());
            bodys.put(getErrorMsgName(), executorDTO.getRspMsg());
            return m;
        }
    }

    @Override
    protected void setInitParam(Map<String, Object> init) {
        // TODO Auto-generated method stub
        logger.debug(">>ExecutorDipperHandler,[{}]", init);
    }


    @Override
    protected void doErrorHandle(Message m) {
        Map<String, Object> bodys = (Map<String, Object>) m.getTarget().getBodys();
        m.getFault().setCodeAll((String) bodys.get(getErrorCodeName()));
        m.getFault().setMsgAll((String) bodys.get(getErrorMsgName()));
    }


    @Override
    protected void doSuccessHandle(Message m) {
        Map<String, Object> bodys = (Map<String, Object>) m.getTarget().getBodys();
        replaceFilter(bodys);
        bodys.put(getErrorCodeName(), m.getFault().getCode());
        bodys.put(getErrorMsgName(), m.getFault().getMsg());
    }

    public void setPreTrasationExecutors(ExecutorContext preTrasationExecutors) {
        this.preTrasationExecutors = preTrasationExecutors;
    }

    private void replaceFilter(Object obj) {
        if (G4Utils.isNotEmpty(obj)) {
            if (obj instanceof String) {
                obj = cleanXSS((String) obj);
            } else if (obj instanceof String[]) {
                String[] values = (String[]) obj;
                for (int i = 0; i < values.length; i++) {
                    values[i] = cleanXSS(values[i]);
                }
            } else if (obj instanceof List) {
                List list = (List) obj;
                for (Object val : list) {
                    replaceFilter(val);
                }
            } else if (obj instanceof Map) {
                Map map = (Map) obj;
                Set set = map.entrySet();
                for (Iterator it = set.iterator(); it.hasNext(); ) {
                    Map.Entry entry = (Map.Entry) it.next();
                    replaceFilter(entry.getValue());
                }
            }
        }

    }

    private String cleanXSS(String value) {
        value = value.replace("\\", "");
        return value;
    }
}
