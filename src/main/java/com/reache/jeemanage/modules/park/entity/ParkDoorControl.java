/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.entity;

import org.hibernate.validator.constraints.Length;

import com.reache.jeemanage.common.persistence.DataEntity;

/**
 * 门禁日志Entity
 * @author shock
 * @version 2018-03-19
 */
public class ParkDoorControl extends DataEntity<ParkDoorControl> {
	
	private static final long serialVersionUID = 1L;
	private String doorControlId;		// 门卡编号
	private String number;		// 车牌号
	private String floor;		// 楼层
	private String jiffyStand;		// 停车架
	private String space;		// 停车位
	private String isuse;		// 是否停车
	
	public ParkDoorControl() {
		super();
	}

	public ParkDoorControl(String id){
		super(id);
	}

	@Length(min=1, max=64, message="门卡编号长度必须介于 1 和 64 之间")
	public String getDoorControlId() {
		return doorControlId;
	}

	public void setDoorControlId(String doorControlId) {
		this.doorControlId = doorControlId;
	}
	
	@Length(min=1, max=64, message="车牌号长度必须介于 1 和 64 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
	
	@Length(min=0, max=1, message="是否停车长度必须介于 0 和 1 之间")
	public String getIsuse() {
		return isuse;
	}

	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}
	
}