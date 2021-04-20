package com.zyz.demo.service.impl;


import com.zyz.demo.mapper.TprPowerMapper;
import com.zyz.demo.mytools.ListToString;
import com.zyz.demo.mytools.StringtoList;
import com.zyz.demo.service.TprPowerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class TprPowerServiceImpl implements TprPowerService {
    @Resource
    TprPowerMapper tprPowerMapper;

    @Override
    public int updateRolepowerByRoleId(String roleId, String powerString) {
        int ID=Integer.parseInt(roleId);
        System.out.println("powerString::"+powerString);
        HashMap powerinfo=new HashMap();
        powerinfo.put("roleId",ID);
        powerinfo.put("powerString",powerString);
        int flag=tprPowerMapper.updateRolepowerByRoleId(powerinfo);
        return flag;
    }

    @Override
    public List<String> findCheckedPowerList(String roleId) {
        int ID=Integer.parseInt(roleId);
        String powerString=tprPowerMapper.findCheckedPowerList(ID);
        List<String> powerList= StringtoList.ToList(powerString);
        return powerList;
    }
}
