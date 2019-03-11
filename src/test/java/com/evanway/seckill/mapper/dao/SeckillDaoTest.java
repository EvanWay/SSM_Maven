package com.evanway.seckill.mapper.dao;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evanway.seckill.BaseTest;
import com.evanway.seckill.entity.Seckill;

public class SeckillDaoTest extends BaseTest {

	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber() {
		
	}

	@Test
	public void testQueryById() {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);		
	}

	@Test
	public void testQueryAll() {
		List<Seckill> seckills = seckillDao.queryAll(0, 100);
		for (Seckill seckill : seckills) {
			System.out.println(seckill);
		}
	}
}
