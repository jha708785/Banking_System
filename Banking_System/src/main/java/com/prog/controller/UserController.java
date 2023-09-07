package com.prog.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prog.entites.AccountBalance;
import com.prog.entites.UserDtls;
import com.prog.repository.AcctBalanceRepository;
import com.prog.repository.AcctTransRepository;
import com.prog.repository.UserRepository;
import com.prog.service.AdminService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@Autowired
	private AcctTransRepository accTransRepo;

	@Autowired
	private AdminService adminService;

	@Autowired
	private AcctBalanceRepository acctBalRepo;

	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		String email = p.getName();
		UserDtls user = userRepo.findByUsername(email);
		m.addAttribute("user", user);
	}

	@GetMapping("/")
	public String home() {
		return "user/home";
	}

	@GetMapping("/changPass")
	public String loadChnagePassword() {
		return "user/change_password";
	}

	@PostMapping("/updatePassword")
	public String changePassword(Principal p, @RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass, HttpSession session) {

		String email = p.getName();

		UserDtls loginUser = userRepo.findByUsername(email);

		boolean f = passwordEncode.matches(oldPass, loginUser.getPassword());

		if (f) {
			loginUser.setPassword(passwordEncode.encode(newPass));
			UserDtls updatePasswordUser = userRepo.save(loginUser);
			if (updatePasswordUser != null) {
				session.setAttribute("msg", "Password Change Sucess");
			} else {
				session.setAttribute("msg", "something wrong on server");
			}
		} else {
			session.setAttribute("msg", "old password incorrect");
		}

		return "redirect:/user/changPass";
	}

	@GetMapping("/viewProfile")
	public String viewProfile() {
		return "user/view_profile";
	}

	@GetMapping("/sendMoney")
	public String sendMoney() {
		return "user/send_money";
	}

	@PostMapping("/sendMoneyTrans")
	public String sendMoneyTrans(HttpSession session, Principal p, @RequestParam String accNo,
			@RequestParam String name, @RequestParam String amt) {

		String email = p.getName();
		UserDtls user = userRepo.findByUsername(email);

		AccountBalance actBal = acctBalRepo.findByUser(user);

		Double amtx = Double.parseDouble(amt);

//		System.out.println(actBal.getTotalBalance());
//		System.out.println(amtx);

		if (actBal.getTotalBalance() < amtx) {
			session.setAttribute("msg", "Insufficent balance");
		} else {
			adminService.sendMoney(amtx, user, accNo);
			session.setAttribute("msg", "Money Send Sucess");
		}

		return "redirect:/user/sendMoney";
	}

	@GetMapping("/viewBalance")
	public String viewBalance() {
		return "user/view_balance";
	}

	@GetMapping("/allTransaction")
	public String allTransaction(Model m, Principal p) {
		String email = p.getName();
		UserDtls user = userRepo.findByUsername(email);

		m.addAttribute("accTrans", accTransRepo.findByUser(user));

		return "user/all_transaction";
	}

}
