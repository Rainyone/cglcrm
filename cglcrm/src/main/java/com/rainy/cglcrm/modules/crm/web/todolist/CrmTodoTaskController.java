/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.web.todolist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rainy.cglcrm.common.config.Global;
import com.rainy.cglcrm.common.persistence.Page;
import com.rainy.cglcrm.common.web.BaseController;
import com.rainy.cglcrm.common.utils.DateUtils;
import com.rainy.cglcrm.common.utils.StringUtils;
import com.rainy.cglcrm.common.utils.excel.ExportExcel;
import com.rainy.cglcrm.modules.crm.entity.custom.CrmCustom;
import com.rainy.cglcrm.modules.crm.entity.product.CrmProduct;
import com.rainy.cglcrm.modules.crm.entity.todolist.CrmTodoTask;
import com.rainy.cglcrm.modules.crm.service.custom.CrmCustomService;
import com.rainy.cglcrm.modules.crm.service.todolist.CrmTodoTaskService;
import com.rainy.cglcrm.modules.sys.utils.DictUtils;

/**
 * 待办任务Controller
 * @author rainy
 * @version 2017-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/crm/todolist/crmTodoTask")
public class CrmTodoTaskController extends BaseController {

	@Autowired
	private CrmTodoTaskService crmTodoTaskService;
	@Autowired
	private CrmCustomService crmCustomService;
	@ModelAttribute
	public CrmTodoTask get(@RequestParam(required=false) String id) {
		CrmTodoTask entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = crmTodoTaskService.get(id);
		}
		if (entity == null){
			entity = new CrmTodoTask();
		}
		return entity;
	}
	
	@RequiresPermissions("crm:todolist:crmTodoTask:view")
	@RequestMapping(value = {"list", ""})
	public String list(CrmTodoTask crmTodoTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CrmTodoTask> page = crmTodoTaskService.findPage(new Page<CrmTodoTask>(request, response), crmTodoTask); 
		model.addAttribute("page", page);
		model.addAttribute("dealState",crmTodoTask.getDealState());
		return "modules/crm/todolist/crmTodoTaskList";
	}
	@RequiresPermissions("crm:todolist:crmTodoTask:view")
	@RequestMapping(value = "todoTaskUpdate")
	public String tooTaskUpdate(CrmTodoTask crmTodoTask, Model model,HttpServletRequest request) {
		return "modules/crm/todolist/todoTaskUpdate";
	}
	
	@RequiresPermissions("crm:todolist:crmTodoTask:edit")
	@RequestMapping(value = "todoTaskUpdateById",method=RequestMethod.POST)
	@ResponseBody
	public String todoTaskUpdateById(String taskid,String crmCustomId,String remark,String dealType) {
		boolean result = false;
		if(StringUtils.isNotBlank(taskid)&&StringUtils.isNotBlank(crmCustomId)) {
			result = crmTodoTaskService.updateDealState(taskid,crmCustomId,remark,dealType);
		}
		return result?"{success:'ok'}":"{success:'false'}";
	}
	@RequiresPermissions("crm:todolist:crmTodoTask:edit")
	@RequestMapping(value = "moreTodoTaskUpdateById",method=RequestMethod.POST)
	@ResponseBody
	public String moreTodoTaskUpdateById(String ids,String remark,String dealType) {
		boolean result = false;
		if(StringUtils.isNotBlank(ids)) {
			String[] taskIds = ids.split(",");
			for(String id:taskIds) {
				String[] idt = id.split("\\|");
				String taskid = idt[0];
				String crmCustomId = idt[1];
				boolean r = crmTodoTaskService.updateDealState(taskid,crmCustomId,remark,dealType);
				result = result||r;
			}
		}
		return result?"{success:'ok'}":"{success:'false'}";
	}
	
/*	@RequiresPermissions("crm:todolist:crmTodoTask:view")
	@RequestMapping(value = "form")
	public String form(CrmTodoTask crmTodoTask, Model model) {
		model.addAttribute("crmTodoTask", crmTodoTask);
		return "modules/crm/todolist/crmTodoTaskForm";
	}*/
	@RequiresPermissions("crm:custom:crmCustom:view")
	@RequestMapping(value = "form")
	public String form(CrmCustom crmCustom, Model model) {
		model.addAttribute("crmCustom", crmCustom);
		return "modules/crm/todolist/crmTodoTaskForm";
	}
	@RequiresPermissions("crm:todolist:crmTodoTask:edit")
	@RequestMapping(value = "deleteFun")
	@ResponseBody
	public String deleteFun(String del_ids, Model model, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(del_ids)) {
			String[] taskIds = del_ids.split(",");
			for(String id:taskIds) {
				CrmTodoTask crmTodoTask = new CrmTodoTask();
				crmTodoTask.setId(id);
				crmTodoTaskService.delete(crmTodoTask);
			}
		}
		return "{success:'ok'}";
	}
	
	@RequiresPermissions("crm:todolist:crmTodoTask:edit")
	@RequestMapping(value = "delete")
	public String delete(CrmTodoTask crmTodoTask, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String id = request.getParameter("taskid");
		crmTodoTask.setId(id);
		crmTodoTaskService.delete(crmTodoTask);
		addMessage(redirectAttributes, "删除待办任务成功");
		return "redirect:"+Global.getAdminPath()+"/crm/todolist/crmTodoTask/?repage";
	}

	/**
	 * 导出数据
	 * @param crmCustom
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("crm:custom:crmCustom:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CrmTodoTask crmTodoTask, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CrmCustom> page =  crmCustomService.findCustomByTodo(crmTodoTask);
            //对关注的产品转换为名称
            if(page!=null&&page.getList()!=null&&page.getList().size()>0){
            	//遍历客户列表
            	for(CrmCustom cc:page.getList()) {
            		String ids = cc.getFocusProducts();
            		String names = "";
            		//遍历产品id
            		if(StringUtils.isNotBlank(ids)) {
            			String[] prodIds = ids.split(",");
            			for(String id:prodIds) {
            				CrmProduct cp = crmCustomService.getProduct(null, id);
            				if(cp!=null) {
            					String name = cp.getProductName();
            					if(StringUtils.isNotBlank(name)) {
            						if(StringUtils.isNotBlank(names)) {
            							names += "," + name;
            						}else {
            							names = name;
            						}
            					}
            				}
            			}
            		}
            		cc.setFocusProducts(names);
            	}
            }
    		new ExportExcel("客户数据", CrmCustom.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出客户数据失败！失败信息："+e.getMessage());
		}
		return "redirect:" + Global.getAdminPath()+  "/crm/todolist/crmTodoTask/?repage";
    }
}