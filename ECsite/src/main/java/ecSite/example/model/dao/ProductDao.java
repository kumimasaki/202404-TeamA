package ecSite.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecSite.example.model.entity.CourseEntity;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProductDao extends JpaRepository<CourseEntity ,Long>{
	
	//用途：商品(講座)一覧を表示させるときに使用
	//SELECT* FROM course
	List<CourseEntity>findAll();
	
	//用途：商品(講座)詳細一覧を表示させるときに使用
	//SELECT * FROM course WHERE course_id = ?
	CourseEntity findByCourseId(Long courseId);
	
}
