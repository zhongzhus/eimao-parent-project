package cn.its.controller;

import cn.its.basic.util.AjaxResult;
import cn.its.plat.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    private AjaxResult loginUser(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        if ("admin".equals(username) && "admin".equals(password)){
            return AjaxResult.me().setSuccess(true).setObject(user).setMsg("登陆成功");
        }
        return AjaxResult.me().setSuccess(false).setObject(user).setMsg("登陆失败");
    }
}
