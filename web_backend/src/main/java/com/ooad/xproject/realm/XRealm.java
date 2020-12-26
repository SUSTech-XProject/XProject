package com.ooad.xproject.realm;

import com.ooad.xproject.entity.Role;
import com.ooad.xproject.mapper.RoleMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class XRealm extends AuthorizingRealm {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = principalCollection.getPrimaryPrincipal().toString();

        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        return s;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = authenticationToken.getPrincipal().toString();
        Role role = roleMapper.selectByUsername(username);
        String passwordInDB = role.getPassword();
        String salt = role.getSalt();

        return new SimpleAuthenticationInfo(username, passwordInDB, ByteSource.Util.bytes(salt), getName());
    }
}
