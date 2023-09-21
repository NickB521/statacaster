package com.nicolasblackson.statacaster.domain.user.models;

import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Data
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String nickName;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Attribute> attributes;
}
