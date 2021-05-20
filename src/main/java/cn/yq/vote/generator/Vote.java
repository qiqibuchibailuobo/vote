package cn.yq.vote.generator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;

/**
 * vote
 * @author 
 */
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Vote implements Serializable {
    private Integer id;

    /**
     * 投票名称
     */
    private String name;

    /**
     * 投票发起人id
     */
    private Integer userId;

    /**
     * 投票审核
     */
    private Integer state;

    /**
     * 投票创建时间
     */
    private Date createDate;

    /**
     * 审核通过时间
     */
    private Date adoptDate;

    /**
     * 描述
     */
    private String describe;

    /**
     * 最多可以给几个竞争人投票，默认为1人
     */
    private Integer maxChoice;

    /**
     * 投票结束时间
     */
    private Date endDate;

    /**
     * 投票是否已经结束，未结束是0，结束是1，未开始是2
     */
    private Integer endState;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getAdoptDate() {
        return adoptDate;
    }

    public void setAdoptDate(Date adoptDate) {
        this.adoptDate = adoptDate;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getMaxChoice() {
        return maxChoice;
    }

    public void setMaxChoice(Integer maxChoice) {
        this.maxChoice = maxChoice;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getEndState() {
        return endState;
    }

    public void setEndState(Integer endState) {
        this.endState = endState;
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
        Vote other = (Vote) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getAdoptDate() == null ? other.getAdoptDate() == null : this.getAdoptDate().equals(other.getAdoptDate()))
            && (this.getDescribe() == null ? other.getDescribe() == null : this.getDescribe().equals(other.getDescribe()))
            && (this.getMaxChoice() == null ? other.getMaxChoice() == null : this.getMaxChoice().equals(other.getMaxChoice()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getEndState() == null ? other.getEndState() == null : this.getEndState().equals(other.getEndState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getAdoptDate() == null) ? 0 : getAdoptDate().hashCode());
        result = prime * result + ((getDescribe() == null) ? 0 : getDescribe().hashCode());
        result = prime * result + ((getMaxChoice() == null) ? 0 : getMaxChoice().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getEndState() == null) ? 0 : getEndState().hashCode());
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
        sb.append(", userId=").append(userId);
        sb.append(", state=").append(state);
        sb.append(", createDate=").append(createDate);
        sb.append(", adoptDate=").append(adoptDate);
        sb.append(", describe=").append(describe);
        sb.append(", maxChoice=").append(maxChoice);
        sb.append(", endDate=").append(endDate);
        sb.append(", endState=").append(endState);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}