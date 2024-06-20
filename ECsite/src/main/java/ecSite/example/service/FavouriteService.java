package ecSite.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecSite.example.model.dao.FavouriteDao;
import ecSite.example.model.entity.FavouriteEntity;

@Service 
public class FavouriteService {
	@Autowired
	private FavouriteDao favouriteDao;

	//講座の登録処理チェック
	public boolean insertFavourite( Long courseId, Long userId) {
		/*
		 * もし、findByFavouriteIdが==nullだったら、 保存処理 
		 * そうでない場合は false
		 */
		if(favouriteDao.findByCourseId(courseId)==null) {
			favouriteDao.save(new FavouriteEntity(courseId, userId));
			return true;
		}else {
			return false;
		}
	}
	
	//削除処理チェック
	public boolean deleteFavourite(Long courseId) {
		//もし、コントローラーからもらったcourseIdが nullであれば
		//削除できないことfalse
		//そうでない場合は、
		//deleteBycourseId を使用して商品の削除
		//true
		if(courseId == null) {
			return false;
		}else {
			favouriteDao.deleteByCourseId(courseId);
			return true;
		}
	}
}
