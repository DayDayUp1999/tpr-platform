package com.zyz.demo.service;

import com.zyz.demo.entity.TprRole;

import java.util.HashMap;
import java.util.List;

public interface TprRoleService {
    public List<TprRole> findall();
    public int addRole(String roleId,String name,String info);
    public int delRole(String roleId);
    public List<TprRole> findByname(String name);
}
