package com.prog.service;

import com.prog.entites.UserDtls;

public interface UserService {

	public UserDtls createUser(UserDtls user);

	public boolean checkEmail(String email);

	
	public UserDtls addEmp(UserDtls user);
}
