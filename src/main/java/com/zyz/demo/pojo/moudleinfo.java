package com.zyz.demo.pojo;

import lombok.Data;

import java.util.List;


@Data
public class moudleinfo {
    private Integer moudleid;
    private String moudlename;
    private String name; //负责人名称
    private String parentid;
    private String moudleopentime;
    private String moudlestatus;
    private String type;
    public List<moudleinfo> children;
}
