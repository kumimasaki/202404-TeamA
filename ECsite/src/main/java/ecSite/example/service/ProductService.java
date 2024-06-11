package ecSite.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecSite.example.model.dao.ProductDao;
import ecSite.example.model.entity.CourseEntity;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	// 商品一覧のチェック
	// もしUserId==null 戻り値としてnull
	// findAll内容をコントローラークラスに渡す
	public List<CourseEntity> selectAllProductList(Long userId) {
		if(userId == null) {
			return null;
		} else {
			return productDao.findAll();
		}
	}

	// 商品詳細一覧のチェック
	// もしcourseId==null 戻り値としてnull
	// findByCourseId内容をコントローラークラスに渡す
	public CourseEntity getProductById(Long courseId) {
		if(courseId == null) {
			return null;
		} else {
			return productDao.findByCourseId(courseId);
		}
	}
}
