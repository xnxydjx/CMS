package com.briup.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Link;
import com.briup.demo.bean.LinkExample;
import com.briup.demo.bean.LinkExample.Criteria;
import com.briup.demo.mapper.LinkMapper;
import com.briup.demo.service.ILinkService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;
/**
 * 操作链接的service功能类
 * @author DJX
 */
@Service//将类放入IOC容器中，事务管理
public class LinkServiceImpl implements ILinkService {
	@Autowired
	private LinkMapper linkMapper;
	@Override
	public void saveOrUpdateLink(Link link) throws CustomerException {
		//参数为引用类型，要做判空处理
		if (link==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE,"参数为空！");
		}
		//判断link对象的id是否为空，为空新增链接，非空修改链接
		if (link.getId()==null) {			
			linkMapper.insert(link);
		}else {
			linkMapper.updateByPrimaryKey(link);
		}
	}
	@Override
	public List<Link> findAllLinks() throws CustomerException {
		return linkMapper.selectByExample(new LinkExample());
	}
	@Override
	public void deleteLinkById(Integer id) throws CustomerException {
		linkMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<Link> findLinksByName(String name) throws CustomerException {
		name = name == null ? "" : name.trim();
		LinkExample example = new LinkExample();
		if ("".equals(name)) {
			//如果搜索条件不写，返回所有数据
			return linkMapper.selectByExample(example);
		}else {
			//返回满足条件的数据
			example.createCriteria().andNameLike("%"+name+"%");//创建条件对象，添加条件
			return linkMapper.selectByExample(example);
		}
	}
}
