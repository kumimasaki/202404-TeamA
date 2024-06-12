package ecSite.example.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminLogoutController {
	
	// Sessionげ使えるように宣言
	@Autowired
	private HttpSession session;
	
	//管理者logout 処理
	@GetMapping("/admin/logout")
	public String adminLogout() {
		//sessionの無効化
		session.invalidate();
		return "redirect:/admin/login";
	}
}
