package com.sx.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessHandle implements Runnable{

	private Socket socket;
	
	public ProcessHandle(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		handle(socket);
	}
	
	public void handle(Socket socket) {
		ObjectOutputStream outputStream = null;
		ObjectInputStream inputStream = null;
		try {
			//处理请求
			inputStream=new ObjectInputStream(socket.getInputStream());
			RpcRequest rpcRequest = (RpcRequest)inputStream.readObject();//接受class对象
			//反射处理
			Class clazz=null;
			String className = rpcRequest.getClassName();
			if(Registry.map.containsKey(className)) {
				clazz = Registry.map.get(className);
			}
			Method method=clazz.getMethod(rpcRequest.getMethodName(), rpcRequest.getParamType());
			Object result = method.invoke(clazz.newInstance(), rpcRequest.getArge());
			//返回结果
			outputStream=new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(result);
			outputStream.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//关闭流
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
