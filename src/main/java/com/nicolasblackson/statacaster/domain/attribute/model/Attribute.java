package com.nicolasblackson.statacaster.domain.attribute.model;

import com.nicolasblackson.statacaster.domain.action.models.Action;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Data
@Table(name = "attribute")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String attributeName;

    @NonNull
    private Integer points;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany
    @JoinColumn(name = "attribute_id")
    private List<Action> actions;
}
