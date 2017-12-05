/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.dao.todolist;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.rainy.cglcrm.common.persistence.CrudDao;
import com.rainy.cglcrm.common.persistence.annotation.MyBatisDao;
import com.rainy.cglcrm.modules.crm.entity.todolist.CrmTodoTask;

/**
 * 待办任务DAO接口
 * @author rainy
 * @version 2017-11-19
 */
@MyBatisDao
public interface CrmTodoTaskDao extends CrudDao<CrmTodoTask> {

	int updateDealState(CrmTodoTask ct);

	int updateCustomDealDate(@Param("id")String id,@Param("sysDealTime") Date sysDealTime,@Param("lastContactTime") Date lastContactTime);
	
}