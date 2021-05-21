package cn.yq.vote.model;

import cn.yq.vote.generator.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserVo {
    List<User> users;
}
