package cn.yq.vote.service;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.generator.User;
import cn.yq.vote.model.PageVo;

public interface UserService {
    AjaxResponse userLogin(User user);
    AjaxResponse userRegister(User user);
    void modifyName(User user);

    void modifyPassword(User user);
    PageVo userPaging(PageVo pageVo);
}
