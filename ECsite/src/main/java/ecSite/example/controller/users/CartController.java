package ecSite.example.controller.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecSite.example.model.entity.CourseEntity;
import ecSite.example.model.entity.UsersEntity;
import ecSite.example.service.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ProductService productService;
	
	// cart_list.htmlを表示する一連の操作
	@GetMapping("/user/cart")
	public String getCartPage(Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		if(usersEntity == null) {
			return "redirect:/user/login";
		} 
		// そうでない場合、
		else {
			// セッションからcartのデータを取得又はcartが存在しない場合(cart == null) ,新しいcartを作る
			List<CourseEntity> cart = (List<CourseEntity>) session.getAttribute("cart");
			if (cart == null) {
				cart = new ArrayList<>();
			}
			// sizeメソッドでcartの要素数を取得totalQuantityに代入
			int totalQuantity = cart.size();
			// Streamのapiを利用して、　mapToIntでCourseFeeをintにして、sumメソッドで合計金額を計算しtotalPriceに代入
			int totalPrice = cart.stream().mapToInt(course -> Integer.parseInt(course.getCourseFee())).sum();
			
			// カート画面のhtmlを表示してログインしている人の名前をカート画面のhtmlに表示
			model.addAttribute("userName", usersEntity.getUserName());
			
			//　各のデータ（cart List, 合計数, 合計金額 ）をcartページに渡す
			model.addAttribute("cart", cart);
            model.addAttribute("totalQuantity", totalQuantity);
            model.addAttribute("totalPrice", totalPrice);
            // もし、cartの要素数がゼロだったら、emptyメッセージを表す
            // cart_list.htmlを表示する
			if (cart.isEmpty()) {
				model.addAttribute("empty", true);
				return "user/cart_list.html";
			} else {
	        // もし、cartの要素数がゼロじゃなかったら、emptyをfalseに設置,メッセージを表さない
	        // cart_list.htmlを表示する
			model.addAttribute("empty", false);
			return "user/cart_list.html";
			}
		}
	}
	
	// 商品ページからcartに追加api
	@PostMapping("/add-to-cart/process")
	public String productAddToCart(@RequestParam("courseId") Long courseId, Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
        if (usersEntity == null) {
            return "redirect:/user/login";
        }  
        // courseIdを使って商品のデータをprodcutに代入
        CourseEntity product = productService.getProductById(courseId);
        // もし、prdouctが削除されたときに,errorメッセージを商品ページに渡して、product_list.htmlを表示する
		if(product == null) {
			model.addAttribute("error", "商品は存在しません");
            return "redirect:/user/product/list";
		}
		// セッションからcartのデータを取得,又はcartが存在しない場合(cart == null) ,新しいcartを作る
		List<CourseEntity> cart = (List<CourseEntity>) session.getAttribute("cart");
		if (cart == null) {
			cart = new ArrayList<>();
		}
		// addメソッドでproductオブジェクトをcart Listに入れる
		// セッションでcartを設置する
		cart.add(product);
		session.setAttribute("cart", cart);
		// 条件が成功したら、cartページを表示する
		return "redirect:/user/cart";
	}
	
	// カートページからcartにあるitemを削除api
	@PostMapping("/remove-cart-item")
	public String removeCartItem(@RequestParam("courseId") Long courseId, Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		if (usersEntity == null) {
            return "redirect:/user/login";
        }
		// セッションからcartのデータを取得
        List<CourseEntity> cart = (List<CourseEntity>) session.getAttribute("cart");
        // cartは空じゃなかったら、removeifメソッドで該当するcourseIdのitemを削除
        if (cart != null) {
            cart.removeIf(course -> course.getCourseId().equals(courseId));
            // 削除後のcartを設置する
            session.setAttribute("cart", cart);
        }
        // 削除後のカートページを表示する
        return "redirect:/user/cart";
	}
	
}
