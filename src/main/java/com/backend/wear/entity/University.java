package com.backend.wear.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
@Table(name="university")
@Entity
public class University extends BaseEntity {

    //아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //대학교 이름
    @Column(name="university_name")
    private String universityName;

    //대학교 학생들
    @OneToMany(mappedBy = "university", fetch = FetchType.LAZY)
    private List<User> userList = new ArrayList<>();

    //대학교 환경 점수, 기부 횟수, 등..
}
