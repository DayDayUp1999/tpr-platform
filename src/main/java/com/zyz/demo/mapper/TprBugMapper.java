package com.zyz.demo.mapper;

import java.util.List;
import java.util.Map;

public interface TprBugMapper {
     List<Map> getbuglist();
     List<Map> getbuglistByname(String projectname);
     List<Map> getbugsolvedlist(String projectname);
     List<Map> bugfollow();

     List<Map> bugfollownewlj(String activatedDate);
     List<Map> bugfollowsolvedlj(String resolvedDate);
}
