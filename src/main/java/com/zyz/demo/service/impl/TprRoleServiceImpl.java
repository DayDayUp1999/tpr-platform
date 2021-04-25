package com.zyz.demo.service.impl;

import com.zyz.demo.entity.TprRole;
import com.zyz.demo.mapper.TprRoleMapper;
import com.zyz.demo.service.TprRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class TprRoleServiceImpl implements TprRoleService {

    @Resource
    private TprRoleMapper tprRoleMapper;

    @Override
    public List<TprRole> findall() {
        List<TprRole> rolelist=tprRoleMapper.findall();
        return rolelist;
    }

    @Override
    public int addRole(String roleId, String name, String info) {
        HashMap map=new HashMap();
        int roleid=Integer.parseInt(roleId);
        int flag=0;
        map.put("roleId",roleid);
        map.put("name",name);
        map.put("info",info);
        try{
            flag=tprRoleMapper.addRole(map);
        } catch (Exception e) {
            throw new RuntimeException("用户ID重复");
        }

        return flag;
    }

    @Override
    public int delRole(String roleId) {
        int ID=Integer.parseInt(roleId);
        int flag=tprRoleMapper.delRole(ID);
        return flag;
    }

    @Override
    public List<TprRole> findByname(String name) {
        List<TprRole> rolelist=tprRoleMapper.findByname(name);
        return rolelist;
    }


}
