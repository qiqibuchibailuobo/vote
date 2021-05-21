package cn.yq.vote.service;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.exception.CustomException;
import cn.yq.vote.exception.CustomExceptionType;
import cn.yq.vote.generator.*;
import cn.yq.vote.model.PageVo;
import cn.yq.vote.model.UserVo;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;
    @Resource
    private VoteMapper voteMapper;
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

        if (pageVo.getSearchVote() == null){
            pageVo.setSearchVote("");
        }

        if(pageVo.getState()==1||pageVo.getState()==0){
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在用户业务，userPaging()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }else {
            userExample.createCriteria().andNameLike("%"+pageVo.getSearchVote()+"%");
        }

        userExample.setOffset((pageVo.getPage()*pageVo.getLimit()-pageVo.getLimit()));
        List<User> users = userMapper.selectByExample(userExample);

        pageVo.setUsers(users);
        pageVo.setTotal(i);
        pageVo.setPage(pageVo.getPage());
        pageVo.setTotalPage((pageVo.getTotal()+pageVo.getLimit()-1)/pageVo.getLimit());
        return pageVo;
    }

    @Override
    public AjaxResponse deleteUsers(UserVo userVo, User user) {
        try {
            if (user.getUserType() == 1){
                return AjaxResponse.error("您没有权限！");
            }
            UserExample userExample = new UserExample();
            for (User user1 : userVo.getUsers()) {
                userExample.createCriteria().andIdEqualTo(user1.getId());
                if (userMapper.selectByExample(userExample).size() == 0) {
                    return AjaxResponse.error("没有这个用户！");
                }
            }
            for (User user1 : userVo.getUsers()) {
                VoteExample voteExample = new VoteExample();
                voteExample.createCriteria().andUserIdEqualTo(user1.getId());
                List<Vote> votes = voteMapper.selectByExample(voteExample);

                for (Vote vote : votes) {
                    vote.setUserId(1);
                    voteMapper.updateByPrimaryKey(vote);
                }
                userMapper.deleteByPrimaryKey(user1.getId());
            }
            return AjaxResponse.success();
        }catch (Exception e) {
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在投票业务，votePassCheck()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }
    }

    @Override
    public AjaxResponse modifyUserInfo(User user, User user1,HttpSession session) {
        if (user1.getUserType() == 1) {
            return AjaxResponse.error("您没有权限");
        }
        userMapper.updateByPrimaryKeySelective(user);

        session.setAttribute("user",user);
        return AjaxResponse.success();
    }
    @Override
    public PageVo votePaging(PageVo pageVo) {

        Date now = new Date();


        VoteExample voteExample1 = new VoteExample();
        voteExample1.createCriteria().andUserIdEqualTo(pageVo.getUser().getId());
        List<Vote> vote1 = voteMapper.selectByExample(voteExample1);
        int i = vote1.size();

        VoteExample voteExample = new VoteExample();
        voteExample.createCriteria().andUserIdEqualTo(pageVo.getUser().getId());
        voteExample.setLimit(pageVo.getLimit());

        voteExample.setOffset((pageVo.getPage() * pageVo.getLimit() - pageVo.getLimit()));


        List<Vote> votes = voteMapper.selectByExample(voteExample);

        for (Vote vote : votes) {
            if (vote.getCreateDate().after(now)) {
                vote.setEndState(2);
            } else if (vote.getCreateDate().before(now) && vote.getEndDate().after(now)) {
                vote.setEndState(0);
            } else if (vote.getEndDate().before(now)) {
                vote.setEndState(1);
            }
            voteMapper.updateByPrimaryKeySelective(vote);
        }
        pageVo.setVotes(votes);
        pageVo.setTotal(i);
        pageVo.setPage(pageVo.getPage());
        pageVo.setTotalPage((pageVo.getTotal() + pageVo.getLimit() - 1) / pageVo.getLimit());
        return pageVo;
    }
}
