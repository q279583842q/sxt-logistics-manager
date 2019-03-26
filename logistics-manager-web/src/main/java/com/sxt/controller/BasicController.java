package com.sxt.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.sxt.dto.BasicDto;
import com.sxt.pojo.BasicData;
import com.sxt.service.IBasicService;
import com.sxt.utils.Constant;

@Controller
@RequestMapping("/basic")
public class BasicController {

	@Resource
	private IBasicService basicService;
	
	@RequestMapping("/queryPage")
	public String queryPage(BasicDto dto,Model model){
		PageInfo<BasicData> pageInfo = basicService.queryPage(dto);
		model.addAttribute(Constant.PAGEHELP_MODEL, pageInfo);
		return "basic/basic";
	}
	
	/**
	 * 进入添加或者修改前的界面
	 *    准备添加或者修改的数据
	 *    添加
	 *    	查询出所有的大类数据
	 *    修改
	 *    	查询出所有的大类数据
	 *    	根据id查询出对应的基础数据信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/basicUpdate")
	public String basicUpdate(Integer id,Model model){
		basicService.getUpdateInfo(id,model);
		return "basic/basicUpdate";
	}
	
	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(BasicData bd){
		if(bd.getParentId() == 0){
			// 表示这条数据是大类数据
			bd.setParentId(null);
		}
		if(bd.getBaseId() != null && bd.getBaseId() > 0 ){
			//表示更新数据
			basicService.updateBasicData(bd);
		}else{
			// 添加数据
			basicService.addBasicData(bd);
		}
		return "redirect:/basic/queryPage";
	}
	@RequestMapping("/delete")
	public String delete(Integer id){
		basicService.deleteBasicData(id);
		return "redirect:/basic/queryPage";
	}
}
