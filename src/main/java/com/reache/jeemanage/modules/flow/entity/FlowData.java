/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.flow.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.reache.jeemanage.common.persistence.DataEntity;

/**
 * 流程数据共享Entity
 * @author shockw
 * @version 2019-04-01
 */
public class FlowData extends DataEntity<FlowData> {
	
	private static final long serialVersionUID = 1L;
	private String flowId;		// 流水号
	private String summary;		// 摘要
	private String type;		// 种类
	private Date applyTime;		// 时间
	private String status;		// 状态
	private Date beginApplyTime;		// 开始 时间
	private Date endApplyTime;		// 结束 时间
	
	public FlowData() {
		super();
	}

	public FlowData(String id){
		super(id);
	}

	@Length(min=1, max=64, message="流水号长度必须介于 1 和 64 之间")
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	@Length(min=1, max=255, message="摘要长度必须介于 1 和 255 之间")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Length(min=1, max=64, message="种类长度必须介于 1 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="时间不能为空")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	@Length(min=1, max=64, message="状态长度必须介于 1 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginApplyTime() {
		return beginApplyTime;
	}

	public void setBeginApplyTime(Date beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}
	
	public Date getEndApplyTime() {
		return endApplyTime;
	}

	public void setEndApplyTime(Date endApplyTime) {
		this.endApplyTime = endApplyTime;
	}
		
}