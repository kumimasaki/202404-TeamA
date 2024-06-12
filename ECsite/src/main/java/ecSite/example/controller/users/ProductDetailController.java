package ecSite.example.controller.users;

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
public class ProductDetailController {
	// Sessionげ使えるように宣言
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ProductService productService;
	
	// 商品(講座)詳細一覧画面を表示
    @GetMapping("/user/product/detail/{courseId}")
    // PathVariableを使って、各商品のURLから各商品のIDをパラメータとして取得
    public String getProductDetailPage(@PathVariable Long courseId, Model model) {
    	// getProductByIdメソッドを呼び出して、対応する商品の情報を取得して格納
        CourseEntity product = productService.getProductById(courseId);
        model.addAttribute("product", product);
        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
        model.addAttribute("userName", usersEntity.getUserName());
        return "product_detail.html";
    }
}
