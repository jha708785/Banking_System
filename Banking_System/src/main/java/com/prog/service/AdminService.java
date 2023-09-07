package com.prog.service;

import java.util.List;

import com.prog.entites.AccountBalance;
import com.prog.entites.AccountTransaction;
import com.prog.entites.UserDtls;

public interface AdminService {
	public List<UserDtls> getAllUserByStatus(String role, String status);

	public AccountTransaction saveTrans(AccountTransaction trans);

	public AccountBalance saveBalance(AccountBalance bal);

	public List<AccountTransaction> getAllTrans();

	public UserDtls getDetailsByAcccountNum(String accNum);

	public boolean saveTrans(String transType, Double amt, int id);

	public boolean sendMoney( Double amt, UserDtls user, String senderAcc);

}
