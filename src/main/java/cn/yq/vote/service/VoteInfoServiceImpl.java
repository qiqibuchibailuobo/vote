package cn.yq.vote.service;

import cn.yq.vote.generator.*;
import cn.yq.vote.model.VoteInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoteInfoServiceImpl implements VoteInfoService{
    @Resource
    private VoteMapper voteMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PlayerMapper playerMapper;
    @Resource
    private VoteInfoMapper voteInfoMapper;

    @Override
    public List<VoteInfoVo> lookVoteInfo(Player player) {
        VoteInfoExample voteInfoExample = new VoteInfoExample();
        voteInfoExample.createCriteria().andVoteIdEqualTo(player.getVoteId()).andPlayerIdEqualTo(player.getId());
        List<VoteInfo> voteInfos = voteInfoMapper.selectByExample(voteInfoExample);
        List<VoteInfoVo> voteInfoVos = new ArrayList<VoteInfoVo>();
        if (voteInfos.size() > 0) {
            for (VoteInfo voteInfoVo : voteInfos) {
                User user = userMapper.selectByPrimaryKey(voteInfoVo.getUserId());
                Vote vote = voteMapper.selectByPrimaryKey(voteInfoVo.getVoteId());
                VoteInfoVo voteInfoVo1 = new VoteInfoVo();
                voteInfoVo1.setVote(vote.getName());

                voteInfoVo1.setPlayer(player.getName());
                if (voteInfoVo.getRealName() == 0){
                    voteInfoVo1.setUser(user.getName());
                }else {
                    voteInfoVo1.setUser("匿名用户");
                }
                voteInfoVos.add(voteInfoVo1);
                System.out.println(voteInfoVo1);

            }
        }
        return voteInfoVos;
    }
}
