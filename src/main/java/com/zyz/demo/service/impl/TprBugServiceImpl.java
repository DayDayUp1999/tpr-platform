package com.zyz.demo.service.impl;

import com.zyz.demo.mapper.TprBugMapper;
import com.zyz.demo.pojo.buginfo;
import com.zyz.demo.pojo.caseinfo;
import com.zyz.demo.service.TprBugService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.zyz.demo.mytools.dateToformat.changedate;

@Service
public class TprBugServiceImpl implements TprBugService {

    @Resource
    private TprBugMapper tprBugMapper;

    @Override
    public List<Map> getbuglist() {
        List<Map> list = tprBugMapper.getbuglist();
        return list;
    }

    @Override
    public List<Map> getbuglistByname(String projectname) {
        List<Map> list = tprBugMapper.getbuglistByname(projectname);
        List<String> names = new ArrayList<>();
        //获取所有模块名字
        for (Map item : list) {
            names.add((String) item.get("moudlename"));
        }
        //去重
        List<String> realnames = names.stream().distinct().collect(Collectors.toList());
        List<Map> test = new ArrayList<>();
        //重新装入
        for (int i = 0; i < realnames.size(); i++) {
            Map temp = new HashMap();
            temp.put("moudlename", realnames.get(i));
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).get("moudlename").equals(realnames.get(i))) {
                    if (list.get(j).get("severity").equals("致命")) {
                        temp.put("zm", list.get(j).get("number"));
                    }
                    if (list.get(j).get("severity").equals("严重")) {
                        temp.put("yz", list.get(j).get("number"));
                    }
                    if (list.get(j).get("severity").equals("一般")) {
                        temp.put("yb", list.get(j).get("number"));
                    }
                }
            }
            test.add(temp);
        }
        return test;
    }

    @Override
    public List<Map> getbugsolvedlist(String projectname) {
        List<Map> list = tprBugMapper.getbugsolvedlist(projectname);
        List<String> names = new ArrayList<>();
        //获取所有模块名字
        for (Map item : list) {
            names.add((String) item.get("moudlename"));
        }
        //去重
        List<String> realnames = names.stream().distinct().collect(Collectors.toList());
        List<Map> test = new ArrayList<>();
        //重新装入
        for (int i = 0; i < realnames.size(); i++) {
            Map temp = new HashMap();
            temp.put("moudlename", realnames.get(i));
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).get("moudlename").equals(realnames.get(i))) {
                    if (list.get(j).get("bugstatus").equals("未解决")) {
                        temp.put("wjj", list.get(j).get("number"));
                    }
                    if (list.get(j).get("bugstatus").equals("已解决")) {
                        temp.put("yjj", list.get(j).get("number"));
                    }
                    if (list.get(j).get("bugstatus").equals("解决中")) {
                        temp.put("jjz", list.get(j).get("number"));
                    }
                }
            }
            test.add(temp);
        }
        return test;
    }

    @Override
    public List<Map> bugfollow() {
        List<Map> list = tprBugMapper.bugfollow();

        List<String> activatedDatelist = new ArrayList<>();
        List<String> resolvedDateDatelist = new ArrayList<>();

        //获取所有BUG的日期
        for (Map item : list) {
            activatedDatelist.add(changedate(item.get("activatedDate").toString()));
            if (item.get("bugstatus").equals("已解决")) {
                resolvedDateDatelist.add(changedate(item.get("resolvedDate").toString()));
            }
        }

        //去重
        List<String> realactivatedDatelist = activatedDatelist.stream().distinct().collect(Collectors.toList());
        List<String> realresolvedDateDatelist = resolvedDateDatelist.stream().distinct().collect(Collectors.toList());
        //根据去重后的list，判断日期大小,确定循环大小
        List<String> datelist = new ArrayList<>();
        if (realactivatedDatelist.size() > realresolvedDateDatelist.size()) {
            datelist = realactivatedDatelist;
        } else {
            datelist = realresolvedDateDatelist;
        }

        List<Map> test = new ArrayList<>();
        //重新装入
        for (int i = 0; i < datelist.size(); i++) {
            Map temp = new HashMap();
            temp.put("date", datelist.get(i));
            temp.put("newbugnumber", Collections.frequency(activatedDatelist, datelist.get(i)));
            temp.put("solvedbugnumber", Collections.frequency(resolvedDateDatelist, datelist.get(i)));
            test.add(temp);
        }
        return test;
    }

    @Override
    public List<Map> bugfollowlj() throws ParseException {
        List<Map> list = tprBugMapper.bugfollow();
        List<String> activatedDatelist = new ArrayList<>();
        List<String> resolvedDateDatelist = new ArrayList<>();
        //获取所有BUG的日期
        for (Map item : list) {
            activatedDatelist.add(item.get("activatedDate").toString());
            if (item.get("bugstatus").equals("已解决")) {
                resolvedDateDatelist.add(item.get("resolvedDate").toString());
            }
        }
        //去重
        List<String> realactivatedDatelist = activatedDatelist.stream().distinct().collect(Collectors.toList());
        List<String> realresolvedDateDatelist = resolvedDateDatelist.stream().distinct().collect(Collectors.toList());
        //根据去重后的list，判断日期大小,确定循环大小
        List<String> datelist = new ArrayList<>();
        if (realactivatedDatelist.size() > realresolvedDateDatelist.size()) {
            datelist = realactivatedDatelist;
        } else {
            datelist = realresolvedDateDatelist;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<Map> test = new ArrayList<>();
        int solvednumberlj=0;
        for (int i = 0; i < datelist.size(); i++){
            //时间+1天
            int newnumberlj=0;
            List<Integer> solvednumberlist=new ArrayList<>();
            Date date = sdf.parse(datelist.get(i));
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            rightNow.add(Calendar.DAY_OF_MONTH, 1);
            Date dt1 = rightNow.getTime();
            System.out.println("dt1>>>>"+dt1.toString());
            String reStr = sdf.format(dt1);
            List<Map> list1 = tprBugMapper.bugfollownewlj(reStr);
            newnumberlj=list1.size();

            List<Map> list2 = tprBugMapper.bugfollowsolvedlj(reStr);

            for (int j=0;j<list2.size();j++){
                Date date1 = sdf.parse(datelist.get(i));
                String temp1=sdf.format(date1);
                Date temp=sdf.parse(list2.get(j).get("resolvedDate").toString());
                String temp2=sdf.format(temp);
                if(temp1.equals(temp2)&&(list2.get(j).get("bugstatus").equals("已解决"))){
                    int solvednumber=0;
                    solvednumber++;
                    solvednumberlist.add(solvednumber);
                }
            }
            solvednumberlj+=solvednumberlist.stream().reduce(Integer::sum).orElse(0);;
            Map temp = new HashMap();
            temp.put("date", changedate(datelist.get(i)));
            temp.put("newbugnumberlj",newnumberlj);
            temp.put("solvedbugnumberlj",solvednumberlj);
            test.add(temp);
        }
        return test;
    }


}
