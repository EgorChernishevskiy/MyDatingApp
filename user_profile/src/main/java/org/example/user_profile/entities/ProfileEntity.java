package org.example.user_profile.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.example.user_profile.utils.enums.Gender;

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
