package com.xxx.controller.test;

import com.xxx.frame.annotation.CurrentUser;
import com.xxx.frame.annotation.LoginRequired;
import com.xxx.models.sys.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:测试
 * @author:@luomouren.
 * @Date:2017-12-31 18:43
 */
@Controller
@RequestMapping("/test")
public class TestController {
    private static String PAGE_FILE_NAME = "test/";

    @LoginRequired
    @RequestMapping(value = "/testLoginRequired")
    public String testLoginRequired(ModelMap map) {
        // 测试是否已登录
        map.put("host","xxxSpringBootDemo");
        return PAGE_FILE_NAME+"test";
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/testCurrentUser")
    public Object testCurrentUser(@CurrentUser SysUser user) {
        return user;
    }

}
