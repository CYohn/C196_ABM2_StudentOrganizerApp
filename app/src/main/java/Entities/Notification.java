package Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification")
public class Notification {

    @PrimaryKey(autoGenerate = true)
    private int notificationId;
    private String entityType;
    private String startOrEnd; // start = 0, end = 1
    private int entityIdNo;
    private String date;
    private Long broadcastIdNo;

    public Notification(final int notificationId, final String entityType, final int entityIdNo, String startOrEnd, String date, final Long broadcastIdNo) {
        this.notificationId = notificationId;
        this.entityType = entityType;
        this.entityIdNo = entityIdNo;
        this.startOrEnd = startOrEnd;
        this.date = date;
        this.broadcastIdNo = broadcastIdNo;
    }

    @Override
    public String toString() {
        return  date;
    }


    public String getStartOrEnd() {
        return this.startOrEnd;
    }

    public void setStartOrEnd(final String startOrEnd) {
        this.startOrEnd = startOrEnd;
    }

    public int getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(final int notificationId) {
        this.notificationId = notificationId;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(final String entityType) {
        this.entityType = entityType;
    }

    public int getEntityIdNo() {
        return this.entityIdNo;
    }

    public void setEntityIdNo(final int entityIdNo) {
        this.entityIdNo = entityIdNo;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getBroadcastIdNo() {
        return this.broadcastIdNo;
    }

    public void setBroadcastIdNo(final Long broadcastIdNo) {
        this.broadcastIdNo = broadcastIdNo;
    }
}
