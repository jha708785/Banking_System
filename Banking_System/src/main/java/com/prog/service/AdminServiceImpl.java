package com.prog.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prog.entites.AccountBalance;
import com.prog.entites.AccountTransaction;
import com.prog.entites.UserDtls;
import com.prog.repository.AcctBalanceRepository;
import com.prog.repository.AcctTransRepository;
import com.prog.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	public UserRepository userRepo;

	@Autowired
	private AcctTransRepository accTransRepo;

	@Autowired
	private AcctBalanceRepository acctBalRepo;

	@Override
	public List<UserDtls> getAllUserByStatus(String role, String status) {

		return userRepo.findByRoleAndAccStatus(role, status);
	}

	@Override
	public AccountTransaction saveTrans(AccountTransaction trans) {

		trans.setDate(LocalDate.now());
		trans.setTime(LocalTime.now());
		trans.setTransType("Credit");
		trans.setTransDtls("Account Opening");
		trans.setBalance(500.00);

		return accTransRepo.save(trans);
	}

	@Override
	public AccountBalance saveBalance(AccountBalance bal) {
		return acctBalRepo.save(bal);
	}

	@Override
	public List<AccountTransaction> getAllTrans() {
		return accTransRepo.findAll();
	}

	@Override
	public UserDtls getDetailsByAcccountNum(String accNum) {
		return userRepo.findByAccountNum(accNum);
	}

	@Override
	public boolean saveTrans(String transType, Double amt, int id) {

		UserDtls user = userRepo.findById(id).get();
		AccountBalance acctBalance = acctBalRepo.findByUser(user);

		AccountTransaction actrns = new AccountTransaction();

		actrns.setUser(user);
		actrns.setTransType(transType);

		actrns.setTime(LocalTime.now());
		actrns.setDate(LocalDate.now());
		actrns.setBalance(amt);

		if ("Debit".equals(transType)) {
			actrns.setTransDtls("Debit By Bank");
			acctBalance.setTotalBalance(acctBalance.getTotalBalance() - amt);
		} else {
			actrns.setTransDtls("Deposit By Bank");
			acctBalance.setTotalBalance(acctBalance.getTotalBalance() + amt);
		}

		if (acctBalRepo.save(acctBalance) != null && accTransRepo.save(actrns) != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean sendMoney(Double amt, UserDtls user, String senderAcc) {

		AccountBalance acctBalance = acctBalRepo.findByUser(user);

		AccountTransaction actrns = new AccountTransaction();

		actrns.setUser(user);
		actrns.setTransType("Debit");

		actrns.setTime(LocalTime.now());
		actrns.setDate(LocalDate.now());
		actrns.setBalance(amt);

		actrns.setTransDtls("Send to acc no-" + senderAcc);
		acctBalance.setTotalBalance(acctBalance.getTotalBalance() - amt);

		if (acctBalRepo.save(acctBalance) != null && accTransRepo.save(actrns) != null) {
			return true;
		}

		return false;
	}

}
