/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.service.custom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rainy.cglcrm.common.persistence.Page;
import com.rainy.cglcrm.common.service.CrudService;
import com.rainy.cglcrm.common.utils.IdGen;
import com.rainy.cglcrm.common.utils.StringUtils;
import com.rainy.cglcrm.modules.crm.entity.custom.CrmCustom;
import com.rainy.cglcrm.modules.crm.entity.product.CrmProduct;
import com.rainy.cglcrm.modules.crm.entity.todolist.CrmTodoTask;
import com.rainy.cglcrm.modules.crm.dao.custom.CrmCustomDao;

/**
 * 客户关系管理Service
 * @author rainy
 * @version 2017-11-19
 */
@Service
@Transactional(readOnly = true)
public class CrmCustomService extends CrudService<CrmCustomDao, CrmCustom> {

	
	public CrmCustom get(String id) {
		CrmCustom crmCustom = super.get(id);
		return crmCustom;
	}
	
	public List<CrmCustom> findList(CrmCustom crmCustom) {
		return super.findList(crmCustom);
	}
	
	public Page<CrmCustom> findPage(Page<CrmCustom> page, CrmCustom crmCustom) {
		return super.findPage(page, crmCustom);
	}
	
	@Transactional(readOnly = false)
	public void save(CrmCustom crmCustom) {
		super.save(crmCustom);
	}
	
	@Transactional(readOnly = false)
	public void delete(CrmCustom crmCustom) {
		super.delete(crmCustom);
	}
	@Transactional(readOnly = false)
	public boolean updateZhouqi(String customId, String emailCycle) {
		int result = dao.updateZhouqi(customId, emailCycle);
		if(result>0) {
			return true;
		}else {
			return false;
		}
	}
	@Transactional(readOnly = false)
	public boolean updateAllEmailCycle(String ids, String emailCycle) {
		int result = dao.updateAllEmailCycle(ids, emailCycle);
		if(result>0) {
			return true;
		}else {
			return false;
		}
	}

	public CrmProduct getProduct(String productName,String productId) {
		CrmProduct crmProd = dao.getProduct(productName,productId);
		return crmProd;
	}

	public String saveProduct(CrmProduct cpsave) {
		String id = IdGen.uuid();
		cpsave.setId(id);
		if(dao.saveProduct(cpsave)>0) {
			return id;
		}
		return null;
	}

	public Page<CrmCustom> findCustomByTodo(CrmTodoTask crmTodoTask) {
		Page<CrmCustom> page = new Page<CrmCustom>();
		String currentId = "";
		if(!crmTodoTask.getCurrentUser().getRoleIdList().contains("e8b93c6833494eb8b7244ef8e25cf55a")) {
			currentId = crmTodoTask.getCurrentUser().getId();
		}
		
		page.setList(dao.findCustomByTodo(crmTodoTask.getCrmCustom().getName(),crmTodoTask.getBeginUpdateTime(),crmTodoTask.getEndUpdateTime(),currentId,crmTodoTask.getDealState()));
		return page;
	}
	
}