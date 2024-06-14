package ecSite.example.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/user/payment/selection")
    public String getPaymentSelectionPage(Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		if(usersEntity == null) {
			return "redirect:/user/login";
		} else {
			// そうでない場合
			return "user/payment_selection.html";
		}

    }
    
    // 支払い方法を選択する処理
    @PostMapping("/user/choose_payment")
    public String choosePaymentMethod(@RequestParam("payment") String paymentMethod, Model model) {
        // ユーザーが選択さた支払い方法をセッションに保存
    	session.setAttribute("paymentMethod", paymentMethod);
    	// クレジットカード決済を選択した場合は、クレジットカード情報入力フォームに戻す
        model.addAttribute("payment", paymentMethod);
        return "user/payment_selection.html";
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
        return "redirect:/user/payment/confirm";
    }
}
