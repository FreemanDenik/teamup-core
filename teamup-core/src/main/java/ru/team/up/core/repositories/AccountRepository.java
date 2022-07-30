package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);

    List<Account> findAllByRole(Role role);
    boolean existsByEmail(String email);
    @Query("FROM Account WHERE username LIKE %?1%")
    Account findByUserName (String userName);




}
