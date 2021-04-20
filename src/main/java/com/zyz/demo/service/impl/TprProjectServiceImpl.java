package com.zyz.demo.service.impl;

import com.zyz.demo.entity.TprUser;
import com.zyz.demo.mapper.TprProjectMapper;
import com.zyz.demo.mapper.TprUserMapper;
import com.zyz.demo.mytools.getMoudleTree;
import com.zyz.demo.pojo.moudleinfo;
import com.zyz.demo.service.TprProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TprProjectServiceImpl implements TprProjectService {


    @Resource
    private TprProjectMapper tprProjectMapper;

    @Resource
    private TprUserMapper tprUserMapper;

    @Override
    public HashMap getmoudleinfo() {
        List<moudleinfo> list =tprProjectMapper.getmoudleinfo();
        List<moudleinfo> treelist= getMoudleTree.getDepartmentTree(list);
        HashMap map=new HashMap();
        map.put("treelist",treelist);
        map.put("size",list.size());
        return map;
    }

    @Override
    public int addmoudle(Map infomap) {
        TprUser user=tprUserMapper.findbyname((String)infomap.get("moudlechargename"));
        infomap.put("moudlecharge",user.getUserId());
        System.out.println("service map"+infomap);
        int flag=tprProjectMapper.addmoudle(infomap);
        return flag;
    }

    @Override
    public int deletemoudle(String moudleid) {
        int flag=tprProjectMapper.deletemoudle(moudleid);
        return flag;
    }

    @Override
    public int updatemoudle(Map map) {
        TprUser user=tprUserMapper.findbyname((String)map.get("moudlechargename"));
        map.put("moudlecharge",user.getUserId());
        int flag=tprProjectMapper.updatemoudle(map);
        return flag;
    }

    @Override
    public HashMap findBymoudlename(String moudlename) {
        List<moudleinfo> list =tprProjectMapper.findBymoudlename(moudlename);
        HashMap map=new HashMap();
        if(moudlename == "" ||moudlename.equals("")){
            List<moudleinfo> treelist= getMoudleTree.getDepartmentTree(list);
            map.put("treelist",treelist);
        }else {
            map.put("treelist",list);
        }
        map.put("size",list.size());
        return map;
    }
}
