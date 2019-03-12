package com.evanway.seckill.service.Impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.evanway.seckill.dto.Exposer;
import com.evanway.seckill.dto.SeckillExecution;
import com.evanway.seckill.entity.Seckill;
import com.evanway.seckill.entity.SuccessKilled;
import com.evanway.seckill.enums.SeckillStateEnum;
import com.evanway.seckill.exception.RepeatKillException;
import com.evanway.seckill.exception.SeckillCloseException;
import com.evanway.seckill.exception.SeckillException;
import com.evanway.seckill.mapper.dao.SeckillDao;
import com.evanway.seckill.mapper.dao.SuccessKilledDao;
import com.evanway.seckill.service.SeckillService;

//@Component @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;

	@Autowired
	private SuccessKilledDao successKilledDao;

	private final String salt = "sadfafafasf1231=-00";

	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 100);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if (seckill == null) {
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() 
				|| nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		try {
			if (md5 == null || !md5.equals(getMD5(seckillId))) {
				throw new SeckillException("seckill data rewrite");
			}
			//减库存 + 记录购买行为
			Date nowTime = new Date();
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if (updateCount < 0) {
				throw new SeckillCloseException("seckill is closed");
			} else {
				// 记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if (insertCount <= 0) {
					throw new RepeatKillException("seckill repeated");
				} else {
					// 秒杀成功
					SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SeckillException("seckill inner error" + e.getMessage());
		}
	}

}
