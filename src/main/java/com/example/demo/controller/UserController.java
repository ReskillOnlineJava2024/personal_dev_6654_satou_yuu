package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
@Controller
public class UserController {
	
	@Autowired
	Account account;
	@Autowired
	UserRepository userRepository;
	@Autowired
	HttpSession session;
	
	//ログインの表示
	@GetMapping({"/login","/logout"})
	public String index() {
		return "login";
	}
	
//	//ログイン操作
    @PostMapping("/login")
    public String login(
			@RequestParam(name="name",defaultValue="")String name,
			@RequestParam(name="password",defaultValue="")String password,
			Model model){
    	if(name.length()==0||password.length()==0) {
			model.addAttribute("error","メールアドレスとパスワードを入力して下さい");
			return "login";
		}
		List<User> userList=userRepository.findByNameAndPassword(name,password);
		if(userList==null||userList.size()==0) {
			model.addAttribute("error","メールアドレスとパスワードが一致しません");
			return "login";
		}
		User user=userList.get(0);
		account.setName(user.getName());
		account.setPassword(user.getPassword());
		return "redirect:/recipes";
	}
	
	//新規登録の表示
	@GetMapping("/user/add")
	public String add() {
		return "createUser";
	}
	
	//新規登録処理
	@PostMapping("user/add")
	public String create(
			@RequestParam(name="name",defaultValue="")String name,
			@RequestParam(name="password",defaultValue="")String password,
			Model model) {
		if(name.length()==0||password.length()==0) {
			model.addAttribute("error","メールアドレスとパスワードを入力して下さい");
			return "createUser";
		}
		User user=new User(name,password);
		userRepository.save(user);
		model.addAttribute("message","登録しました");
		return "successCreate";
	}
	

}
