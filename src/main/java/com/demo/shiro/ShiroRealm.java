package com.demo.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    /**
     * 用户权限初始化
     * @param principalCollection
     * @return
     */
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Object principal = principalCollection.getPrimaryPrincipal();
        System.out.println(principal);

        Set<String> role = new HashSet<>();
        role.add("admin");

        Set<String> permiss = new HashSet<>();
        permiss.add("book:getBook");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(role);
        authorizationInfo.addStringPermissions(permiss);



        return authorizationInfo;
    }

    /**
     * 用户校验初始化
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //清除缓存
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                System.out.println(key+":"+key.toString());
                cache.remove(key);
            }
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String loginName = token.getUsername();
        char[] loginpassword = token.getPassword();

        AuthenticationInfo authenticator = new SimpleAuthenticationInfo("admin","123",getName());

        return authenticator;
    }
}
