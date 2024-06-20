package ecSite.example.controller.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecSite.example.model.entity.CourseEntity;
import ecSite.example.model.entity.UsersEntity;
import ecSite.example.service.FavouriteService;
import ecSite.example.service.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
public class FavouriteController {
	@Autowired
	private FavouriteService favouriteService;

	@Autowired
	private ProductService productService;

	@Autowired
	private HttpSession session;

	// 気に入り画面を表示
	@GetMapping("/user/favourite")
	public String getFavouritePage(Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null だったら、ログイン画面にリダイレクトする
		if (usersEntity == null) {
			return "redirect:/user/login";
		} else {
			// そうでない場合は、
			// 気に入り一覧を表示、気に入り一覧にデータがない場合は、追加
			List<CourseEntity> favourite = (List<CourseEntity>) session.getAttribute("favourite");
			if (favourite == null) {
				favourite = new ArrayList<>();
			}
			model.addAttribute("userName", usersEntity.getUserName());
			model.addAttribute("favourite", favourite);

			// もし、favouritelistが空だったら、
			// 気に入り商品はまだありませんというメッセージを表示
			if (favourite.isEmpty()) {
				model.addAttribute("empty", true);
				return "user/product_like.html";
			} else {
				model.addAttribute("empty", false);
				return "user/product_like.html";
			}

		}
	}

	// 気に入り一覧の追加処理
	@PostMapping("/add-favourite/process")
	public String addFabourite(@RequestParam("courseId") Long courseId, Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		if (usersEntity == null) {
			return "redirect:/user/login";
		}
		// courseIdを使って商品のデータをproduct変数に格納
		CourseEntity product = productService.getProductById(courseId);
		// もし、productが削除されたときに,errorメッセージを商品ページに渡して、product_list.htmlを表示する
		if (product == null) {
			model.addAttribute("error", "商品は存在しません");
			return "redirect:/user/product/list";
		}
		// セッションからfavouriteのデータを取得,
		//favouriteが存在しない場合(favourite == null)
		// ,新しいfavouritelistを作る
		List<CourseEntity> favourite = (List<CourseEntity>) session.getAttribute("favourite");
		//String errorfav = "既に気に入り一覧に追加されています";
		//session.setAttribute("errorfav", errorfav);
		if (favourite == null && favouriteService.insertFavourite(courseId, usersEntity.getUserId())) {
			favourite = new ArrayList<>();
			// addメソッドでproductオブジェクトをfavourite Listに入れる
			// セッションでfavouriteを設置する
			favourite.add(product);
			session.setAttribute("favourite", favourite);
			// 条件が成功したら、favouriteページを表示する
			model.addAttribute("message","気に入り一覧に追加しました。");
			return "redirect:/user/favourite";
		} else {
			model.addAttribute("message","既に気に入り一覧に追加されています");
			return "redirect:/user/product/list";
		}
	}

	// 気に入り商品の削除処理
	@PostMapping("/delete-Favourite")
	public String deleteFavourite(@RequestParam("courseId") Long courseId, Model model) {
		// セッションからログインしている人の情報を取得
		UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUserInfo");
		// もし、usersEntity==null ログイン画面にリダイレクトする
		if (usersEntity == null) {
			return "redirect:/user/login";
		}
		// セッションからfavouriteのデータを取得,
		List<CourseEntity> favourite = (List<CourseEntity>) session.getAttribute("favourite");
		if(favourite != null && favouriteService.deleteFavourite(courseId)) {
			 favourite.removeIf(course -> course.getCourseId().equals(courseId));
			 session.setAttribute("favourite", favourite);
		}
		 // 削除後の気に入りページを表示する
        return "redirect:/user/favourite";
	}
}