package com.sx.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * 代理模式调用
 * @author rengq
 *
 * @param <T>
 */
public class RpcProxy<T> {


	public <T> T remoreCall(final Class<T> clazz, final String host, final int port) {
		
		
		return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz}, new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				ObjectOutputStream outputStream = null;
				ObjectInputStream inputStream = null;
				try {
					//处理请求
					Socket socket=new Socket(host, port);
					
					//封装消息体
					RpcRequest requset=new RpcRequest();
					requset.setClassName(clazz.getName());
					requset.setMethodName(method.getName());
					requset.setParamType(method.getParameterTypes());
					requset.setArge(args);
					
					//发送消息
					outputStream=new ObjectOutputStream(socket.getOutputStream());
					outputStream.writeObject(requset);
					outputStream.flush();
					
					//接受消息
					inputStream=new ObjectInputStream(socket.getInputStream());
					Object result = inputStream.readObject();//接受class对象
					return result;
				}catch (Exception e) {
					return null;
				}finally {
					
				}
				
			}
			
		});
	}

}
