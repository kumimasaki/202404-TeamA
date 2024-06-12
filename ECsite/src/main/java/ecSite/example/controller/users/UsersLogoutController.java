package ecSite.example.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersLogoutController {
	// Sessionげ使えるように宣言
	@Autowired
	private HttpSession session;
	
	// ユーザーログアウト処理
	@GetMapping("/user/logout")
	public String usersLogout() {
		// セッションの無効化
		session.invalidate();
		return "redirect:/user/login";
	}
}
