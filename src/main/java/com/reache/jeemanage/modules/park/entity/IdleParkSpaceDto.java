package com.reache.jeemanage.modules.park.entity;

import java.util.ArrayList;
import java.util.List;

import com.reache.jeemanage.common.persistence.DataEntity;

public class IdleParkSpaceDto extends DataEntity<IdleParkSpaceDto> {

	private static final long serialVersionUID = 1L;
	private String floor; // 楼层
	private String jiffyStand; // 停车架
	private String count; // 总车位
	private String idleCount; // 空闲车位和
	private List<ParkSpace> inuseSpaces =new ArrayList<ParkSpace>(); // 在用车位名称
	private List<ParkSpace> idleSpaces=new ArrayList<ParkSpace>(); ; // 空闲车位名称

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

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<ParkSpace> getInuseSpaces() {
		return inuseSpaces;
	}

	public void setInuseSpaces(List<ParkSpace> inuseSpaces) {
		this.inuseSpaces = inuseSpaces;
	}

	public List<ParkSpace> getIdleSpaces() {
		return idleSpaces;
	}

	public void setIdleSpaces(List<ParkSpace> idleSpaces) {
		this.idleSpaces = idleSpaces;
	}

}
