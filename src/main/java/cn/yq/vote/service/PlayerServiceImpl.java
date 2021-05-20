package cn.yq.vote.service;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.exception.CustomException;
import cn.yq.vote.exception.CustomExceptionType;
import cn.yq.vote.generator.*;
import cn.yq.vote.model.PageVo;
import cn.yq.vote.model.PlayerVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{
    @Resource
    private PlayerMapper playerMapper;
    @Resource
    private VoteInfoMapper voteInfoMapper;

    @Override
    public PageVo playerPaging(PageVo pageVo) {
        PlayerExample playerExample1 = new PlayerExample();
        playerExample1.createCriteria().andVoteIdEqualTo(pageVo.getVote().getId());
        List<Player> player1 = playerMapper.selectByExample(playerExample1);
        int i = player1.size();
        int num = 0;
        for (Player player : player1) {
            num+=player.getGainVotes();
        }
        PlayerExample playerExample = new PlayerExample();
        playerExample.setLimit(pageVo.getLimit());
        playerExample.setOffset((pageVo.getPage()*pageVo.getLimit()-pageVo.getLimit()));
        playerExample.createCriteria().andVoteIdEqualTo(pageVo.getVote().getId());

        List<Player> players =  playerMapper.selectByExample(playerExample);
        for (Player player2 : players) {
            if(num == 0){
                player2.setGainVotesPercentage((float) 0);
                playerMapper.updateByPrimaryKeySelective(player2);
            }else {
                BigDecimal bg = new BigDecimal( (player2.getGainVotes()/(float)num)*100);
                double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                player2.setGainVotesPercentage((float)f1);
                playerMapper.updateByPrimaryKeySelective(player2);
            }
        }
        pageVo.setPlayers(players);
        pageVo.setTotal(i);
        pageVo.setPage(pageVo.getPage());
        pageVo.setTotalPage((pageVo.getTotal()+pageVo.getLimit()-1)/pageVo.getLimit());
        return pageVo;
    }

    @Override
    @Transactional
    public AjaxResponse deletePlayer(Player player) {
        try {
            VoteInfoExample voteInfoExample = new VoteInfoExample();
            voteInfoExample.createCriteria().andPlayerIdEqualTo(player.getId()).andVoteIdEqualTo(player.getVoteId());
            voteInfoMapper.deleteByExample(voteInfoExample);
            playerMapper.deleteByPrimaryKey(player.getId());
            return AjaxResponse.success("删除成功！");
        }catch (Exception e) {
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在选手业务，deletePlayer()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }
    }

    @Override
    @Transactional
    public AjaxResponse addPlayer(PlayerVo playerVo) {
        try {
            PlayerExample playerExample = new PlayerExample();
            playerExample.createCriteria().andNameEqualTo(playerVo.getPlayer().getName());
            List<Player> players = playerMapper.selectByExample(playerExample);

            if (players.size() > 0) {
                return AjaxResponse.error("已存在该名称选手！");
            }else {
                Player player = new Player();
                player.setName(playerVo.getPlayer().getName());
                player.setCreateTime(new Date());
                player.setVoteId(playerVo.getVote().getId());
                player.setDescribe(playerVo.getPlayer().getDescribe());
                player.setState(1);
                playerMapper.insertSelective(player);

                return AjaxResponse.success("添加成功！");
            }

        }catch (Exception e) {
            throw new CustomException(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "在选手业务，deletePlayer()方法内，出现ClassNotFoundException，请将该信息告知管理员");
        }
    }
}
