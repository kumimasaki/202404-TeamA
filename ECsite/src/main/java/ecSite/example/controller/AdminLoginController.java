package ecSite.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecSite.example.model.entity.AdminEntity;
import ecSite.example.service.AdminService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminLoginController {
	@Autowired
	private AdminService adminService;
	

	//@Autowiredアノテーションを使って
	//session変数を用意してSessionのユーザ情報を取得できるようにする
	@Autowired
	private HttpSession session;
	
	//ログイン画面を表示
	@GetMapping("/admin/login")
	public String getAdminLoginPage(Model model) {
		model.addAttribute("error", false);
		return "admin_login.html";
	}
	
	//ログイン画面の処理
	@PostMapping("/admin/login/process")
	public String adminLoginProcess(
			@RequestParam String adminEmail,
			@RequestParam String adminPassword,
			Model model) {
		

		//loginCheckメソッドを呼び出してその結果をuser　という変数に格納
		AdminEntity adminEntity = adminService.loginCheck(adminEmail, adminPassword);
		//もし、admin == null ログイン画面にとどまります
		//そうでない場合は、sessionにログイン情報を保存
		//ブログ一覧画面にダイレクトする
		if(adminEntity == null) {
			model.addAttribute("error" , true);
			return "admin_login.html";
		}else {
		    session.setAttribute("loginAdminInfo", adminEntity);
		    model.addAttribute("adminName",adminEntity.getAdminName());
			return "course_list.html";
		}
	}

}
