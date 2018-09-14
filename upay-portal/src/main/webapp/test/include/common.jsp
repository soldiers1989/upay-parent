<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%--<script language="JavaScript" type="text/javascript" src="<%=path%>/test/include/jquery-1.9.1.min.js"></script>--%>
	<script src="http://code.jquery.com/jquery-1.9.1.min.js" integrity="sha256-wS9gmOZBqsqWxgIVgA8Y9WcQOa7PgSIX+rPA0VL2rbQ=" crossorigin="anonymous"></script>
<script type="text/javascript">
	var app = {path:'/upay-portal'};

	var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
			.split('');

	function URLencode(sStr) {
		return escape(sStr).replace(/\+/g, '%2B').replace(/\"/g, '%22')
				.replace(/\'/g, '%27').replace(/\//g, '%2F');
	}

	//通过UUID生成sessionId
	function genUUID() {
		var chars = CHARS, uuid = new Array(36), rnd = 0, r;
		for (var i = 0; i < 36; i++) {
			if (i == 8 || i == 13 || i == 18 || i == 23) {
				uuid[i] = '';
			} else if (i == 14) {
				uuid[i] = '4';
			} else {
				if (rnd <= 0x02)
					rnd = 0x2000000 + (Math.random() * 0x1000000) | 0;
				r = rnd & 0xf;
				rnd = rnd >> 4;
				uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
			}
		}
		return uuid.join('');
	};

	/**
	 *
	 * @param syshead 报文头
	 * @param body 报文体
	 * @param apphead 应用头
	 * @returns {{SYSHEAD: {TRANS_CODE: *, CHNL_ID: string, SESSION_ID: string, VERSION: null, USER_ID: *, RSP_CODE: null, RSP_MSG: null, PLATFORM: null}, APPHEAD: *, BODY: *}}
	 * @constructor
	 */
	function Message(SYSHEAD, BODY, APPHEAD) {
		BODY = BODY || {};
		var obj = {
			SYSHEAD : {
				TRANS_CODE : SYSHEAD.TRANS_CODE,
				CHNL_ID : '00',
				VERSION : null,
				RSP_CODE : null,
				RSP_MSG : null,
				PLATFORM : '00',
				pageSize:10,
				pageIndex:1
			},
			BODY : BODY
		}
		if (APPHEAD != null && APPHEAD != undefined) {
			obj.APPHEAD = APPHEAD;
		}

		if (SYSHEAD.hasOwnProperty('SESSION_ID')) {
			obj.SYSHEAD.SESSION_ID = SYSHEAD.SESSION_ID;
		}
		if (SYSHEAD.hasOwnProperty('USER_ID')) {
			obj.SYSHEAD.USER_ID = SYSHEAD.USER_ID;
		}

		return obj;
	}
	 
	 
	 /**
	 *
	 * @param syshead 报文头
	 * @param body 报文体
	 * @param apphead 应用头
	 * @returns {{syshead: {trans_code: *, chnl_id: string, session_id: string, user_id: *, rsp_code: null, rsp_msg: null, platform: null}, apphead:{ current_num:,page_index:}, body: *}}
	 * @constructor
	 */
	function Message2(syshead, body, apphead) {
		 body = body || {};
		var obj = {
			syshead : {
				trans_code : syshead.trans_code,
				chnl_id : '00',
				version : null,
				rsp_code : null,
				rsp_msg : null,
               // sessionId:'000162',
				platform:'01',
				pageSize:10,pageIndex:1
			},
			body : body
		}
		if (apphead != null && apphead != undefined) {
			obj.apphead = apphead;
		}

		if (syshead.hasOwnProperty('session_id')) {
			obj.syshead.session_id = syshead.session_id;
		}
		if (syshead.hasOwnProperty('user_id')) {
			obj.syshead.user_id = syshead.user_id;
		}

		return obj;
	}

	/**
	 * 报文打包与加密
	 * @param message
	 * @returns {{msg: *, md5: string}}
	 * @constructor
	 */
	function packMessage(message) {
		var msg = JSON.stringify(message);
	    return {
			msg : msg
		}; 
	}

	//获取用户号
	function getUserId() {
		return $("#userId").val();
	}

	//获取SessionId
	function getSessionId() {
		return $("#sessionId").val();
	}

	//支付交易
	function doPayAjax(tranCode, BODY) {
		$("#tips").html('交易处理中,请稍后...');
		$.ajax({
			type: 'POST',
			dataType: "html",
			url: app.path + "/upaygate/" + tranCode + "/",
			data: BODY,
			headers: {'Content-Type': 'application/text'},
			success: function( result ) {
				$("#tips").html(result);
			},
			error: function () {
				doFail();
			}
		});
	}

	//普通交易
	function doAjax(SYSHEAD, BODY, APPHEAD) {
		$("#tips").html('交易处理中,请稍后...');
		$.post(app.path +"/JSONServer/execute.do",
				packMessage(new Message2(SYSHEAD, BODY, APPHEAD)),
				function(data) {
					doSuc(data);
				}).fail(function() {
			doFail();
		});
	}

	function doSuc(data) {
		$("#tips").html(JSON.stringify(data, null, 50));
	}

	function doFail() {
		$("#tips").html('交易失败');
	}
</script>
</head>
<body onload="">
	<textarea rows="3" cols="5" style="width: 500" disabled="disabled">红塔银行银e付后台测试页面</textarea>
	<p>
		<input type="hidden" id="smsType" value="1" /> <label
			style="color: red" id="tips"></label>
	<p>
</body>
</html>
