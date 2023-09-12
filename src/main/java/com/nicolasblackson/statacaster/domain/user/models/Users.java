package com.nicolasblackson.statacaster.domain.user.models;

import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String nickName;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Attribute> attributes;
}
