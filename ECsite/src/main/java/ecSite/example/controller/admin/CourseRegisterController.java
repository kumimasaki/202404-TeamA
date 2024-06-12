package ecSite.example.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ecSite.example.model.entity.AdminEntity;
import ecSite.example.service.CourseService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CourseRegisterController {
	@Autowired 
	private CourseService courseService;
	
	@Autowired
	private HttpSession session;
	
	//講座登録画面の表示
	@GetMapping("/admin/course/register")
	public String getCourseRegisterPage(Model model) {
		//セッションからログインしている人の情報をadminという変数に格納
		AdminEntity admin = (AdminEntity) session.getAttribute("loginAdminInfo");
		//もしadmin== nullログイン画面にリダイレクトする
		//そうでない場合は,ログインしている人の名前を画面に渡す
		//講座登録画面のhtmlを表示させる
		if(admin == null) {
			return "redirect:/admin/login";
		}else {
			model.addAttribute("adminName", admin.getAdminName());
			return "admin/course_register.html";
		}
	}


	//講座登録処理
	@PostMapping("/admin/course/register/process")
	public String courseRegisterProcess(@RequestParam Date startDate,
			@RequestParam(name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime, @RequestParam(name = "finishTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime finishTime,
			@RequestParam String courseName, @RequestParam String courseDetail,
			@RequestParam String courseFee, @RequestParam MultipartFile courseImage,
		  Model model) {
		
		//セッションからログインしている人の情報をadminという変数に格納
		AdminEntity admin = (AdminEntity) session.getAttribute("loginAdminInfo");
		
		//もし、admin==nullだったら、ログイン画面にリダイレクトする
		//そうでない場合は、画像のファイル名を取得
		//画像のアップロード
		//もし、同じファイルの名前がなかったら保存
		//商品一覧にリダイレクトする
		//そうでない場合は、商品登録画面にとどまります
		
		if(admin == null) {
			return "redirect:/admin/login";
		}else {
			//ファイルの名前を取得
			String fileName = courseImage.getOriginalFilename();
			
			//ファイルの保存作業
			try {
				Files.copy(courseImage.getInputStream(), Path.of("src/main/resources/static/course-img/" + fileName));
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			if(courseService.createCourse(startDate, startTime, finishTime,
					courseName, courseDetail, courseFee, 
					fileName, admin.getAdminId())) {
				return "admin/course_register_finish.html";
			}else {
				return "admin/course_register.html";
			}
		}
	}

}
