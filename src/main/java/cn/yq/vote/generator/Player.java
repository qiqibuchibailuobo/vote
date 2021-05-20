package cn.yq.vote.generator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;

/**
 * player
 * @author 
 */
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Player implements Serializable {
    private Integer id;

    /**
     * 参与竞争者名称
     */
    private String name;

    /**
     * 得票数
     */
    private Integer gainVotes;

    /**
     * 参与时间
     */
    private Date createTime;

    /**
     * 参与竞争的投票
     */
    private Integer voteId;

    /**
     * 描述
     */
    private String describe;

    /**
     * 参与者
     */
    private Integer userId;

    /**
     * 得票占百分比
     */
    private Float gainVotesPercentage;

    /**
     * 参赛选手审核，通过为1未通过为0

     */
    private Integer state;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGainVotes() {
        return gainVotes;
    }

    public void setGainVotes(Integer gainVotes) {
        this.gainVotes = gainVotes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getGainVotesPercentage() {
        return gainVotesPercentage;
    }

    public void setGainVotesPercentage(Float gainVotesPercentage) {
        this.gainVotesPercentage = gainVotesPercentage;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Player other = (Player) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getGainVotes() == null ? other.getGainVotes() == null : this.getGainVotes().equals(other.getGainVotes()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getVoteId() == null ? other.getVoteId() == null : this.getVoteId().equals(other.getVoteId()))
            && (this.getDescribe() == null ? other.getDescribe() == null : this.getDescribe().equals(other.getDescribe()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getGainVotesPercentage() == null ? other.getGainVotesPercentage() == null : this.getGainVotesPercentage().equals(other.getGainVotesPercentage()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getGainVotes() == null) ? 0 : getGainVotes().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getVoteId() == null) ? 0 : getVoteId().hashCode());
        result = prime * result + ((getDescribe() == null) ? 0 : getDescribe().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getGainVotesPercentage() == null) ? 0 : getGainVotesPercentage().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", gainVotes=").append(gainVotes);
        sb.append(", createTime=").append(createTime);
        sb.append(", voteId=").append(voteId);
        sb.append(", describe=").append(describe);
        sb.append(", userId=").append(userId);
        sb.append(", gainVotesPercentage=").append(gainVotesPercentage);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}