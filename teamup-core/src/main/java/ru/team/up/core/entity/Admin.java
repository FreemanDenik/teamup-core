package ru.team.up.core.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "ADMIN_ACCOUNT")
public class Admin extends Account{

}
