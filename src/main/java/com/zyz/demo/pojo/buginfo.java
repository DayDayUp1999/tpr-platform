package com.zyz.demo.pojo;

import lombok.Data;

@Data
public class buginfo {
    private String bugId;
    private Integer projectBy;
    private Integer moudleBy;
    private String title;
    private String pri;
    private String fromcase;
    private String severity;
    private String bugstatus;
    private String activatedDate;
    private String resolvedBy;
    private String resolvedDate;
}
