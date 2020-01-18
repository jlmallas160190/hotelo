package com.avalith.hotelo.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@RequiredArgsConstructor
@Slf4j
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private static final String SUPER_USER = "SUPER_USER";

    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

        return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetType.toUpperCase(),
                permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        try {

            for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
                if (grantedAuth.getAuthority().equals(SUPER_USER) ||
                        grantedAuth.getAuthority().startsWith(targetType) &&
                                grantedAuth.getAuthority().contains(permission)) {
                    log.info("User {} authorized", auth.getPrincipal().toString());
                    return true;
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        log.info("User {} not authorized", auth.getPrincipal().toString());
        return false;
    }
}
