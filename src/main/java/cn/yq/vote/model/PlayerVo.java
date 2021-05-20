package cn.yq.vote.model;

import cn.yq.vote.generator.Player;
import cn.yq.vote.generator.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class PlayerVo {
    private Vote vote;
    private Player player;
}
