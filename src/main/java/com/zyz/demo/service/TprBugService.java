package com.zyz.demo.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface TprBugService {
    public List<Map> getbuglist();
    public List<Map> getbuglistByname(String projectname);

    public List<Map> getbugsolvedlist(String projectname);

    public List<Map> bugfollow();
    public List<Map> bugfollowlj() throws ParseException;
}
