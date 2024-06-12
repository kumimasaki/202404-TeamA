package ecSite.example.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ecSite.example.model.entity.AdminEntity;
import ecSite.example.model.entity.CourseEntity;
import ecSite.example.service.CourseService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CourseListController {
	
	//@Autowiredアノテーションを使って
	//session変数を用意してSessionのユーザ情報を取得できるようにする
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CourseService courseService;
	
	
	//口座一覧画面の表示
	@GetMapping("/admin/course/list")
	public String getCourseListPage(Model model) {
		//sessionからログインしてる人の情報を取得
		AdminEntity adminEntity = (AdminEntity) session.getAttribute("loginAdminInfo");
		
		//もし、adminEntity== null ログイン画面にリダイレクトする
		//そうでない場合は、ブログ一覧のhtmlを表示してloginしている人の情報を画面に渡す
		if(adminEntity == null) {
			return "redirect:/admin/login";
		}else {
			//講座の情報を取得する
			List<CourseEntity> courseList = courseService.selectAllCourseList(adminEntity.getAdminId());
			model.addAttribute("adminName",adminEntity.getAdminName());
			model.addAttribute("courseList", courseList);
			return"admin/course_list.html";
		}
		
	}

}
