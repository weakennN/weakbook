package com.weakennN.weakbook.entity;

import javax.persistence.*;

@Entity()
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Column(name = "entity_id")
    private Integer entityId;
    @ManyToOne
    private User sender;
    @Column(name = "seen")
    private boolean seen;
    @Enumerated(EnumType.ORDINAL)
    private NotificationType notificationType;

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
