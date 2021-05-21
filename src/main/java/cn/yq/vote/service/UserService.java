package cn.yq.vote.service;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.generator.User;
import cn.yq.vote.model.PageVo;
import cn.yq.vote.model.UserVo;

import javax.servlet.http.HttpSession;

public interface UserService {
    AjaxResponse userLogin(User user);
    AjaxResponse userRegister(User user);
    void modifyName(User user);

    void modifyPassword(User user);
    PageVo userPaging(PageVo pageVo);

    AjaxResponse deleteUsers(UserVo userVo, User user);

    AjaxResponse modifyUserInfo(User user, User user1, HttpSession session);
    PageVo votePaging(PageVo pageVo);
}
