package com.zyz.demo.service;

import java.util.List;

public interface TprPowerService {
    public int updateRolepowerByRoleId(String roleId, String powerlist);
    public List<String> findCheckedPowerList(String roleId);
}
