package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	HttpSession session;
	
	@Autowired
	Account account;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping({"/login", "/logout"})
	public String index() {
		session.invalidate();
		return "login";
	}
	
	@PostMapping("/login")
	public String login(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {
		
		List<User> userList = userRepository.findByEmailAndPassword(email, password);
		if(userList.size() == 0) {
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			return "login";
		}
		
		User user = userList.get(0);
		account.setName(user.getName());
		account.setId(user.getId());

		return "redirect:/tasks";
	}
	
	@GetMapping("/users/new")
	public String create() {
		return "accountForm";
	}
	
	@PostMapping("users/add")
	public String add(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("password") String password,	
			Model model) {
		
		List<String> errorList = new ArrayList<>();
		if(name.length() == 0) {
			errorList.add("名前必須です");
		}
		if(email.length() == 0) {
			errorList.add("メールアドレスは必須です");
		}
		List<User> userList = userRepository.findByEmail(email);
		if(userList != null && userList.size() > 0) {
			errorList.add("登録済みのメールアドレスです");
		}
		if(password.length() == 0) {
			errorList.add("パスワードは必須です");
			}
		
		if(errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("name",name);
			model.addAttribute("email",email);
			return "accountForm";
		}
		
		User user = new User(name,email, password);
		userRepository.save(user);
		
		return "redirect:/login";
	}
	
	@GetMapping("/users/mypege")
	public String userMypage(
			Model model) {
		
		User user = userRepository.findById(account.getId()).get();
		model.addAttribute("user",user);
		
		return "myPege";
	}
	
	@GetMapping("users/{id}/edit")
	public String userEditDisp(
			@PathVariable("id") Integer id,
			Model model
			) {
		
		User user = userRepository.findById(id).get();
		model.addAttribute("user",user);
		
		return "editUser";
	}
	
	@PostMapping("users/{id}/edit")
	public String userEdit(
			@PathVariable("id") Integer id,
			@RequestParam("email") String email,
			@RequestParam("name") String name,
			@RequestParam("password") String password
			) {
		
		User user = userRepository.findById(id).get();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		userRepository.save(user);
		
		return "redirect:/users/mypege";
	}
	
	@PostMapping("users/{id}/delete")
	public String userDelete(@PathVariable("id") Integer id
			) {
		userRepository.deleteById(id);
		return "redirect:/login";
	}
	
	@PostMapping("users/{id}/back")
	public String userBack(
			@PathVariable("id") Integer id
			) {
		return "redirect:/tasks";
	}
}
