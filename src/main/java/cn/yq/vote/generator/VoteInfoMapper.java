package cn.yq.vote.generator;

import cn.yq.vote.generator.VoteInfo;
import cn.yq.vote.generator.VoteInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoteInfoMapper {
    long countByExample(VoteInfoExample example);

    int deleteByExample(VoteInfoExample example);

    int insert(VoteInfo record);

    int insertSelective(VoteInfo record);

    List<VoteInfo> selectByExample(VoteInfoExample example);

    int updateByExampleSelective(@Param("record") VoteInfo record, @Param("example") VoteInfoExample example);

    int updateByExample(@Param("record") VoteInfo record, @Param("example") VoteInfoExample example);
}