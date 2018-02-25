package com.reache.jeemanage.modules.park.entity;

import com.reache.jeemanage.common.persistence.DataEntity;

public class IdleParkSpaceDto  extends DataEntity<IdleParkSpaceDto>{
	
	private static final long serialVersionUID = 1L;
	private String floor; // 楼层

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getJiffyStand() {
		return jiffyStand;
	}

	public void setJiffyStand(String jiffyStand) {
		this.jiffyStand = jiffyStand;
	}

	public String getIdleCount() {
		return idleCount;
	}

	public void setIdleCount(String idleCount) {
		this.idleCount = idleCount;
	}

	public String getIdleSpaces() {
		return idleSpaces;
	}

	public void setIdleSpaces(String idleSpaces) {
		this.idleSpaces = idleSpaces;
	}

	private String jiffyStand; // 停车架
	private String idleCount; // 空闲车位和
	private String idleSpaces; // 空闲车位名称
}
