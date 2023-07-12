package ru.manturov.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.manturov.security.UserRole;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "user_")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Account> accounts;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role")
    @Column(name = "role")
    private Set<UserRole> roles = emptySet();
}
