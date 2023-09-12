package com.nicolasblackson.statacaster.domain.action.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Data
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actionName;

    private Boolean trait;

    @Column(name = "attribute_id")
    private Long attributeId;
}
