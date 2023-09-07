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
import org.springframework.web.bind.annotation.RequestParam;

import com.prog.entites.UserDtls;
import com.prog.repository.UserRepository;
import com.prog.service.AdminService;
import com.prog.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute
	private void userDetails(Model m, Principal p) {

		if (p != null) {
			String email = p.getName();
			UserDtls user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/create_account")
	public String createuser(@ModelAttribute UserDtls user, HttpSession session) {

		// System.out.println(user);

		boolean f = userService.checkEmail(user.getEmail());

		if (f) {
			session.setAttribute("msg", "Email Id alreday exists");
		}

		else {
			UserDtls userDtls = userService.createUser(user);
			if (userDtls != null) {
				session.setAttribute("msg", "Register Sucessfully");
			} else {
				session.setAttribute("msg", "Something wrong on server");
			}
		}

		return "redirect:/register";
	}

	@GetMapping("/netbanking")
	public String netbanking() {
		return "netbanking";
	}

	@PostMapping("/createNetbanking")
	public String createNetBankig(HttpSession session, @RequestParam String acno, @RequestParam String usname,
			@RequestParam String psw) {

		UserDtls user = userRepo.findByAccountNum(acno);

		if (user != null) {

			if (userRepo.existsByUsername(usname)) {
				session.setAttribute("msg", "username alreday exists");
			} else {
				user.setPassword(passwordEncoder.encode(psw));
				user.setUsername(usname);
				userRepo.save(user);
				session.setAttribute("msg", "Netbanking created sucessfully");
			}

		} else {
			session.setAttribute("msg", "Account number is incorrect");
		}

		return "redirect:/netbanking";
	}

}
