package com.hichat.zuul.shiro;

import com.hichat.common.pojo.WebUser;
import com.hichat.common.util.ReturnResult;
import com.hichat.zuul.service.WebUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private WebUserService webUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 验证当前登录的Subject
     * LoginController.login()方法中执行Subject.login()时 执行此方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());
        // 从数据库获取对应用户名密码的用户
        ReturnResult<WebUser> loginResult = webUserService.login(name,password);
        if (loginResult.getStatus() == 400) {
            throw new AuthenticationException();
        } else {
            WebUser webUser = (WebUser) loginResult.getData();
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    webUser, //用户
                    webUser.getPassword(), //密码
                    getName()  //realm name
            );
            return authenticationInfo;
        }
    }
}
