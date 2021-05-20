package cn.yq.vote.controller;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.generator.Player;
import cn.yq.vote.model.PageVo;
import cn.yq.vote.model.PlayerVo;
import cn.yq.vote.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Resource
    private PlayerService playerService;

    @PostMapping("/playerPaging")
    public PageVo playerPaging(@RequestBody PageVo pageVo){
        return playerService.playerPaging(pageVo);
    }
    @PostMapping("/deletePlayer")
    public AjaxResponse deletePlayer(@RequestBody Player player){
        return playerService.deletePlayer(player);
    }
    @PostMapping("/addPlayer")
    public AjaxResponse addPlayer(@RequestBody PlayerVo playerVo){
        return playerService.addPlayer(playerVo);
    }
}
