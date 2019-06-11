package com.sx.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {
	
	ExecutorService service = Executors.newCachedThreadPool();
	
	public void start(int port) {
		try {
			ServerSocket server=new ServerSocket(port);
			while (true) {
				Socket socket = server.accept();//等待连接
				service.execute(new ProcessHandle(socket));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
