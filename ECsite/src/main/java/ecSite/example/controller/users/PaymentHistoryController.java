package ecSite.example.controller.users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ecSite.example.model.dao.TransactionHistoryDao;
import ecSite.example.model.dao.TransactionItemDao;
import ecSite.example.model.entity.CourseEntity;
import ecSite.example.model.entity.TransactionHistory;
import ecSite.example.model.entity.TransactionItem;
import ecSite.example.model.entity.UsersEntity;
import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentHistoryController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private TransactionHistoryDao transHistoryDao;
	
	@Autowired
	private TransactionItemDao transItemDao;
	
	@GetMapping("/user/history")
	public String getOrderHistoryPage(Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		if(usersEntity == null) {
			return "redirect:/user/login";
		} 
		
		// ユーザーの購入履歴を取得します
		List<TransactionHistory> transHistory = transHistoryDao.findByUserId(usersEntity.getUserId());
		model.addAttribute("userName", usersEntity.getUserName());
		model.addAttribute("transHistory", transHistory);
		
		return "user/product_order_hitstory.html";
	}
	
	@GetMapping("/user/history/{transactionId}")
	public String getOrderHistoryDetailPage(@PathVariable Long transactionId,Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		if(usersEntity == null) {
			return "redirect:/user/login";
		}
		
		// ユーザーの購入履歴を取得します orElse(存在しない場合条件を当たる)
		TransactionHistory transHistory = transHistoryDao.findByTransactionId(transactionId);
		if (transHistory == null) {
			return "redirect:/user/history";
		}
		
		// 履歴の商品を取得する
		List<TransactionItem> transItem = transItemDao.findByTransactionHistory(transHistory);
	    
		model.addAttribute("userName", usersEntity.getUserName());
		model.addAttribute("transHistory", transHistory);
		model.addAttribute("transItem", transItem);
		
		return "user/order_history_detail.html";
	}
	
	
	// 決済するとき、cartのデータを履歴に保存するAPI
	@PostMapping("/user/payment_success")
	public String completeTransaction(Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		if(usersEntity == null) {
			return "redirect:/user/login";
		} 
		
    	// セッションからcartのデータを取得
        List<CourseEntity> cart = (List<CourseEntity>) session.getAttribute("cart");
        // もし、cart sessionは存在しない、そしてcartに商品が入ってない状態だったら、決済ページに遷移できません。
        if (cart == null || cart.isEmpty()) {
            model.addAttribute("empty", true);
            return "redirect:/user/cart";
        }
        
		// Streamのapiを利用して、　mapToIntでCourseFeeをintにして、sumメソッドで合計金額を計算しtotalPriceに代入
		int totalPrice = cart.stream().mapToInt(course -> Integer.parseInt(course.getCourseFee())).sum();
				
		
		// 決済した内容をデータとしてtransHistoryDao.saveでDBに保存する
		TransactionHistory transHistory = new TransactionHistory(usersEntity.getUserId(), totalPrice, LocalDateTime.now());
		transHistory = transHistoryDao.save(transHistory);
		
		// transItemsのarrayListを作成して,cartにある各courseIdとDBに新しいIDを生成したtransactionIdをtransItemsにforeachで入力
		List<TransactionItem> transItems = new ArrayList<>();
		for (CourseEntity course : cart) {
			TransactionItem transItem = new TransactionItem(course);
		    transItem.setTransactionHistory(transHistory);
			transItems.add(transItem);
		}
		// saveAllメソッド各itemを保存する
		transItemDao.saveAll(transItems);
		// cartをリセット
		session.setAttribute("cart", new ArrayList<>());
		
		model.addAttribute("userName", usersEntity.getUserName());
		return "user/payment_success.html";
	}
}
