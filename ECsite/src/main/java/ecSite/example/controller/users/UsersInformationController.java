package ecSite.example.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecSite.example.model.dao.UsersDao;
import ecSite.example.model.entity.UsersEntity;
import ecSite.example.service.UsersService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsersInformationController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private HttpSession session;

	// 個人情報の表示画面
	@GetMapping("/user/information")
	public String getUserInfo(Model model) {
		// sessionからloginしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし account == null,login画面にリダイレクトする
		if (usersEntity == null) {
			return "redirect:/user/login";
		} else {
			//そうでない場合、更新する内容を画面に渡す
			model.addAttribute("userName", usersEntity.getUserName());
			model.addAttribute("userEmail", usersEntity.getUserEmail());
			return "user/user_information.html";
		}
	}

	// 個人情報の更新処理
	@PostMapping("/user/information/process")
	public String updateInformation(@RequestParam String userName, @RequestParam String userEmail, Model model) {
		// sessionからlogin人の情報を usersEntityという変数に格納
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		//もし account == null だったら、login画面にリダイレクトする
		if (usersEntity == null) {
			return "redirect:/user/login";
		} else {
			//そうでない場合、保存処理をする
	        usersService.updateUserInformation(usersEntity.getUserId(),userName, userEmail);
	        usersEntity.setUserName(userName);
	        usersEntity.setUserEmail(userEmail);
	        session.setAttribute("loginUserInfo", usersEntity);
	        model.addAttribute("userName", userName);
	        model.addAttribute("userEmail", userEmail);
	        return "user/user_information.html";
	    }
	}
	
	//	my page 画面表示
	@GetMapping("/user/mypage")
	public String getUserMyPage(Model model) {
		// sessionからloginしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		//もし account == null だったら、login画面にリダイレクトする
		if(usersEntity == null) {
			return "redirect:/user/login";
		}else {
			//そうでない場合、mypage画面のhtmlを表示、loginしている人の情報を画面に表示
			model.addAttribute("userName", usersEntity.getUserName());
			return "user/mypage.html";
		}
		
	}
}
