package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.*;

/**
 * Родительская сущность для пользователя, админа, модератора
 */
@NoArgsConstructor
@SuperBuilder
@Entity
@Getter
@Setter
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Account implements UserDetails {
    /**
     * Уникальный идентификатор
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя
     */
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    /**
     * Фамилия
     */
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    /**
     * Отчество
     */
    @Column(name = "MIDDLE_NAME")
    private String middleName;

    /**
     * Изображения акаунта
     */
    @OneToOne(fetch = FetchType.EAGER)
    private Image image;

    /**
     * Логин
     */
    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;
    /**
     * Роль
     */
    @Column(name = "ROLE")
    private Role role;
    /**
     * Электронная почта
     */
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    /**
     * Пароль
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * Время создания аккаунта
     */
    @Column(name = "ACCOUNT_CREATED_TIME", nullable = false)
    private LocalDate accountCreatedTime;

    /**
     * Время последней активности
     */
    @Column(name = "LAST_ACCOUNT_ACTIVITY", nullable = false)
    private LocalDateTime lastAccountActivity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
