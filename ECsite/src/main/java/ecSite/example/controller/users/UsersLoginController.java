package ecSite.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecSite.example.model.entity.UsersEntity;
import ecSite.example.service.UsersService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsersLoginController {
	@Autowired
	private UsersService usersService;
	
	// Sessionげ使えるように宣言
	@Autowired
	private HttpSession session;
	
	// ユーザーログイン画面の表示
	@GetMapping("/user/login")
	public String getUsersLoginPage(Model model) {
		model.addAttribute("error", false);
		return "user/user_login.html";
	}
	
	// ユーザーログイン処理
	@PostMapping("/user/login/process")
	public String usersLoginProcess(
			@RequestParam String userEmail,
			@RequestParam String userPassword,
			Model model) {
		// loginChechメソッドを呼び出してその結果をusersEntityという変数に格納
		UsersEntity usersEntity = usersService.loginCheck(userEmail, userPassword);
		// もし、usersEntity==nullログイン画面にとどまります
		// そうでない場合、sessionにログイン情報に保存
		// 商品一覧画面にリダイレクトする
		
		if(usersEntity == null) {
			model.addAttribute("error", true);
			return "user/user_login.html";
		} else {
			session.setAttribute("loginUserInfo", usersEntity);
			return "redirect:/user/product/list";
		}
	}
	
}
