package com.nicolasblackson.statacaster.domain.attribute.model;

import com.nicolasblackson.statacaster.domain.action.models.Action;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Data
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String attributeName;

    private Integer points;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany
    @JoinColumn(name = "attribute_id")
    private List<Action> actions;
}
