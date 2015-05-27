package cn.flus.cms.core.dao.domain;

import java.util.Date;

public class CategoryEntity implements Comparable<CategoryEntity> {

    private Integer id;

    private Integer parentId;

    private Integer depth;

    private String  name;

    private Integer status;

    private String  allowTopic;

    private String  allowReply;

    private Integer weight;

    private Long    creatorId;

    private String  creator;

    private Date    createTime;

    private String  deleteFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAllowTopic() {
        return allowTopic;
    }

    public void setAllowTopic(String allowTopic) {
        this.allowTopic = allowTopic;
    }

    public String getAllowReply() {
        return allowReply;
    }

    public void setAllowReply(String allowReply) {
        this.allowReply = allowReply;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public int compareTo(CategoryEntity o) {
        int result = 0;
        if (this.getWeight() == null && o.getWeight() != null) {
            result = -1;
        }
        if (this.getWeight() != null && o.getWeight() == null) {
            result = 1;
        }
        result = this.getWeight().intValue() < o.getWeight().intValue() ? 1 : (this.getWeight().intValue() == o.getWeight().intValue() ? 0 : -1);
        if (result == 0) {
            result = this.getId().intValue() > o.getId().intValue() ? 1 : (this.getId().intValue() == o.getId().intValue() ? 0 : -1);
        }
        return result;
    }

    @Override
    public String toString() {
        return "CategoryEntity [id=" + id + ", name=" + name + "]";
    }

}
