package ecSite.example.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import ecSite.example.model.entity.AdminEntity;
import ecSite.example.service.CourseService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CourseDeleteController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private HttpSession session;
	
	@PostMapping("/admin/course/delete")
	public String courseDelete(Long courseId,Model model) {
		// セクションからログインしている人の情報をadminという変数に格納
		AdminEntity admin = (AdminEntity) session.getAttribute("loginAdminInfo");
		
		// もし、admin==nullだったら、ログイン画面にリダイレクトする
		if(admin == null) {
			return "redirect:/admin/login";
		}else {
			//もしdeleteCourseの結果がtrueだったら、
			if(courseService.deleteCourse(courseId)) {
				//講座の削除終了ページに移動
				model.addAttribute("adminName",admin.getAdminName());
				return "admin/course_delete_finish.html";
			}else {
				//そうでない場合は、
				//編集画面にリダイレクトする
				return "redirect:/admin/course/edit"+courseId;
			}
		}
	}

}
