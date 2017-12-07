/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.service.todolist;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rainy.cglcrm.common.persistence.Page;
import com.rainy.cglcrm.common.service.CrudService;
import com.rainy.cglcrm.modules.crm.entity.custom.CrmCustom;
import com.rainy.cglcrm.modules.crm.entity.todolist.CrmTodoTask;
import com.rainy.cglcrm.modules.crm.dao.todolist.CrmTodoTaskDao;

/**
 * 待办任务Service
 * @author rainy
 * @version 2017-11-19
 */
@Service
@Transactional(readOnly = true)
public class CrmTodoTaskService extends CrudService<CrmTodoTaskDao, CrmTodoTask> {

	public CrmTodoTask get(String id) {
		return super.get(id);
	}
	
	public List<CrmTodoTask> findList(CrmTodoTask crmTodoTask) {
		return super.findList(crmTodoTask);
	}
	
	public Page<CrmTodoTask> findPage(Page<CrmTodoTask> page, CrmTodoTask crmTodoTask) {
		//市场推广人员能看到全部，其他人只能看本人的
		if(crmTodoTask.getCurrentUser().getRoleIdList().contains("e8b93c6833494eb8b7244ef8e25cf55a")) {
			crmTodoTask.getSqlMap().put("sctgRole", "not null");
		}
		return super.findPage(page, crmTodoTask);
	}
	
	@Transactional(readOnly = false)
	public void save(CrmTodoTask crmTodoTask) {
		super.save(crmTodoTask);
	}
	
	@Transactional(readOnly = false)
	public void delete(CrmTodoTask crmTodoTask) {
		super.delete(crmTodoTask);
	}
	@Transactional(readOnly = false)
	public boolean updateDealState(String id,String crmCustomId, String remark,String dealType) {
		Date nowDate = new Date();
		CrmTodoTask ct = new CrmTodoTask();
		ct.setId(id);
		
		ct.setUpdatePerson(ct.getCurrentUser().getId());
		ct.setUpdateTime(nowDate);
		ct.setRemark(remark);
		int i = 0;
		int j = 0;
		if("ignore".equals(dealType)) {//忽略
			ct.setDealState("2");
			//更新状态
			i = dao.updateDealState(ct);
		}else if("deal".equals(dealType)) {//已处理
			ct.setDealState("1");
			//系统处理时间
			//最后一次联系时间
			j = dao.updateCustomDealDate(crmCustomId,nowDate,nowDate);
			//更新状态
			i = dao.updateDealState(ct);
		}else if("waitDeal".equals(dealType)) {//待处理
			ct.setDealState("0");
			//不回写最后一次联系时间
			j = dao.updateCustomDealDate(crmCustomId,nowDate,null);
			//更新状态
			i = dao.updateDealState(ct);
		}
		
		if(i+j>=1) {
			return true;
		}else {
			return false;
		}
	}
	
}