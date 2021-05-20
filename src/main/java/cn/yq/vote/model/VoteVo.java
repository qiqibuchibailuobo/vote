package cn.yq.vote.model;

import cn.yq.vote.generator.Player;
import cn.yq.vote.generator.User;
import cn.yq.vote.generator.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class VoteVo {
    List<Player> players;
    List<Vote> votes;
    Vote vote;
    String describe;
    boolean realName;
    User user;
}
