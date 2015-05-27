package cn.flus.cms.core.dao.domain;

import java.util.Date;

public class TopicEntity {

    private Integer id;

    private Integer categoryId;

    private String  title;

    private String  summary;

    private String  thumbnail;

    private String  content;

    private String  keywords;

    private Long    authorId;

    private String  author;

    private String  source;

    private Integer status;

    private String  linkOut;

    private String  linkUrl;

    private String  allowReply;

    private String  placeTop;

    private Integer viewCount;

    private Integer replyCount;

    private Integer praiseCount;

    private Date    createTime;

    private Date    publishTime;

    private Long    lastReplyerId;

    private String  lastReplyer;

    private Date    lastReplyTime;

    private String  deleteFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLinkOut() {
        return linkOut;
    }

    public void setLinkOut(String linkOut) {
        this.linkOut = linkOut;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getAllowReply() {
        return allowReply;
    }

    public void setAllowReply(String allowReply) {
        this.allowReply = allowReply;
    }

    public String getPlaceTop() {
        return placeTop;
    }

    public void setPlaceTop(String placeTop) {
        this.placeTop = placeTop;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Long getLastReplyerId() {
        return lastReplyerId;
    }

    public void setLastReplyerId(Long lastReplyerId) {
        this.lastReplyerId = lastReplyerId;
    }

    public String getLastReplyer() {
        return lastReplyer;
    }

    public void setLastReplyer(String lastReplyer) {
        this.lastReplyer = lastReplyer;
    }

    public Date getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public String toString() {
        return "TopicEntity [id=" + id + ", title=" + title + "]";
    }

}
