package cn.yq.vote.service;

import cn.yq.vote.generator.Player;
import cn.yq.vote.model.VoteInfoVo;

import java.util.List;

public interface VoteInfoService {
    List<VoteInfoVo> lookVoteInfo(Player player);

}
