package ecSite.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecSite.example.model.entity.UsersEntity;
import ecSite.example.service.UsersService;

@Controller
public class UsersRegisterController {
	@Autowired
	private UsersService usersService;
	
	//ユーザー登録画面の表示
	@GetMapping("/user/register")
	public String getUsersRegisterPage(Model model) {
		model.addAttribute("notConfirmedUser", new UsersEntity());
		return "user_register.html";
	}
	
	// ユーザー登録確認処理
	@PostMapping("/user/confirm")
	public String usersConfirm(
			@RequestParam String userName,
			@RequestParam String userEmail,
			@RequestParam String userPassword,
			@RequestParam String userTelephone,
			Model model) {
		// もし、createNotConfirmedUserがtrueだったら、user_register_confirm.htmlに遷移
		// そうでない場合、user_register.htmlにとどまります
		if(usersService.createNotConfirmedUser(userName, userEmail, userPassword, userTelephone)) {
			UsersEntity notConfirmedUser = usersService.getNotConfirmedUser();
			model.addAttribute("notConfirmedUser", notConfirmedUser);
			return "user_register_confirm.html";
		} else {
			model.addAttribute("error", "このメールアドレスはすでに存在してます");
			return "user_register.html";
		}
	}
	
	// ユーザー登録処理
	@PostMapping("/user/register/process")
	public String usersRegisterProcess() {
		usersService.createConfirmedUser();
		return "redirect:/user/login";
	}
}
