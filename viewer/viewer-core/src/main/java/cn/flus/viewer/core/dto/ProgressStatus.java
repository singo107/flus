package cn.flus.viewer.core.dto;

public class ProgressStatus {

    private String guid;
    private String type;
    private String hasThumbnail;
    private String progress;
    private String startedAt;
    private String status;
    private String urn;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHasThumbnail() {
        return hasThumbnail;
    }

    public void setHasThumbnail(String hasThumbnail) {
        this.hasThumbnail = hasThumbnail;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    @Override
    public String toString() {
        return "ProgressStatus [guid=" + guid + ", type=" + type + ", hasThumbnail=" + hasThumbnail + ", progress="
               + progress + ", startedAt=" + startedAt + ", status=" + status + ", urn=" + urn + "]";
    }

}
