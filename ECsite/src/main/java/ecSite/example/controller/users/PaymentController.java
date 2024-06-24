package ecSite.example.controller.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecSite.example.model.entity.CourseEntity;
import ecSite.example.model.entity.UsersEntity;
import ecSite.example.service.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	// Sessionげ使えるように宣言
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ProductService productService;
	
	// payment_selection.htmlを表示する
    @GetMapping("/user/payment_selection")
    public String getPaymentSelectionPage(Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		if(usersEntity == null) {
			return "redirect:/user/login";
		} else {
			// カート画面のhtmlを表示してログインしている人の名前をカート画面のhtmlに表示
			model.addAttribute("userName", usersEntity.getUserName());
			// そうでない場合
			return "user/payment_selection.html";
		}

    }
    // payment_confirmに遷移する処理、必要なデータを渡します
    @PostMapping("/user/payment_confirm")
    public String paymentConfirm(@RequestParam("paymentMethod") String paymentMethod, Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		if(usersEntity == null) {
			return "redirect:/user/login";
		} else {
		// セッションからcartのデータを取得
		List<CourseEntity> cart = (List<CourseEntity>) session.getAttribute("cart");
		// もし、cart sessionは存在しない、そしてcartに商品が入ってない状態だったら、カートページに戻る。
        if (cart == null || cart.isEmpty()) {
            return "redirect:/user/cart";
        	}
        
		// sizeメソッドでcartの要素数を取得totalQuantityに代入
		int totalQuantity = cart.size();
		// Streamのapiを利用して、　mapToIntでCourseFeeをintにして、sumメソッドで合計金額を計算しtotalPriceに代入
		int totalPrice = cart.stream().mapToInt(course -> Integer.parseInt(course.getCourseFee())).sum();
        
		//　各のデータ（ユーザー名,cart List, 合計数, 合計金額, 支払方法）をpayment_confirmページに渡す
        model.addAttribute("userName", usersEntity.getUserName());
        model.addAttribute("cart", cart);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentMethod", paymentMethod);
        
        return "user/payment_confirm.html";
		}
    }
    
    // 支払い方法を選択する処理
    @PostMapping("/user/choose_payment")
    public String choosePaymentMethod(@RequestParam("payment") String paymentMethod, Model model) {
        // ユーザーが選択さた支払い方法をセッションに保存
    	session.setAttribute("paymentMethod", paymentMethod);
    	// もし、クレジットカード決済を選択した場合は、クレジットカード情報入力フォームに戻す
        if ("credit_card".equals(paymentMethod)) {
        	return "redirect:/user/save_credit_card";
        } else {
        	// そうでない場合
            return "redirect:/user/payment_confirm";
        }
    }
    
    // ユーザーが入力されたクレジットカード情報を保存する処理
    @PostMapping("/user/save_credit_card")
    public String saveCreditCardInfo(
    		@RequestParam String cardNumber,
            @RequestParam String expiryDate,
            @RequestParam String cvv, 
            Model model) {
    	// ユーザーが入力されたクレジットカード情報をセッションに保存
        session.setAttribute("cardNumber", cardNumber);
        session.setAttribute("expiryDate", expiryDate);
        session.setAttribute("cvv", cvv);
        // payment_confirmにリダイレクトする
        return "redirect:/user/payment_confirm";
    }
}
