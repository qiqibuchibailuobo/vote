package cn.yq.vote.controller;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.generator.User;
import cn.yq.vote.generator.UserExample;
import cn.yq.vote.generator.UserMapper;
import cn.yq.vote.model.PageVo;
import cn.yq.vote.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;

    @PostMapping("/registerIn")
    public AjaxResponse register(@RequestBody User user) {
        return userService.userRegister(user);
    }

    @GetMapping("/user")
    public User user(HttpServletRequest request) {
        User user=(User)request.getSession().getAttribute("user");
        return user;
    }


    @PutMapping("/modifyName")
    public User modifyName(@RequestBody String name, HttpServletRequest request, HttpSession session){
        User user=(User)request.getSession().getAttribute("user");
        user.setName(name);
        userService.modifyName(user);
        session.setAttribute("user",user);
        return user;
    }
    @PutMapping("/modifyPassword")
    public User modifyPassword(@RequestBody String pass,HttpServletRequest request,HttpSession session){
        User user=(User)request.getSession().getAttribute("user");
        user.setPassword(pass);
        userService.modifyPassword(user);
        session.setAttribute("user",user);
        return user;
    }

    @PostMapping("/userPaging")
    public PageVo userPaging(@RequestBody PageVo pageVo){

        return userService.userPaging(pageVo);
    }
    @PostMapping("/goAdmin")
    public AjaxResponse register(@RequestBody int userType) {
        if (userType == 1) {
            return AjaxResponse.error();
        }else {
            return AjaxResponse.success();
        }
    }
}
