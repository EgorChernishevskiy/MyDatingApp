package com.example.deck_service.entity.profile;

import com.example.deck_service.utils.enums.Gender;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "preferences")
public class PreferencesEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "user_id")
    private ProfileEntity profile;

    private Integer ageMin;

    private Integer ageMax;

    private Integer radius;

}
