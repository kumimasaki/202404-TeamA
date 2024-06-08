package ecSite.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecSite.example.model.entity.CourseEntity;
import jakarta.transaction.Transactional;

//Springにdaoクラスのお知らせアノテーション
@Repository
//DBに対して一連の操作をひとまとめにする
@Transactional
public interface CourseDao extends JpaRepository<CourseEntity ,Long > {
	
	//保存処理と更新処理insert and update
	CourseEntity save(CourseEntity courseEntity);
	
	//用途：講座一覧を表示させるときに使用
	//SELECT* FROM course
	List<CourseEntity>findAll();

	//SELECT * FROM course WHERE  courseName = ?
	//用途：courseの登録チェックに使用　同じcourse名が登録されないようにする
}
