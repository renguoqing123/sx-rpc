package com.sx.rpc;

import java.io.Serializable;

import lombok.Data;

@Data
public class RpcRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1112800296706568993L;
	private String className;
	private String methodName;
	private Class[] paramType;
	private Object[] arge;
}
