package com.dubhe.common.exception;

import com.dubhe.common.util.GlobalConstants;

/**
 * G4公共异常类<br>
 * 
 */
public class G4Exception extends RuntimeException {

	public G4Exception() {
		super();
	}

	public G4Exception(String msg) {
		super(GlobalConstants.Exception_Head + msg);
	}
}
