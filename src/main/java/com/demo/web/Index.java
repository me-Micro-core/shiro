package com.demo.web;

import com.demo.shiro.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class Index {

    @Autowired
    ShiroRealm jdbcRealm;
    private AuthorizationInfo obj;

    /**
     * 登录
     * @param session
     * @param passWord
     * @param userName
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(HttpSession session,String passWord,String userName){
        System.out.println(session.getId());
        String res = "list";
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(userName,passWord);
            token.setRememberMe(true);
            try {
                subject.login(token);
            }catch (AuthenticationException info){
                res = "redirect:/login.jsp";
            }
        }

        return res;
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/logOut")
    public String logOut(){
       Subject subject = SecurityUtils.getSubject();
       if (subject.isAuthenticated()){
           subject.logout();
       }
        return "redirect:/login.jsp";
    }

    /**
     * 书本列表
     * @return
     */
    @RequiresRoles("admin")
    @RequiresPermissions(value = {"book:getBook"},logical= Logical.OR)
    @RequestMapping(value = "/getBooks")
    public String getBooks(){

        Subject subject = SecurityUtils.getSubject();
        obj =jdbcRealm.getAuthorizationCache().get(SecurityUtils.getSubject().getPrincipals());
        return "books";
    }

    @RequestMapping(value = "/test")
    public String goTest(){

        return "test";
    }
}
