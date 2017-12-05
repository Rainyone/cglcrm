/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.entity.todolist;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.rainy.cglcrm.common.persistence.DataEntity;
import com.rainy.cglcrm.modules.crm.entity.custom.CrmCustom;
import com.rainy.cglcrm.modules.sys.entity.User;

/**
 * 待办任务Entity
 * @author rainy
 * @version 2017-11-19
 */
public class CrmTodoTask extends DataEntity<CrmTodoTask> {
	
	private static final long serialVersionUID = 1L;
	private CrmCustom crmCustom;		// 客户id
	private String dealState;		// 处理状态（0待处理1已处理2已忽略）
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String createPerson;		// 创建人
	private String updatePerson;		// 更新人
	private String state;		// 状态（1正常0删除）
	private Date beginUpdateTime;		// 开始 更新时间
	private Date endUpdateTime;		// 结束 更新时间
	private User chargePerson;		// 负责人
	private String remark;	//说明
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(User chargePerson) {
		this.chargePerson = chargePerson;
	}

	public CrmCustom getCrmCustom() {
		return crmCustom;
	}

	public void setCrmCustom(CrmCustom crmCustom) {
		this.crmCustom = crmCustom;
	}

	public CrmTodoTask() {
		super();
	}

	public CrmTodoTask(String id){
		super(id);
	}

	
	@Length(min=0, max=1, message="处理状态（0待处理1已处理2已忽略）长度必须介于 0 和 1 之间")
	public String getDealState() {
		return dealState;
	}

	public void setDealState(String dealState) {
		this.dealState = dealState;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=64, message="创建人长度必须介于 0 和 64 之间")
	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	
	@Length(min=0, max=64, message="更新人长度必须介于 0 和 64 之间")
	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	
	@Length(min=0, max=1, message="状态（1正常0删除）长度必须介于 0 和 1 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public Date getBeginUpdateTime() {
		return beginUpdateTime;
	}

	public void setBeginUpdateTime(Date beginUpdateTime) {
		this.beginUpdateTime = beginUpdateTime;
	}
	
	public Date getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(Date endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}
		
}