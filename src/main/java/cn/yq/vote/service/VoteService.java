package cn.yq.vote.service;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.generator.Player;
import cn.yq.vote.generator.User;
import cn.yq.vote.generator.Vote;
import cn.yq.vote.model.PageVo;
import cn.yq.vote.model.VoteInfoVo;
import cn.yq.vote.model.VoteVo;

import java.util.List;

public interface VoteService {
    PageVo votePaging(PageVo pageVo);
    AjaxResponse vote(List<Player> players, User user, Vote vote,boolean realName);

    AjaxResponse joinVote(VoteVo voteVo, User user);

    AjaxResponse createVote(Vote vote,User user);

    PageVo userVotePaging(PageVo pageVo, User user);

    AjaxResponse updateVote(Vote vote, User user);

    AjaxResponse playerPassCheck(VoteVo voteVo);
    AjaxResponse votePassCheck(VoteVo voteVo,User user);
    AjaxResponse deleteVotes(VoteVo voteVo,User user);
}
