package com.zyz.demo.service.impl;

import com.zyz.demo.entity.TprUser;
import com.zyz.demo.mapper.TprProjectMapper;
import com.zyz.demo.mapper.TprUserMapper;
import com.zyz.demo.mytools.getMoudleTree;
import com.zyz.demo.pojo.moudleinfo;
import com.zyz.demo.service.TprProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TprProjectServiceImpl implements TprProjectService {


    @Resource
    private TprProjectMapper tprProjectMapper;

    @Resource
    private TprUserMapper tprUserMapper;

    @Override
    public HashMap getmoudleinfo() {
        List<moudleinfo> list =tprProjectMapper.getmoudleinfo();
        List<moudleinfo> treelist= getMoudleTree.getMoudleTree(list);
        HashMap map=new HashMap();
        try{
            list =tprProjectMapper.getmoudleinfo();
            treelist= getMoudleTree.getMoudleTree(list);
        }
        catch (Exception e) {
            throw new RuntimeException("错误");
        }
        finally {
            map.put("treelist",treelist);
            map.put("size",treelist.size());
        }
        return map;
    }

    @Override
    public int addmoudle(Map infomap) {
        TprUser user=tprUserMapper.findbyname((String)infomap.get("moudlechargename"));
        try{
            infomap.put("moudlecharge",user.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("系统中没有该负责人");
        }
        int flag=tprProjectMapper.addmoudle(infomap);
        return flag;
    }

    @Override
    public int deletemoudle(String moudleid) {
        int flag=tprProjectMapper.deletemoudle(moudleid);
        if (moudleid!=""){
            try{
               flag=tprProjectMapper.deletemoudle(moudleid);
            } catch (Exception e) {
                throw new RuntimeException("错误");
            }finally {

            }
        }
        return flag;
    }

    @Override
    public int updatemoudle(Map map) {
        TprUser user=tprUserMapper.findbyname((String)map.get("moudlechargename"));
        try{
            map.put("moudlecharge",user.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("系统中没有该负责人");
        }
        int flag=tprProjectMapper.updatemoudle(map);
        return flag;
    }

    @Override
    public HashMap findBymoudlename(String moudlename)  {
        if(moudlename==""){
            List<moudleinfo> list =tprProjectMapper.getmoudleinfo();
            List<moudleinfo> treelist= getMoudleTree.getMoudleTree(list);
            HashMap map=new HashMap();
            map.put("treelist",treelist);
            map.put("size",treelist.size());
            return map;
        }
        List<moudleinfo> list =tprProjectMapper.getmoudleinfo();
        List<moudleinfo> templist=list;
        List<String> ids=tprProjectMapper.findBymoudlenameid(moudlename);
        Iterator<moudleinfo> iterator = list.iterator();
        List<String> tempParentidlist=new ArrayList<>();
        while (iterator.hasNext()) {
            int flag=0;
            moudleinfo temp = iterator.next();
            String tempParentid="";
            if (temp.getMoudlename().contains(moudlename) && temp.getType().equals("moudle")) {
                tempParentid=temp.getParentid();
                tempParentidlist.add(tempParentid);
                continue;
            }
            if (temp.getMoudlename().contains(moudlename)) {
                continue;
            }
            for (int i=0;i<ids.size();i++){
                if (temp.getParentid().equals(ids.get(i))){
                    flag=1;
                    continue;
                }
            }
            if (flag==1){
                 continue;
            }
            iterator.remove();//使用迭代器的删除方法删除
        }

        for (int i=0;i<tempParentidlist.size();i++){
            moudleinfo temp=new moudleinfo();
            temp=tprProjectMapper.getsingleproject(tempParentidlist.get(i));
            list.add(temp);
        }
        //判断是否全是模块
        int flag=0;
        for (int i=0;i<list.size();i++){
            if (list.get(i).getType().equals("project")){
                flag=1;
            }
        }
        List<moudleinfo> myList = list.stream().distinct().collect(Collectors.toList());

        HashMap map=new HashMap();
        if(moudlename == "" ||moudlename.equals("")){
            List<moudleinfo> treelist= getMoudleTree.getMoudleTree(myList);
            map.put("size",treelist.size());
            map.put("treelist",treelist);
            return map;
        }else if(flag==1 && !map.containsKey("treelist")){
            map.put("treelist",getMoudleTree.getMoudleTree(myList));
            map.put("size",getMoudleTree.getMoudleTree(myList).size());
        }else if (flag==0 && !map.containsKey("treelist"))
        {
            map.put("treelist",myList);
            map.put("size",myList.size());
        }

        return map;
    }
}
