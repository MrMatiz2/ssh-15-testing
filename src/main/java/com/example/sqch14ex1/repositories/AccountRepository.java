package com.example.sqch14ex1.repositories;

import com.example.sqch14ex1.entities.Account;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query("SELECT * FROM account WHERE name = :name")
    List<Account> findAccountByName(String name);

    @Modifying
    @Query("UPDATE account set amount = :amount WHERE id = :id")
    void changeAmount(Long id, BigDecimal amount);

}
