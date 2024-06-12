package ecSite.example.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ecSite.example.service.AdminService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminRegisterController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private AdminService adminService;

	// 管理者登録画面表示
	@GetMapping("/admin/register")
	public String getAdminRegisterPage(Model model) {
		model.addAttribute("error", false);
		return "admin/admin_register.html";
	}

	// 管理者登録処理
	@PostMapping("/admin/confirm")
	public String getAdminRegisterConfirm(@RequestParam String adminName, @RequestParam String adminEmail,
			@RequestParam String adminPassword, Model model) {
		// もし、createAdminがtrueだったら、 admin_register_confirm.htmlに移動
		// そうでない場合admin_register.htmlにとどまります
		if (adminService.userCheck( adminEmail)) {
			model.addAttribute("adminName", adminName);
			model.addAttribute("adminEmail", adminEmail);
			session.setAttribute("adminName", adminName);
			session.setAttribute("adminEmail",adminEmail);
			session.setAttribute("adminPassword", adminPassword);
			return "admin/admin_register_confirm.html";
		} else {
			model.addAttribute("error", true);
			return "admin/admin_register.html";
		}
	}

	// 管理者登録確認処理
	@PostMapping("/admin/register/process")
	public String getAdminRegisterProcess(Model model) {
		//sessionから値を取得
		String name = (String) session.getAttribute("adminName");
		String email = (String) session.getAttribute("adminEmail");
		String password = (String) session.getAttribute("adminPassword");
		if(adminService.createAdmin(name, email, password)) {
			session.invalidate();
			return "admin/admin_login.html";
		}else {
			model.addAttribute("error", true);
			session.invalidate();
			return "admin/admin_register.html";
			
		}
		
	}

}

