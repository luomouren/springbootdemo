package com.xxx.controller.user;

import com.xxx.models.sys.SysUser;
import com.xxx.services.user.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 *
 * @author luomouren
 * @date 2017/6/4
 */
@RestController
@RequestMapping({"/user"})
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysUserService sysUserServices;

    @ResponseBody
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Object getById(String userId) {
        logger.info("getById:userId:" + userId);
        SysUser user = sysUserServices.findById(userId);
        return user;
    }

    @ResponseBody
    @RequestMapping(value = "/getByUserName", method = RequestMethod.GET)
    public Object getByUserName(String userName) {
        logger.info("getByUserName:userName:" + userName);
        SysUser user = sysUserServices.findByUserName(userName);
        return user;
    }

}


