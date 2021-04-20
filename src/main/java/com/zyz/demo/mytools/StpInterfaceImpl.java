package com.zyz.demo.mytools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.zyz.demo.mapper.TprUserMapper;
import org.springframework.stereotype.Component;
import cn.dev33.satoken.stp.StpInterface;

import javax.annotation.Resource;

/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成sa-token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private TprUserMapper tprUserMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginKey) {
        Map userPermission=tprUserMapper.getPermissionList((String) loginId);
        String Permission= (String) userPermission.get("power");
        List<String> list = Arrays.asList(Permission.split(","));
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        Map userRole=tprUserMapper.getPermissionList((String) loginId);
        List<String> list = new ArrayList<String>();
        list.add((String) userRole.get("rolename"));
        return list;
    }

}
