package ecSite.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecSite.example.model.entity.UsersEntity;
import ecSite.example.service.UsersService;

@Controller
public class UsersPasswordResetController {
    @Autowired
    private UsersService usersService;
    
	// パスワードリセットフォームの画面を表示
    @GetMapping("/user/password_reset")
    public String getUsersPasswordResetFormPage(Model model) {
        return "user/password_reset_form.html";
    }
    
    // パスワードリセット確認処理
    @PostMapping("/user/password_reset_confirm")
    public String usersPasswordResetConfirm(
            @RequestParam String userName,
            @RequestParam String userEmail,
            Model model) {
		// もし、resetCheckがtrueだったら、password_reset_confirm.htmlに遷移
		// そうでない場合、password_reset_form.htmlにとどまります
        if(usersService.resetCheck(userName, userEmail)) {
            model.addAttribute("userEmail", userEmail);
            return "user/password_reset_confirm.html";
        } else {
            model.addAttribute("error", "メールアドレスまたはパスワードが正しくありません");
            return "user/password_reset_form.html";
        }
    }
    
    // パスワードリセット処理
    @PostMapping("/user/password_reset_process")
    public String usersPasswordResetProcess(
    		@RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {
    	if(newPassword.equals(confirmPassword)) {
            usersService.resetUserPassword(newPassword);
            return "user/password_reset_success.html";
    	} else {
            model.addAttribute("error", "入力されたパスワードは一致しません");
            return "user/password_reset_confirm.html";
    	}

    }
}
