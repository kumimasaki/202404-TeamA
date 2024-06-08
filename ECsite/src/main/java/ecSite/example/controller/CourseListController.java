package ecSite.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ecSite.example.model.entity.AdminEntity;
import jakarta.servlet.http.HttpSession;

@Controller
public class CourseListController {
	
	//@Autowiredアノテーションを使って
	//session変数を用意してSessionのユーザ情報を取得できるようにする
	@Autowired
	private HttpSession session;
	
	//口座一覧画面の表示
	@GetMapping("/course/list")
	public String getCourseListPage(Model model) {
		//sessionからログインしてる人の情報を取得
		AdminEntity adminEntity = (AdminEntity) session.getAttribute("loginAdminInfo");
		
		//もし、adminEntity== null ログイン画面にリダイレクトする
		//そうでない場合は、ブログ一覧のhtmlを表示してloginしている人の情報を画面に渡す
		if(adminEntity ==null) {
			return "redirect:/admin/login";
		}else {
			return"course_list.html";
		}
		
	}

}
