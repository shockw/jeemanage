package com.reache.jeemanage.test.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobDemo extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(JobDemo.class);

	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		logger.info("线程id：" + Thread.currentThread().getId() + " 时间:" + new Date());
	}
}
