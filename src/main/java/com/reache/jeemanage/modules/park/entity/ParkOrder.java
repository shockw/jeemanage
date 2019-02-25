/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.reache.jeemanage.common.persistence.DataEntity;

/**
 * 停车订单Entity
 * @author shockw
 * @version 2019-02-24
 */
public class ParkOrder extends DataEntity<ParkOrder> {
	
	private static final long serialVersionUID = 1L;
	private String floor;		// 楼层
	private String jiffyStand;		// 停车架
	private String personId;		// 用户id
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String status;		// 订单状态：0，新建，1，完成
	private Date beginStartTime;		// 开始 开始时间
	private Date endStartTime;		// 结束 开始时间
	private String path; //照片路径
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ParkOrder() {
		super();
	}

	public ParkOrder(String id){
		super(id);
	}

	@Length(min=1, max=64, message="楼层长度必须介于 1 和 64 之间")
	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}
	
	@Length(min=1, max=64, message="停车架长度必须介于 1 和 64 之间")
	public String getJiffyStand() {
		return jiffyStand;
	}

	public void setJiffyStand(String jiffyStand) {
		this.jiffyStand = jiffyStand;
	}
	
	@Length(min=1, max=64, message="用户id长度必须介于 1 和 64 之间")
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始时间不能为空")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=1, max=1, message="订单状态：0，新建，1，完成长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginStartTime() {
		return beginStartTime;
	}

	public void setBeginStartTime(Date beginStartTime) {
		this.beginStartTime = beginStartTime;
	}
	
	public Date getEndStartTime() {
		return endStartTime;
	}

	public void setEndStartTime(Date endStartTime) {
		this.endStartTime = endStartTime;
	}
		
}