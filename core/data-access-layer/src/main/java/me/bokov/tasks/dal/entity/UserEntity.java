package me.bokov.tasks.dal.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode (callSuper = true)
@Entity
@Table (name = "users")
@Data
public class UserEntity extends AbstractBaseEntity {

    @Column (name = "display_name", nullable = false)
    private String displayName;

    @Column (name = "login_name", nullable = false, unique = true)
    private String loginName;

    @Column (name = "email", nullable = false, unique = true)
    private String email;

    @Column (name = "password_hash", nullable = false)
    private String passwordHash;

    @ElementCollection
    @CollectionTable (
            name = "user_roles",
            joinColumns = @JoinColumn (name = "user_id", referencedColumnName = "id", nullable = false)
    )
    @Column (name = "role_name", nullable = false)
    private Set <String> roles;

}
