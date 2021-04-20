package com.zyz.demo.mapper;



import java.util.HashMap;


public interface TprPowerMapper {
    int updateRolepowerByRoleId(HashMap powerinfo);
    String findCheckedPowerList(int roleId);
}
