package cn.yq.vote.controller;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.generator.Player;
import cn.yq.vote.generator.User;
import cn.yq.vote.generator.Vote;
import cn.yq.vote.model.PageVo;
import cn.yq.vote.model.VoteInfoVo;
import cn.yq.vote.model.VoteVo;
import cn.yq.vote.service.VoteInfoService;
import cn.yq.vote.service.VoteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/vote")
public class VoteController {
    @Resource
    private VoteService voteService;
    @Resource
    private VoteInfoService voteInfoService;

    @PostMapping("/votePaging")
    public PageVo votePaging(@RequestBody PageVo pageVo){
        return voteService.votePaging(pageVo);
    }

    @PostMapping("/vote")
    public AjaxResponse vote(@RequestBody VoteVo voteVo,
                             HttpServletRequest request,
                             HttpSession session){
        User user=(User)request.getSession().getAttribute("user");

        return voteService.vote(voteVo.getPlayers(),user,voteVo.getVote(),voteVo.isRealName());
    }
    @PostMapping("/joinVote")
    public AjaxResponse joinVote(@RequestBody VoteVo voteVo,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");

        return voteService.joinVote(voteVo,user);
    }
    @PostMapping("/createVote")
    public AjaxResponse createVote(@RequestBody Vote vote,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");

        return voteService.createVote(vote,user);
    }
    @PostMapping("/userVotePaging")
    public PageVo userVotePaging(@RequestBody PageVo pageVo,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        return voteService.userVotePaging(pageVo,user);
    }
    @PutMapping("/updateVote")
    public AjaxResponse updateVote(@RequestBody Vote vote,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        return voteService.updateVote(vote, user);
    }
    @PutMapping("/playerPassCheck")
    public AjaxResponse playerPassCheck(@RequestBody VoteVo voteVo,
                                        HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        return voteService.playerPassCheck(voteVo);
    }

    @PostMapping("/lookVoteInfo")
    public List<VoteInfoVo> lookVoteInfo(@RequestBody Player player){
        return voteInfoService.lookVoteInfo(player);
    }
    @PutMapping("/votePassCheck")
    public AjaxResponse votePassCheck(@RequestBody VoteVo voteVo,
                                        HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        return voteService.votePassCheck(voteVo,user);
    }
    @PostMapping("/deleteVotes")
    public AjaxResponse deleteVotes(@RequestBody VoteVo voteVo,
                                      HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        return voteService.deleteVotes(voteVo,user);
    }
}
