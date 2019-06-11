package com.sx.rpc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Registry {
	//注册中心
	public static Map<Object,Class> map = new ConcurrentHashMap<Object,Class>();
}
