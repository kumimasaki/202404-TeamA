package ecSite.example.controller.admin;

import java.util.Date;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ecSite.example.model.entity.AdminEntity;
import ecSite.example.model.entity.CourseEntity;
import ecSite.example.service.CourseService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CourseEditController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private HttpSession session;

	// 編集画面の表示
	@GetMapping("/admin/course/edit/{courseId}")
	public String getCourseEditPage(@PathVariable Long courseId, Model model) {

		// sessionからログインしてる人の情報をadminという変数に格納
		AdminEntity admin = (AdminEntity) session.getAttribute("loginAdminInfo");
		// もしadmin == null だったら、ログイン画面にリダイレクトする

		if (admin == null) {
			return "redirect:/admin/login";
		} else {
			// 編集画面に表示させる情報をcourse変数に格納
			CourseEntity course = courseService.courseEditCheck(courseId);
			/*
			 * もし、courseEntity == null だったら、
			 * 講座一覧ページにリダイレクトする 
			 * そうでない場合は、
			 * 編集画面に編集する内容を渡す 編集画面を表示
			 */
			if (course == null) {
				return "redirect:/admin/course/list";
			} else {
				model.addAttribute("adminName", admin.getAdminName());
				model.addAttribute("course", course);
				return "admin/course_edit.html";
			}

		}
	}

	// 変更処理
	@PostMapping("/admin/course/edit/process")
	public String courseUpdate(@RequestParam java.sql.Date startDate,
			@RequestParam(name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
			@RequestParam(name = "finishTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime finishTime,
			@RequestParam String courseName, @RequestParam String courseDetail, @RequestParam String courseFee,
			@RequestParam MultipartFile courseImage, @RequestParam Long courseId, Model model) {

		// セッションからログインしている人の情報を取得
		AdminEntity admin = (AdminEntity) session.getAttribute("loginAdminInfo");

		// もし、admin==nullだったら、ログイン画面にリダイレクトする
		if (admin == null) {
			return "redirect:/admin/login";
			// そうでない場合は、
		} else {
			// ファイルの保存
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())+courseImage.getOriginalFilename();
			try {
				Files.copy(courseImage.getInputStream(), Path.of("src/main/resources/static/course-img/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// もし、productUpdateの結果がtrue の場合、変更終了ページに移動
			// そうでない場合は、商品編集画面にリダイレクトする
			if (courseService.courseUpdate(courseId, startDate, startTime, finishTime, courseName, courseDetail,
					courseFee, fileName, admin.getAdminId())) {
				model.addAttribute("adminName",admin.getAdminName());
				return "admin/course_edit_finish.html";
			} else {
				return "redirect:/admin/course/edit";
			}

		}
	}
}
