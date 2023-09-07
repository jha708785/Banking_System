package com.prog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prog.entites.AccountBalance;
import com.prog.entites.UserDtls;

public interface AcctBalanceRepository extends JpaRepository<AccountBalance, Integer> {

	public AccountBalance findByUser(UserDtls user);

}
