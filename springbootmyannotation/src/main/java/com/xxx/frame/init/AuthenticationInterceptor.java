package com.xxx.frame.init;


import com.xxx.frame.annotation.LoginRequired;
import com.xxx.frame.annotation.SignCertificationRequired;
import com.xxx.frame.constants.AnnotationConstants;
import com.xxx.frame.constants.CurrentUserConstants;
import com.xxx.frame.util.SignCertificationUtils;
import com.xxx.frame.util.TokenUtils;
import com.xxx.models.sys.SysUser;
import com.xxx.services.user.SysUserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @description:Token验证过滤器,判断是否已登录
 * @author:@luomouren.
 * @Date:2017-12-10 22:40
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserService userService;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean result = true;
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (null != methodAnnotation) {
            // 判断是否存在令牌信息，如果存在，则允许登录
            String accessToken = request.getParameter(AnnotationConstants.ACCESS_TOKEN);
            if (null == accessToken) {
                result = false;
                throw new RuntimeException("无token，请重新登录");
            }
            Claims claims = TokenUtils.parseJWT(accessToken);
            String userName = claims.getId();
            SysUser user = userService.findByUserName(userName);
            if (user == null) {
                result = false;
                throw new RuntimeException("用户不存在，请重新登录");
            }
            // 当前登录用户@CurrentUser
            request.setAttribute(CurrentUserConstants.CURRENT_USER, user);
        }

        // 判断接口是否需要安全验证SignCertificationRequired
        SignCertificationRequired signCertificationRequiredAnnotation = method.getAnnotation(SignCertificationRequired.class);
        // 有@SignCertificationRequired注解 需要认证
        if (null != signCertificationRequiredAnnotation) {
            String key = request.getHeader(AnnotationConstants.KEY_PARAM_NAME);
            String qts = request.getHeader(AnnotationConstants.QTS_PARAM_NAME);
            String sig = request.getHeader(AnnotationConstants.SIG_PARAM_NAME);

            if(SignCertificationUtils.checkSignCertification(key,qts,sig)){
                result = true;
            }else{
                //"API请求需要的认证信息不正确！
                result = false;
            }
        }
        return result;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
