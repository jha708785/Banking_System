package com.prog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.prog.entites.UserDtls;
import com.prog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDtls createUser(UserDtls user) {

		user.setRole("ROLE_USER");
		user.setAccStatus("false");

		return userRepo.save(user);
	}

	@Override
	public boolean checkEmail(String email) {

		return userRepo.existsByEmail(email);
	}

	@Override
	public UserDtls addEmp(UserDtls user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_EMP");
		user.setAccStatus("true");
		return userRepo.save(user);
	}

}
