package com.briup.demo.utils;

import java.util.Date;

import org.springframework.util.concurrent.SuccessCallback;

/**
 * 返回消息的工具类
 * @author ASUS
 */
public class MessageUtil {
	/**
	 * 成功，并且返回数据
	 * 第一个标识，后面泛型类
	 * 第二个泛型
	 */
	public static <E>Message<E> success(E obj){
		return new Message<E>(200,"success",obj,new Date().getTime());
	}
	/**
	 * 成功，无返回值
	 */
	public static <E>Message<E> success(){
		return new Message<E>(200,"success",null,new Date().getTime());
	}
	/**
	 * 失败，返回自定义异常
	 */
	public static <E>Message<E> error(Integer code,String msg){
		return new Message<E>(code, msg, null, new Date().getTime());
	}
}
