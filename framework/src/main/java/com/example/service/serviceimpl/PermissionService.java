package com.example.service.serviceimpl;

import com.example.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {

    public boolean hasPermission(String permission) {
        if (SecurityUtils.getLoginUser().getUser().getId().equals(1L)) {
            return true;
        }
        List<String> permsList = SecurityUtils.getLoginUser().getPermsList();
        return permsList.contains(permission);
    }
}
