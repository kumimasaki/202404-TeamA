package ecSite.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecSite.example.model.dao.CourseDao;
import ecSite.example.model.entity.CourseEntity;

@Service
public class ProductService {
	@Autowired
	private CourseDao courseDao;
	
	//商品一覧のチェック
	//もし、userId== null　戻り値としてnull
	//そうでない場合は、findAll 内容をコントローラークラスに渡す
	public List<CourseEntity>selectAllProductList(Long userId){
		if (userId == null) {
			return null;
		}else {
			return courseDao.findAll();
		}
	}
	// 商品詳細一覧のチェック
	// もしcourseId==null 戻り値としてnull
	// findByCourseId内容をコントローラークラスに渡す
	public CourseEntity getProductById(Long courseId) {
		if(courseId == null) {
			return null;
		} else {
			return courseDao.findByCourseId(courseId);
		}
	}

}
