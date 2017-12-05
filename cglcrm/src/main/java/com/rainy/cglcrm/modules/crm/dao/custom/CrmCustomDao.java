/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.dao.custom;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rainy.cglcrm.common.persistence.CrudDao;
import com.rainy.cglcrm.common.persistence.annotation.MyBatisDao;
import com.rainy.cglcrm.modules.crm.entity.custom.CrmCustom;
import com.rainy.cglcrm.modules.crm.entity.product.CrmProduct;

/**
 * 客户关系管理DAO接口
 * @author rainy
 * @version 2017-11-19
 */
@MyBatisDao
public interface CrmCustomDao extends CrudDao<CrmCustom> {
	int updateZhouqi(@Param("customId")String customId,@Param("emailCycle")String emailCycle);

	int updateAllEmailCycle(@Param("ids")String ids,@Param("emailCycle") String emailCycle);

	CrmProduct getProduct(@Param("productName")String productName,@Param("productId") String productId);

	int saveProduct(CrmProduct cpsave);

	List<CrmCustom> findCustomByTodo(@Param("name")String name, @Param("beginTime")Date beginUpdateTime, @Param("endTime")Date endUpdateTime,@Param("currentId")String currentId,@Param("dealState")String dealState); 
}