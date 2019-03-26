package com.sxt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxt.dto.BasicDto;
import com.sxt.mapper.BasicDataMapper;
import com.sxt.pojo.BasicData;
import com.sxt.pojo.BasicDataExample;
import com.sxt.service.IBasicService;

@Service
public class BasicServiceImpl implements IBasicService {
	
	@Resource
	private BasicDataMapper basicMapper;

	/**
	 * 带条件的查询
	 */
	@Override
	public List<BasicData> query(BasicData bd) {
		BasicDataExample example = new BasicDataExample();
		
		return basicMapper.selectByExample(example );
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageInfo<BasicData> queryPage(BasicDto dto) {
		PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		List<BasicData> list = this.query(dto.getBasicData());
		return new PageInfo<>(list);
	}

	@Override
	public void addBasicData(BasicData bd) {
		// TODO Auto-generated method stub
		basicMapper.insertSelective(bd);
	}

	@Override
	public void deleteBasicData(int id) {
		// TODO Auto-generated method stub
		basicMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateBasicData(BasicData bd) {
		// TODO Auto-generated method stub
		basicMapper.updateByPrimaryKeySelective(bd);
	}

	@Override
	public void getUpdateInfo(Integer id, Model model) {
		// 1.查询所有的大类信息
		BasicDataExample example = new BasicDataExample();
		example.createCriteria().andParentIdIsNull();
		List<BasicData> parents = basicMapper.selectByExample(example );
		model.addAttribute("parents", parents);
		if(id!=null && id > 0){
			// 2.表示更新数据
			BasicData data = basicMapper.selectByPrimaryKey(id);
			model.addAttribute("basic", data);
		}
	}

	/**
	 * 根据大类名称 查询该大类所属的所有的小类
	 */
	@Override
	public List<BasicData> getBasicDataByParentName(String basicCommonInterval) {
		
		return basicMapper.getBasicDataByParentName(basicCommonInterval);
	}

}
