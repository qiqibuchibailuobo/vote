package cn.yq.vote.service;

import cn.yq.vote.exception.AjaxResponse;
import cn.yq.vote.generator.Player;
import cn.yq.vote.model.PageVo;
import cn.yq.vote.model.PlayerVo;

public interface PlayerService {
    PageVo playerPaging(PageVo pageVo);

    AjaxResponse deletePlayer(Player player);

    AjaxResponse addPlayer(PlayerVo playerVo);
}
