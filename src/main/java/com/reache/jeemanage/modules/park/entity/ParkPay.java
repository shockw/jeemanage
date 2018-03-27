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
 * 停车费用Entity
 * @author shock
 * @version 2018-03-19
 */
public class ParkPay extends DataEntity<ParkPay> {
	
	private static final long serialVersionUID = 1L;
	private String floor;		// 楼层
	private String jiffyStand;		// 停车架
	private String space;		// 停车位
	private String number;		// 车牌号
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private String cost;		// 费用
	
	public ParkPay() {
		super();
	}

	public ParkPay(String id){
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
	
	@Length(min=1, max=64, message="停车位长度必须介于 1 和 64 之间")
	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}
	
	@Length(min=1, max=64, message="车牌号长度必须介于 1 和 64 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始时间不能为空")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=1, max=11, message="费用长度必须介于 1 和 11 之间")
	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}
	
}