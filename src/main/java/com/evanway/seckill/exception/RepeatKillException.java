package com.evanway.seckill.exception;

/**
 * 重复秒杀（运行期）
 * @author Administrator
 *
 */
public class RepeatKillException extends SeckillException {

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RepeatKillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}