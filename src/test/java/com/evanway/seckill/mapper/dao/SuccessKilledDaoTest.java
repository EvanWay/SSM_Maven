package com.evanway.seckill.mapper.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evanway.seckill.BaseTest;
import com.evanway.seckill.entity.SuccessKilled;

public class SuccessKilledDaoTest extends BaseTest {

	@Autowired
	private SuccessKilledDao successKilledDao;

	@Test
	public void testInsertSuccessKilled() {
		long id = 1000;
		long userPhone = 18814379885L;
		int insertCount = successKilledDao.insertSuccessKilled(id, userPhone);
		System.out.println("insertCount=" + insertCount);
	}

	@Test
	public void testQueryByIdWithSecKill() {
		long id = 1000;
		long userPhone = 18814379885L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(id, userPhone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}

}
