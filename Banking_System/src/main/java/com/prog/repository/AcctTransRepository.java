package com.prog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prog.entites.AccountTransaction;
import com.prog.entites.UserDtls;

public interface AcctTransRepository extends JpaRepository<AccountTransaction, Integer> {

	public List<AccountTransaction> findByUser(UserDtls user);

}
