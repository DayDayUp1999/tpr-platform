package com.zyz.demo.pojo;

import lombok.Data;

@Data
public class caseinfo {
    private String caseId;
    private Integer projectBy;
    private Integer moudleBy;
    private String title;
    private String pri;
    private String type;
    private String casestatus;
    private String openedBy;
    private String openedDate;
    private String reviewedBy;
    private String reviewedDate;
    private String lastEditedBy;
    private String lastEditedDate;
    private String lastRuner;
    private String lastRunDate;
    private String lastRunResult;

}
