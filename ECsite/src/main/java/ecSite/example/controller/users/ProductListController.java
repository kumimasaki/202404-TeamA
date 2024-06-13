package ecSite.example.controller.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ecSite.example.model.entity.CourseEntity;
import ecSite.example.model.entity.UsersEntity;
import ecSite.example.service.ProductService;
import jakarta.servlet.http.HttpSession;


@Controller
public class ProductListController {
	// Sessionげ使えるように宣言
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ProductService productService;
	
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
			List<CourseEntity> productList = productService.selectAllProductList(usersEntity.getUserId());
			model.addAttribute("userName", usersEntity.getUserName());
			model.addAttribute("productList", productList);
			if (productList.isEmpty()) {
				model.addAttribute("empty", true);
			}
			model.addAttribute("empty", false);
			return "user/product_list.html";
		}
	}
	
	// 商品(講座)詳細一覧画面を表示
    @GetMapping("/user/product/detail/{courseId}")
    // PathVariableを使って、各商品のURLから各商品のIDをパラメータとして取得
    public String getProductDetailPage(@PathVariable Long courseId, Model model) {
    	// セッションからログインしている人の情報を取得
    	UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		//そうでない場合、商品一覧画面のhtmlを表示してログインしている人の名前を商品一覧画面のhtmlに表示
		if(usersEntity == null) {
			return "redirect:/user/login";
    	} else {
    		// getProductByIdメソッドを呼び出して、対応する商品の情報を取得して格納
    		CourseEntity product = productService.getProductById(courseId);
    		model.addAttribute("product", product);
    		model.addAttribute("userName", usersEntity.getUserName());
    		return "user/product_detail.html";
    	}
    }

}
