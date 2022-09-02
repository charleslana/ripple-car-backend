package com.charles.ripplecarbackend.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_user_car")
public class UserCar implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "user_car_sequence", sequenceName = "user_car_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_car_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "level", nullable = false)
    private Long level = 1L;

    @Column(name = "experience", nullable = false)
    private Long experience = 0L;

    @Column(name = "broken", nullable = false)
    private Boolean broken = false;

    @Column(name = "busted", nullable = false)
    private Boolean busted = false;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
