/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.entity.custom;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.rainy.cglcrm.common.persistence.DataEntity;
import com.rainy.cglcrm.common.utils.excel.annotation.ExcelField;
import com.rainy.cglcrm.modules.sys.entity.User;

/**
 * 客户关系管理Entity
 * @author rainy
 * @version 2017-11-19
 */
public class CrmCustom extends DataEntity<CrmCustom> {
	
	private static final long serialVersionUID = 1L;
	private String customSource;		// 客户来源
	private String continent;		// 洲别
	private String country;		// 国家
	private String mainIndustry;		// 主营行业
	private String managementFormat;		// 经营业态
	private String focusProducts;		// 关注产品（系统中产品id，多个用逗号隔开）
	private User chargePerson;		// 负责人（系统中用户）
	private String dealStage;		// 成交阶段
	private String reback;		// 反馈情况
	private Date lastContactTime;		// 最后联系时间
	private String firstName;		// 名字
	private String middleName;		// 中间名
	private String lastName;		// 姓
	private String company;		// 公司名
	private String email;		// 邮箱
	private String site;		// 网站
	private String phone;		// 手机
	private String telephone;		// 电话
	private String remark;		// 备注
	private Date sysDealTime;		// 系统处理时间
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String createPerson;		// 创建人
	private String updatePerson;		// 更新人
	private String state;		// 状态（1正常0删除）
	private Date beginLastContactTime;		// 开始 最后联系时间
	private Date endLastContactTime;		// 结束 最后联系时间
	private String emailCycle;		// 
	private String name;
	private String telEmailSite;
	
	
	public String getTelEmailSite() {
		return telEmailSite;
	}

	public void setTelEmailSite(String telEmailSite) {
		this.telEmailSite = telEmailSite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=64, message="邮件周期")
	@ExcelField(title="邮件周期", align=2, sort=180, dictType="email_cycle")
	public String getEmailCycle() {
		return emailCycle;
	}

	public void setEmailCycle(String emailCycle) {
		this.emailCycle = emailCycle;
	}

	public CrmCustom() {
		super();
	}

	public CrmCustom(String id){
		super(id);
	}

	@Length(min=0, max=100, message="客户来源长度必须介于 0 和 100 之间")
	@ExcelField(title="客户来源", align=2, sort=10)
	public String getCustomSource() {
		return customSource;
	}

	public void setCustomSource(String customSource) {
		this.customSource = customSource;
	}
	
	@Length(min=0, max=64, message="洲别长度必须介于 0 和 64 之间")
	@ExcelField(title="洲别", align=2, sort=30,dictType="continent")
	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}
	
	@Length(min=0, max=64, message="国家长度必须介于 0 和 64 之间")
	@ExcelField(title="国家", align=2, sort=20,dictType="country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=200, message="主营行业长度必须介于 0 和 200 之间")
	@ExcelField(title="主营行业", align=2, sort=40)
	public String getMainIndustry() {
		return mainIndustry;
	}

	public void setMainIndustry(String mainIndustry) {
		this.mainIndustry = mainIndustry;
	}
	
	@Length(min=0, max=255, message="经营业态长度必须介于 0 和 255 之间")
	@ExcelField(title="经营业态", align=2, sort=50)
	public String getManagementFormat() {
		return managementFormat;
	}

	public void setManagementFormat(String managementFormat) {
		this.managementFormat = managementFormat;
	}
	
	@Length(min=0, max=2000, message="关注产品（系统中产品id，多个用逗号隔开）长度必须介于 0 和 2000 之间")
	@ExcelField(title="关注产品", align=2, sort=60)
	public String getFocusProducts() {
		return focusProducts;
	}

	public void setFocusProducts(String focusProducts) {
		this.focusProducts = focusProducts;
	}
	
	@ExcelField(title="负责人",value="chargePerson.name", align=2, sort=70)
	public User getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(User chargePerson) {
		this.chargePerson = chargePerson;
	}
	
	@Length(min=0, max=64, message="成交阶段长度必须介于 0 和 64 之间")
	@ExcelField(title="成交阶段", align=2, sort=80, dictType="deal_stage")
	public String getDealStage() {
		return dealStage;
	}

	public void setDealStage(String dealStage) {
		this.dealStage = dealStage;
	}
	
	@Length(min=0, max=64, message="反馈情况长度必须介于 0 和 64 之间")
	@ExcelField(title="反馈情况", align=2, sort=90, dictType="reback")
	public String getReback() {
		return reback;
	}

	public void setReback(String reback) {
		this.reback = reback;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastContactTime() {
		return lastContactTime;
	}

	public void setLastContactTime(Date lastContactTime) {
		this.lastContactTime = lastContactTime;
	}
	
	@Length(min=0, max=20, message="名字长度必须介于 0 和 20 之间")
	@ExcelField(title="名字", align=2, sort=100)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Length(min=0, max=20, message="中间名长度必须介于 0 和 20 之间")
	@ExcelField(title="中间名", align=2, sort=110)
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	@Length(min=0, max=20, message="姓长度必须介于 0 和 20 之间")
	@ExcelField(title="姓", align=2, sort=120)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Length(min=0, max=100, message="公司名长度必须介于 0 和 100 之间")
	@ExcelField(title="公司名", align=2, sort=130)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@Length(min=0, max=100, message="邮箱长度必须介于 0 和 100 之间")
	@ExcelField(title="邮箱", align=2, sort=140)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=100, message="网站长度必须介于 0 和 100 之间")
	@ExcelField(title="网站", align=2, sort=150)
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	@Length(min=0, max=100, message="手机长度必须介于 0 和 100 之间")
	@ExcelField(title="手机", align=2, sort=160)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=100, message="电话长度必须介于 0 和 100 之间")
	@ExcelField(title="电话", align=2, sort=170)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSysDealTime() {
		return sysDealTime;
	}

	public void setSysDealTime(Date sysDealTime) {
		this.sysDealTime = sysDealTime;
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
	
	public Date getBeginLastContactTime() {
		return beginLastContactTime;
	}

	public void setBeginLastContactTime(Date beginLastContactTime) {
		this.beginLastContactTime = beginLastContactTime;
	}
	
	public Date getEndLastContactTime() {
		return endLastContactTime;
	}

	public void setEndLastContactTime(Date endLastContactTime) {
		this.endLastContactTime = endLastContactTime;
	}
		
}