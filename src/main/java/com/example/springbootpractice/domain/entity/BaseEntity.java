package com.example.springbootpractice.domain.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.springbootpractice.util.SecurityJwt;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant updatedAt;

    private String createdBy;
    private String updatedBy;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a",timezone = "GMT+7")

    @PrePersist
    public void prePersist() {
        String creator =SecurityJwt.getCurrentUserLogin().orElse("system");
        setCreatedBy(creator);
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedBy = SecurityJwt.getCurrentUserLogin().orElse("system");
    }
}
