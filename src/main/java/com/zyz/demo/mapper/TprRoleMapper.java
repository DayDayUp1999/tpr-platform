package com.zyz.demo.mapper;

import com.zyz.demo.entity.TprRole;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface TprRoleMapper {
    List<TprRole> findall();
    int addRole(HashMap addinfo);
    int delRole(@Param("roleId") int roleId);
    List<TprRole> findByname(String rolename);
}
