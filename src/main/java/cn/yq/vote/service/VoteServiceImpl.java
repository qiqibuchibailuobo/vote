package cn.yq.vote.service;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.exception.CustomException;
import cn.yq.vote.exception.CustomExceptionType;
import cn.yq.vote.generator.*;
import cn.yq.vote.model.PageVo;
import cn.yq.vote.model.VoteInfoVo;
import cn.yq.vote.model.VoteVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {
    @Resource
    private VoteMapper voteMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PlayerMapper playerMapper;
    @Resource
    private VoteInfoMapper voteInfoMapper;

    @Override
    public PageVo votePaging(PageVo pageVo) {

        Date now = new Date();


        VoteExample voteExample1 = new VoteExample();
        List<Vote> vote1 = voteMapper.selectByExample(voteExample1);
        int i = vote1.size();

        VoteExample voteExample = new VoteExample();
        voteExample.setLimit(pageVo.getLimit());

        voteExample.setOffset((pageVo.getPage() * pageVo.getLimit() - pageVo.getLimit()));
        if (pageVo.getSearchVote() == null){
            pageVo.setSearchVote("");
        }

        if(pageVo.getState()==1||pageVo.getState()==0){
            voteExample.createCriteria().andStateEqualTo(pageVo.getState()).andNameLike("%"+pageVo.getSearchVote()+"%");
        }else {
            voteExample.createCriteria().andNameLike("%"+pageVo.getSearchVote()+"%");
        }

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

    @Override
    @Transactional
    public AjaxResponse vote(List<Player> players, User user, Vote vote, boolean realName) {
        VoteInfoExample voteInfoExample = new VoteInfoExample();
        voteInfoExample.createCriteria()
                .andUserIdEqualTo(user.getId())
                .andVoteIdEqualTo(vote.getId());

        List<VoteInfo> voteInfos = voteInfoMapper.selectByExample(voteInfoExample);
        for (int i = 0; i < voteInfos.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                if (voteInfos.get(i).getUserId().equals(user.getId()) && voteInfos.get(i).getVoteId().equals(vote.getId()) && voteInfos.get(i).getPlayerId().equals(players.get(j).getId())) {
                    return AjaxResponse.error("您已经给其中一位选手投过了票！");
                }

            }
        }
        if (voteInfos.size() > vote.getMaxChoice() || voteInfos.size() == vote.getMaxChoice()) {
            return AjaxResponse.error("您已经投过票了！");
        } else {
            if ((players.size() + voteInfos.size()) > vote.getMaxChoice()) {
                return AjaxResponse.error("您选择的选手大于您的剩余票数！");
            } else {
                for (Player player : players) {
                    VoteInfo voteInfo = new VoteInfo();
                    voteInfo.setVoteId(vote.getId());
                    voteInfo.setPlayerId(player.getId());
                    voteInfo.setUserId(user.getId());
                    if (realName) {
                        voteInfo.setRealName(0);
                    } else {
                        voteInfo.setRealName(1);
                    }
                    voteInfoMapper.insert(voteInfo);
                    player.setGainVotes(player.getGainVotes() + 1);
                    playerMapper.updateByPrimaryKeySelective(player);
                }
                VoteInfoExample voteInfoExample1 = new VoteInfoExample();
                voteInfoExample1.createCriteria()
                        .andUserIdEqualTo(user.getId())
                        .andVoteIdEqualTo(vote.getId());

                List<VoteInfo> voteInfos1 = voteInfoMapper.selectByExample(voteInfoExample);
                return AjaxResponse.success("当前投票您还有" + (vote.getMaxChoice() - voteInfos1.size()) + "票");
            }
        }
    }

    @Override
    public AjaxResponse joinVote(VoteVo voteVo, User user) {
        PlayerExample playerExample = new PlayerExample();
        playerExample.createCriteria().andUserIdEqualTo(user.getId()).andVoteIdEqualTo(voteVo.getVote().getId());
        List<Player> players = playerMapper.selectByExample(playerExample);
        if (players.size() > 0) {
            return AjaxResponse.error("您已经参加了该投票！");
        } else {
            Player player = new Player();
            player.setName(user.getName());
            player.setCreateTime(new Date());
            player.setVoteId(voteVo.getVote().getId());
            player.setDescribe(voteVo.getVote().getDescribe());
            player.setUserId(user.getId());
            playerMapper.insertSelective(player);
            return AjaxResponse.success("参加成功，请等待发起人审核！");
        }
    }

    @Override
    @Transactional
    public AjaxResponse createVote(Vote vote, User user) {
        try {
            if (vote.getEndDate().before(vote.getCreateDate())) {
                return AjaxResponse.error("结束时间不能在开始时间之前！");
            } else {
                VoteExample voteExample = new VoteExample();
                voteExample.createCriteria().andNameEqualTo(vote.getName()).andUserIdEqualTo(user.getId());
                List<Vote> votes = voteMapper.selectByExample(voteExample);
                if (votes.size() > 0) {
                    return AjaxResponse.error("该投票名称已存在！");
                } else {
                    Vote v = new Vote();
                    v.setName(vote.getName());
                    v.setUserId(user.getId());
                    v.setState(0);
                    v.setCreateDate(vote.getCreateDate());
                    v.setDescribe(vote.getDescribe());
                    v.setMaxChoice(vote.getMaxChoice());
                    v.setEndDate(vote.getEndDate());
                    v.setAdoptDate(null);
                    v.setEndState(0);
                    voteMapper.insert(v);
                    return AjaxResponse.success("创建成功！");
                }
            }
        } catch (Exception e) {
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在投票业务，createVote()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }
    }

    @Override
    public PageVo userVotePaging(PageVo pageVo, User user) {

        Date now = new Date();


        VoteExample voteExample1 = new VoteExample();
        List<Vote> vote1 = voteMapper.selectByExample(voteExample1);
        int i = vote1.size();

        VoteExample voteExample = new VoteExample();
        voteExample.setLimit(pageVo.getLimit());

        voteExample.setOffset((pageVo.getPage() * pageVo.getLimit() - pageVo.getLimit()));
        voteExample.createCriteria().andUserIdEqualTo(user.getId());
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

    @Override
    @Transactional
    public AjaxResponse updateVote(Vote vote, User user) {
        try {
            VoteExample voteExample = new VoteExample();
            if(user.getUserType() == 1){
                voteExample.createCriteria().andUserIdEqualTo(user.getId()).andIdEqualTo(vote.getId());
            }else if (user.getUserType() == 0){
                voteExample.createCriteria().andIdEqualTo(vote.getId());
            }
            List<Vote> votes = voteMapper.selectByExample(voteExample);
            if (votes.size() == 0) {
                return AjaxResponse.error("该投票不存在！");
            }
            voteMapper.updateByPrimaryKeySelective(vote);
            return AjaxResponse.success();
        } catch (Exception e) {
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在投票业务，updateVote()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }
    }

    @Override
    public AjaxResponse playerPassCheck(VoteVo voteVo) {
        try {
            PlayerExample playerExample = new PlayerExample();
            for (Player player : voteVo.getPlayers()) {
                playerExample.createCriteria().andVoteIdEqualTo(voteVo.getVote().getId()).andIdEqualTo(player.getId());
                if (playerMapper.selectByExample(playerExample).size() == 0) {
                    return AjaxResponse.error("没有这个参赛选手！");
                }
            }
            for (Player player : voteVo.getPlayers()) {
                Player p = new Player();
                p.setId(player.getId());
                p.setUserId(player.getUserId());
                p.setDescribe(player.getDescribe());
                p.setVoteId(player.getVoteId());
                p.setCreateTime(player.getCreateTime());
                p.setGainVotes(player.getGainVotes());
                p.setGainVotesPercentage(player.getGainVotesPercentage());
                p.setState(player.getState());
                p.setName(player.getName());
                playerMapper.updateByPrimaryKeySelective(p);
            }

            return AjaxResponse.success();
        }catch (Exception e) {
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在投票业务，playerPassCheck()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }

    }

    @Override
    public AjaxResponse votePassCheck(VoteVo voteVo,User user) {

        try {
            if (user.getUserType() == 1){
                return AjaxResponse.error("您没有权限！");
            }
            VoteExample VoteExample = new VoteExample();
            for (Vote vote : voteVo.getVotes()) {
                VoteExample.createCriteria().andIdEqualTo(vote.getId());
                if (voteMapper.selectByExample(VoteExample).size() == 0) {
                    return AjaxResponse.error("没有这个投票项目！");
                }
            }
            for (Vote vote : voteVo.getVotes()) {
                if (vote.getAdoptDate()!=null){
                    if (vote.getState() == 0){
                        vote.setAdoptDate(null);
                        voteMapper.updateByPrimaryKey(vote);
                    }else {
                        voteMapper.updateByPrimaryKey(vote);
                    }
                }else{
                    if (vote.getState() == 0){
                        vote.setAdoptDate(null);
                        voteMapper.updateByPrimaryKey(vote);
                    }else {
                        vote.setAdoptDate(new Date());
                        voteMapper.updateByPrimaryKey(vote);
                    }
                }
            }
            return AjaxResponse.success();
        }catch (Exception e) {
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在投票业务，votePassCheck()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }

    }

    @Override
    public AjaxResponse deleteVotes(VoteVo voteVo, User user) {
        try {
            if (user.getUserType() == 1){
                return AjaxResponse.error("您没有权限！");
            }
            VoteExample VoteExample = new VoteExample();
            for (Vote vote : voteVo.getVotes()) {
                VoteExample.createCriteria().andIdEqualTo(vote.getId());
                if (voteMapper.selectByExample(VoteExample).size() == 0) {
                    return AjaxResponse.error("没有这个投票项目！");
                }
            }
            for (Vote vote : voteVo.getVotes()) {
               PlayerExample playerExample = new PlayerExample();
               playerExample.createCriteria().andVoteIdEqualTo(vote.getId());
               List<Player> players = playerMapper.selectByExample(playerExample);
               for (Player player : players) {
                   playerMapper.deleteByPrimaryKey(player.getId());
               }
               VoteInfoExample voteInfoExample = new VoteInfoExample();
               voteInfoExample.createCriteria().andVoteIdEqualTo(vote.getId());
               List<VoteInfo> voteInfos = voteInfoMapper.selectByExample(voteInfoExample);
               for (VoteInfo voteInfo : voteInfos) {
                   voteInfoMapper.deleteByExample(voteInfoExample);
               }
                voteMapper.deleteByPrimaryKey(vote.getId());
            }
            return AjaxResponse.success();
        }catch (Exception e) {
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在投票业务，votePassCheck()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }
    }


}
