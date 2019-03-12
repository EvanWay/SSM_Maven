package com.evanway.seckill.service;

import java.util.List;

import com.evanway.seckill.dto.Exposer;
import com.evanway.seckill.dto.SeckillExecution;
import com.evanway.seckill.entity.Seckill;
import com.evanway.seckill.exception.RepeatKillException;
import com.evanway.seckill.exception.SeckillCloseException;
import com.evanway.seckill.exception.SeckillException;

public interface SeckillService {
	
	/**
	 * 查询所有的秒杀
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
	 * @param seckillId
	 * @return
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
		throws SeckillException,RepeatKillException,SeckillCloseException;
}
