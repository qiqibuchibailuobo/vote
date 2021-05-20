package cn.yq.vote.model;

import cn.yq.vote.generator.Player;
import cn.yq.vote.generator.User;
import cn.yq.vote.generator.Vote;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class PageVo {
    List<User> users;
    List<Vote> votes;
    List<Player> players;
    Vote vote;
    @JsonProperty(value = "limit")
    int limit;
    @JsonProperty(value = "page")
    long page;
    int total;
    int totalPage;
    int state;
    String searchVote;
}
