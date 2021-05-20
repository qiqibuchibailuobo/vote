package cn.yq.vote.service;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.exception.CustomException;
import cn.yq.vote.exception.CustomExceptionType;
import cn.yq.vote.generator.User;
import cn.yq.vote.generator.UserExample;
import cn.yq.vote.generator.UserMapper;
import cn.yq.vote.model.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;
    @Override
    public AjaxResponse userLogin(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andUsernameEqualTo(user.getUsername());

        List<User> u1 = userMapper.selectByExample(userExample);
//        System.out.println(u);
        try {
            if(u1!=null&&u1.size()>0){
                User u = u1.get(0);
                if (!u.getUsername().equals(user.getUsername())) {
                    System.out.println(user.getUsername());
                    System.out.println(u.getUsername());
                    return AjaxResponse.error(user);
                }else {
                    if (u.getPassword().equals(user.getPassword())){
                        return AjaxResponse.success(u);
                    }else {
                        return AjaxResponse.error(user);
                    }
                }
            }else {
                return AjaxResponse.error(user);
            }
        }catch (Exception e) {
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在登录业务，userLogin()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }
    }

    @Override
    public AjaxResponse userRegister(User user) {
        if (user.getPassword().equals("")||user.getUsername().equals("")||user.getEmail().equals("")){
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "提交了空信息！");
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andUsernameEqualTo(user.getUsername());

        List<User> u1 = userMapper.selectByExample(userExample);
//        System.out.println(u);
        try {
            if(u1!=null&&u1.size()>0){
                return AjaxResponse.error(user,"用户名已存在！");
            }else {
                user.setName(user.getUsername());
                user.setUserType(1);
                userMapper.insert(user);
                return AjaxResponse.success(user, "注册成功！");
            }
        }catch (Exception e) {
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在注册业务，userRegister()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }

    }

    @Override
    public void modifyName(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void modifyPassword(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public PageVo userPaging(PageVo pageVo) {
        UserExample userExample1 = new UserExample();
        List<User> users1 = userMapper.selectByExample(userExample1);
        int i = users1.size();

        UserExample userExample = new UserExample();
        userExample.setLimit(pageVo.getLimit());

        userExample.setOffset((pageVo.getPage()*pageVo.getLimit()-pageVo.getLimit()));
        List<User> users = userMapper.selectByExample(userExample);

        pageVo.setUsers(users);
        pageVo.setTotal(i);
        pageVo.setPage(pageVo.getPage());
        pageVo.setTotalPage((pageVo.getTotal()+pageVo.getLimit()-1)/pageVo.getLimit());
        return pageVo;
    }
}
