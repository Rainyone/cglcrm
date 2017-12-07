/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.web.custom;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.rainy.cglcrm.common.beanvalidator.BeanValidators;
import com.rainy.cglcrm.common.config.Global;
import com.rainy.cglcrm.common.persistence.Page;
import com.rainy.cglcrm.common.web.BaseController;
import com.rainy.cglcrm.common.utils.DateUtils;
import com.rainy.cglcrm.common.utils.StringUtils;
import com.rainy.cglcrm.common.utils.excel.ExportExcel;
import com.rainy.cglcrm.common.utils.excel.ImportExcel;
import com.rainy.cglcrm.modules.crm.entity.custom.CrmCustom;
import com.rainy.cglcrm.modules.crm.entity.product.CrmProduct;
import com.rainy.cglcrm.modules.crm.service.custom.CrmCustomService;
import com.rainy.cglcrm.modules.sys.entity.Dict;
import com.rainy.cglcrm.modules.sys.entity.User;
import com.rainy.cglcrm.modules.sys.service.SystemService;
import com.rainy.cglcrm.modules.sys.utils.DictUtils;
import com.rainy.cglcrm.modules.sys.utils.UserUtils;

/**
 * 客户关系管理Controller
 * @author rainy
 * @version 2017-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/crm/custom/crmCustom")
public class CrmCustomController extends BaseController {

	@Autowired
	private CrmCustomService crmCustomService;
	
	@ModelAttribute
	public CrmCustom get(@RequestParam(required=false) String id) {
		CrmCustom entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = crmCustomService.get(id);
		}
		if (entity == null){
			entity = new CrmCustom();
		}
		return entity;
	}

	@RequiresPermissions("crm:custom:crmCustom:view")
	@RequestMapping(value = {"crmCustomZhouqi"})
	public String crmCustomZhouqi(CrmCustom crmCustom,HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("customId"+crmCustom.getId()+";value"+ crmCustom.getEmailCycle());
		model.addAttribute("crmCustom", crmCustom);
		return "modules/crm/custom/crmCustomZhouqi";
	}
	
	@RequiresPermissions("crm:custom:crmCustom:edit")
	@RequestMapping(value = "updateCustomZhouqi",method=RequestMethod.POST)
	@ResponseBody
	public String updateCustomZhouqi(String customId,String emailCycle) {
		boolean result = false;
		if(StringUtils.isNotBlank(customId)&&StringUtils.isNotBlank(emailCycle)) {
			result = crmCustomService.updateZhouqi(customId,emailCycle);
		}
		String emailCycleStr = "";
		if(result) {
			emailCycleStr = DictUtils.getDictLabel(emailCycle, "email_cycle", "5");
		}
		return result?"{success:'ok',emailCycleStr:'"+emailCycleStr+"'}":"{success:false}";
	}
	
	@RequiresPermissions("crm:custom:crmCustom:edit")
	@RequestMapping(value = "updateAllEmailCycle",method=RequestMethod.POST)
	@ResponseBody
	public String updateAllEmailCycle(String ids,String emailCycle) {
		boolean result = false;
		if(StringUtils.isNotBlank(ids)&&StringUtils.isNotBlank(emailCycle)) {
			result = crmCustomService.updateAllEmailCycle(ids,emailCycle);
		}
		String emailCycleStr = "";
		if(result) {
			emailCycleStr = DictUtils.getDictLabel(emailCycle, "email_cycle", "5");
		}
		return result?"{success:'ok',emailCycleStr:'"+emailCycleStr+"'}":"{success:false}";
	}
	@RequiresPermissions("crm:custom:crmCustom:view")
	@RequestMapping(value = {"list", ""})
	public String list(CrmCustom crmCustom, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CrmCustom> page = crmCustomService.findPage(new Page<CrmCustom>(request, response), crmCustom); 
		model.addAttribute("page", page);
		return "modules/crm/custom/crmCustomList";
	}

	@RequiresPermissions("crm:custom:crmCustom:view")
	@RequestMapping(value = "form")
	public String form(CrmCustom crmCustom, Model model) {
		model.addAttribute("crmCustom", crmCustom);
		return "modules/crm/custom/crmCustomForm";
	}
	@RequiresPermissions("crm:custom:crmCustom:view")
	@RequestMapping(value = "formView")
	public String formView(CrmCustom crmCustom,String dealState, Model model) {
		model.addAttribute("crmCustom", crmCustom);
		model.addAttribute("dealState",dealState);
		return "modules/crm/custom/crmCustomFormView";
	}

	@RequiresPermissions("crm:custom:crmCustom:edit")
	@RequestMapping(value = "save")
	public String save(CrmCustom crmCustom, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, crmCustom)){
			return form(crmCustom, model);
		}
		if(crmCustom.getSysDealTime()==null) {
			crmCustom.setSysDealTime(new Date());
		}
		if(crmCustom.getLastContactTime()==null) {
			crmCustom.setSysDealTime(new Date());
		}
		crmCustom.setCreateTime(new Date());
		crmCustom.setUpdateTime(new Date());
		crmCustom.setCreatePerson(crmCustom.getCurrentUser().getId());
		crmCustom.setUpdatePerson(crmCustom.getCurrentUser().getId());
		crmCustom.setState("1");
		crmCustomService.save(crmCustom);
		addMessage(redirectAttributes, "保存客户关系管理成功");
		return "redirect:"+Global.getAdminPath()+"/crm/custom/crmCustom/?repage";
	}
	
	@RequiresPermissions("crm:custom:crmCustom:edit")
	@RequestMapping(value = "delete")
	public String delete(CrmCustom crmCustom, RedirectAttributes redirectAttributes) {
		crmCustomService.delete(crmCustom);
		addMessage(redirectAttributes, "删除客户关系管理成功");
		return "redirect:"+Global.getAdminPath()+"/crm/custom/crmCustom/?repage";
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
    public String exportFile(CrmCustom crmCustom, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CrmCustom> page =  crmCustomService.findPage(new Page<CrmCustom>(request, response,-1), crmCustom);
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
		return "redirect:" + Global.getAdminPath()+  "/crm/custom/crmCustom/?repage";
    }

	/**
	 * 导入客户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("crm:custom:crmCustom:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath() + "/crm/custom/crmCustom/?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CrmCustom> list = ei.getDataList(CrmCustom.class);
			for (CrmCustom crmCustom : list){
				try{
					//负责人为空则默认为本人处理
					if(crmCustom.getChargePerson()!=null&&StringUtils.isNotBlank(crmCustom.getChargePerson().getId())) {
						crmCustom.setChargePerson(UserUtils.getUser());
					}else {
						User u = UserUtils.getByLoginName(crmCustom.getChargePerson().getId());
						//如果用户名查不到用户则也默认为当前用户负责
						if(u!=null) {
							crmCustom.setChargePerson(u);
						}else {
							crmCustom.setChargePerson(UserUtils.getUser());
						}
					}
					//处理产品
					if(StringUtils.isNotBlank(crmCustom.getFocusProducts())) {
						//逗号隔开
						String [] products = crmCustom.getFocusProducts().split(",");
						String productIds = "";
						for(String prod:products) {
							CrmProduct cp = crmCustomService.getProduct(prod,null);
							//如果有值则不保存产品表
							if(cp!=null&&cp.getId()!=null&&StringUtils.isNotBlank(cp.getId())) {
								if(StringUtils.isNotBlank(productIds)){
									//如果已经关注过此产品则忽略,否则添加
									if(!productIds.contains(cp.getId())) {
										productIds += "," + cp.getId();
									}
								}else {
									productIds = cp.getId();
								}
							}
							//如果没有查到产品则入库新产品
							else {
								CrmProduct cpsave = new CrmProduct();
								cpsave.setProductName(prod);
								cpsave.setPdesc(prod);
								cpsave.setRemark(prod);
								cpsave.setCreateTime(new Date());
								cpsave.setUpdateTime(new Date());
								cpsave.setCreatePerson(cpsave.getCurrentUser().getId());
								cpsave.setUpdatePerson(cpsave.getCurrentUser().getId());
								cpsave.setState("1");
								String id = crmCustomService.saveProduct(cpsave);
								if(StringUtils.isNotBlank(productIds)){
									//如果已经关注过此产品则忽略,否则添加
									if(!productIds.contains(id)) {
										productIds += "," + id;
									}
								}else {
									productIds = id;
								}
							}
						}
						crmCustom.setFocusProducts(productIds);
					}
					Date nowDate = new Date();
					crmCustom.setSysDealTime(nowDate);
					crmCustom.setCreateTime(nowDate);
					crmCustom.setUpdateTime(nowDate);
					crmCustom.setLastContactTime(nowDate);
					crmCustom.setCreatePerson(UserUtils.getUser().getId());
					crmCustom.setUpdatePerson(UserUtils.getUser().getId());
					crmCustom.setState("1");
					crmCustomService.save(crmCustom);;
					successNum++;
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>客户来源 "+crmCustom.getCustomSource()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>客户来源 "+crmCustom.getCustomSource()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条记录，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/crm/custom/crmCustom/?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("crm:custom:crmCustom:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客户数据导入模板.xlsx";
    		List<CrmCustom> list = Lists.newArrayList();
    		list.add(crmCustomService.get("e995bb886da148409ce037dcc808e8b6"));
    		new ExportExcel("客户数据", CrmCustom.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/crm/custom/crmCustom/?repage";
    }
}