package com.example.deck_service.entity.profile;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private ProfileEntity profile;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point coordinates;
}
