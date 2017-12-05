/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.service.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rainy.cglcrm.common.persistence.Page;
import com.rainy.cglcrm.common.service.CrudService;
import com.rainy.cglcrm.modules.crm.entity.product.CrmProduct;
import com.rainy.cglcrm.modules.crm.dao.product.CrmProductDao;

/**
 * 产品管理Service
 * @author rainy
 * @version 2017-11-19
 */
@Service
@Transactional(readOnly = true)
public class CrmProductService extends CrudService<CrmProductDao, CrmProduct> {

	public CrmProduct get(String id) {
		return super.get(id);
	}
	
	public List<CrmProduct> findList(CrmProduct crmProduct) {
		return super.findList(crmProduct);
	}
	
	public Page<CrmProduct> findPage(Page<CrmProduct> page, CrmProduct crmProduct) {
		return super.findPage(page, crmProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(CrmProduct crmProduct) {
		super.save(crmProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(CrmProduct crmProduct) {
		super.delete(crmProduct);
	}
	
}