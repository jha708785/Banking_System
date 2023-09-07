package com.prog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prog.entites.UserDtls;
import com.prog.repository.UserRepository;
import com.prog.service.AdminService;
import com.prog.service.UserService;

@Controller
@RequestMapping("/emp")
public class EmpController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String index() {
		return "emp/home";
	}

	@GetMapping("/allTrans")
	public String allTrans(Model m) {
		m.addAttribute("trans", adminService.getAllTrans());
		return "emp/all_transaction";
	}

	@GetMapping("/trans")
	public String trans(Model m) {
		m.addAttribute("st", "no");
		return "emp/transaction";
	}

	@PostMapping("/searchAcct")
	public String searchAccount(@RequestParam("accNum") String accNum, Model m) {
		m.addAttribute("st", "yes");
		m.addAttribute("acct", adminService.getDetailsByAcccountNum(accNum.trim()));

		return "emp/transaction";
	}

	@GetMapping("/account")
	public String account(Model m) {
		m.addAttribute("accSt", adminService.getAllUserByStatus("ROLE_USER", "true"));
		return "emp/all_account";
	}

	@GetMapping("/viewAccount/{id}")
	public String viewAccount(Model m, @PathVariable int id) {
		m.addAttribute("user", userRepo.findById(id).get());
		return "emp/view_account";
	}

	@PostMapping("/saveTrans")
	public String saveTransaction(@RequestParam String transType, @RequestParam String amt, @RequestParam int id,
			Model m, HttpSession session, @RequestParam String tbalance, @RequestParam String accno) {

		Double amtx = Double.parseDouble(amt);
		Double tamtx = Double.parseDouble(tbalance);

		if ("Debit".equals(transType)) {
			if (amtx > tamtx) {
				session.setAttribute("msg", "insufficent Balance");
			} else {
				adminService.saveTrans(transType, amtx, id);
				session.setAttribute("sucMsg", "Transaction success");
			}

		} else if ("Credit".equals(transType)) {
			adminService.saveTrans(transType, amtx, id);
			session.setAttribute("sucMsg", "Transaction success");
		} else {
			session.setAttribute("msg", "Choose Transaction Type");
		}
		return "redirect:/emp/trans";
	}

	@GetMapping("/addCustomer")
	public String addCustomer() {
		return "emp/add_customer";
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
				session.setAttribute("msg", "Register Sucessfully ! Admin Will Approve/Reject Account");
			} else {
				session.setAttribute("msg", "Something wrong on server");
			}
		}

		return "redirect:/emp/addCustomer";
	}

}
