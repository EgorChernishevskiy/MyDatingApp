package com.example.deck_service.entity.profile;

import com.example.deck_service.utils.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer age;

    private Gender gender;

    private String about;
}
