package com.artgeektech.househub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by guang on 9:34 AM 5/2/18.
 */
@Entity
@Table(name = "house_user",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"houseId", "userId", "type"})})
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdTime", "updatedTime"}, allowGetters = true)
public class HouseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long houseId;

    @NotNull
    private Long userId;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Integer type;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedTime;

    public HouseUser(Long houseId, Long userId, Integer type) {
        this.houseId = houseId;
        this.userId = userId;
        this.type = type;
    }

    public HouseUser() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
