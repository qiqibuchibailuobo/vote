package cn.yq.vote.controller;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.generator.User;
import cn.yq.vote.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private UserService userService;

    @PostMapping("/loginIn")
    public AjaxResponse login(@RequestBody User user, HttpServletRequest request) {
        if(userService.userLogin(user).isIsok()){
            HttpSession session = request.getSession();
            session.setAttribute("user",userService.userLogin(user).getData());
        }
        return userService.userLogin(user);
    }

    @GetMapping("/logOut")
    public void logOut(HttpServletRequest request,HttpSession session) {
        request.getSession().removeAttribute("user");
        session.invalidate();
    }

}
