package com.evanway.seckill.mapper.dao.cache;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evanway.seckill.BaseTest;
import com.evanway.seckill.entity.Seckill;
import com.evanway.seckill.mapper.dao.SeckillDao;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class RedisDaoTest extends BaseTest{

	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private SeckillDao seckillDao;
	
	private long id = 1001;
	
	@Test
	public void testSeckill() {
		
		Seckill seckill = redisDao.getSeckill(id);
		if (seckill == null) {
			seckill = seckillDao.queryById(id);
			if (seckill != null) {
				String result = redisDao.putSeckill(seckill);
				System.out.println(result);
				seckill = redisDao.getSeckill(id);
				System.out.println(seckill);
			}
		}
		
	}
}
