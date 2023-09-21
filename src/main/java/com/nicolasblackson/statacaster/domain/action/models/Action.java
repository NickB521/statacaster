package com.nicolasblackson.statacaster.domain.action.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Data
@Table(name = "action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String actionName;

    private Boolean trait;

    @Column(name = "attribute_id")
    private Long attributeId;
}
