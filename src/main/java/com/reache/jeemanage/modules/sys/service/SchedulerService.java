/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 		  __													   *
 *  __   (__`\             000000000     00000000     000000000    *
 * (__`\   \\`\            00      00    00     00    00      00   *
 *  `\\`\   \\ \           00      00    00      00   00      00   *
 *    `\\`\  \\ \          00     00     00       00  00     00    *
 *      `\\`\#\\ \#        00000000      00       00  00000000     *
 *        \_ ##\_ |##      00     00     00       00  00           *
 *        (___)(___)##     00      00    00      00   00           *
 *         (0)  (0)`\##    00      00    00     00    00           *
 *          |~   ~ , \##   000000000     00000000     00           *
 *          |      |  \##                                          *
 *          |     /\   \##         __..---'''''-.._.._             *
 *          |     | \   `\##  _.--'                _  `.           *
 *          Y     |  \    `##'                     \`\  \          *
 *         /      |   \                             | `\ \         *
 *        /_...___|    \                            |   `\\        *
 *       /        `.    |                          /      ##       *
 *      |          |    |                         /      ####      *
 *      |          |    |                        /       ####      *
 *      | () ()    |     \     |          |  _.-'         ##       *
 *      `.        .'      `._. |______..| |-'|                     *
 *        `------'           | | | |    | || |                     *
 *                           | | | |    | || |                     *
 *                           | | | |    | || |                     *
 *                           | | | |    | || |                     *
 *                     _____ | | | |____| || |                     *
 *                    /     `` |-`/     ` |` |                     *
 *                    \________\__\_______\__\                     *
 *                     """""""""   """""""'"""                     *
 *_________________________________________________________________*
 *                                                                 *
 *         We don't produce code, we're just code porters.         *
 *_________________________________________________________________*           
 *******************************************************************
 */
package com.reache.jeemanage.modules.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.quartzweb.manager.web.QuartzWebManager;

/**
 * 日志Service
 * 
 * @author BDP
 * @date 2017年10月17日 下午8:37:11
 * @version 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class SchedulerService {
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	public Map<String, Object> getSchedulerInfo() throws SchedulerException {
		Map<String, Object> schedulerInfo = new HashMap<String, Object>();
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		schedulerInfo.put("schedulerName", scheduler.getSchedulerName());
		schedulerInfo.put("isShutdown", scheduler.isShutdown());
		schedulerInfo.put("isStarted", scheduler.isStarted());
		schedulerInfo.put("isInStandbyMode", scheduler.isInStandbyMode());
		return schedulerInfo;
	}
	@Transactional(readOnly = false)
	public void addJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		Class beanClass = QuartzWebManager.getClass("com.reache.jeemanage.test.job.JobDemo");
		JobDataMap jobMap = new JobDataMap();
		JobDetail jobDetail = JobBuilder.newJob(beanClass).withIdentity("test", "test").withDescription("")
				.setJobData(jobMap).storeDurably().build();
		scheduler.addJob(jobDetail, true);
		TriggerBuilder<Trigger> triggerTriggerBuilder = TriggerBuilder.newTrigger();
		triggerTriggerBuilder = triggerTriggerBuilder.withIdentity("test", "test");
		triggerTriggerBuilder.forJob("test", "test");
		CronTrigger trigger = triggerTriggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();
		scheduler.scheduleJob(trigger);
		
	}

}
