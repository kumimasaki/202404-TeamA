package ecSite.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ecSite.example.model.entity.UsersEntity;
import jakarta.servlet.http.HttpSession;


@Controller
public class ProductListController {
	// Sessionげ使えるように宣言
	@Autowired
	private HttpSession session;
	
	// 商品一覧画面を表示する
	@GetMapping("/user/product/list")
	public String getProductList(Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		//そうでない場合、商品一覧画面のhtmlを表示してログインしている人の名前を商品一覧画面のhtmlに表示
		if(usersEntity == null) {
			return "redirect:/user/login";
		} else {
			model.addAttribute("userName", usersEntity.getUserName());
			return "product_list.html";
		}
	}
}
