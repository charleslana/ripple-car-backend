package com.charles.ripplecarbackend.model.entity;

import com.charles.ripplecarbackend.enums.CarClassEnum;
import com.charles.ripplecarbackend.enums.RarityEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_car")
public class Car implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "car_sequence", sequenceName = "car_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "acceleration", nullable = false)
    private Long acceleration;

    @Column(name = "top_speed", nullable = false)
    private Long topSpeed;

    @Column(name = "control", nullable = false)
    private Long control;

    @Column(name = "weight", nullable = false)
    private Long weight;

    @Column(name = "toughness", nullable = false)
    private Long toughness;

    @Column(name = "potency", nullable = false)
    private Long potency;

    @Column(name = "nitro", nullable = false)
    private Long nitro;

    @Column(name = "rarity", nullable = false)
    @Enumerated(EnumType.STRING)
    private RarityEnum rarity;

    @Column(name = "car_class", nullable = false)
    @Enumerated(EnumType.STRING)
    private CarClassEnum carClass;

    @Column(name = "max_level", nullable = false)
    private Long maxLevel;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserCar> userCar;
}
