package com.evanway.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.evanway.seckill.BaseTest;
import com.evanway.seckill.dto.Exposer;
import com.evanway.seckill.dto.SeckillExecution;
import com.evanway.seckill.entity.Seckill;
import com.evanway.seckill.exception.RepeatKillException;
import com.evanway.seckill.exception.SeckillCloseException;

public class SeckillServiceTest extends BaseTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}

	@Test
	public void testGetById() {
		long id =1000;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}",seckill);
	}

	@Test
	public void testSeckill() {
		long id =1000;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()) {
			logger.info("exposer={}",exposer);
			long phone = 18814379888L;
			String md5 = exposer.getMd5();
			try {
				SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
				logger.info("result={}",execution);
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			}catch (RepeatKillException e) {
				logger.error(e.getMessage());
			}
		}else {
			//秒杀未开启
			logger.warn("exposer={}",exposer);
		}
	}

}
