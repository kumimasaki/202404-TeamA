package ecSite.example.service;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ecSite.example.model.dao.CourseDao;
import ecSite.example.model.entity.CourseEntity;

@Service
public class CourseService {
	@Autowired
	private CourseDao courseDao;

	// 講座一覧のチェック
	// もし、adminId== null 戻り値としてnull
	// そうでない場合は、findAll 内容をコントローラークラスに渡す
	public List<CourseEntity> selectAllCourseList(Long adminId) {
		if (adminId == null) {
			return null;
		} else {
			return courseDao.findAll();
		}
	}

	// 講座の登録処理チェック
	public boolean createCourse(java.sql.Date startDate, LocalTime startTime, LocalTime finishTime, String courseName,
			String courseDetail, String courseFee, String courseImage, Long adminId) {
		/*
		 * もし、findByCourseNameが==nullだったら、 保存処理 
		 * そうでない場合は false
		 */
		if (courseDao.findByCourseName(courseName) == null) {
			courseDao.save(new CourseEntity(startDate, startTime, finishTime, courseName, courseDetail, courseFee,
					courseImage, adminId));
			return true;
		} else {
			return false;
		}
	}

	  //編集画面を表示する時のチェック
	public CourseEntity courseEditCheck(Long courseId) {
		/*
		 * もし、courseId == nullだったら、nullを返す
		 * そうでない場合は、
		 * findByCourseId の情報をコントローラークラスに渡す
		*/
		if(courseId == null) {
			return null;
		}else {
			return courseDao.findByCourseId(courseId);
		}
		
	}
	
	//更新処理のチェック
	public boolean courseUpdate(Long courseId,
			Date startDate, 
			LocalTime startTime,
			LocalTime finishTime, 
			String courseName,
			String courseDetail, 
			String courseFee,
			String courseImage, 
			Long adminId
			) {
		//もし、courseId==nullだったら、更新処理はしない
		//false
		//そうでない場合は、
		//更新処理をする
		//コントローラークラスからもらった、courseIdをつかって、編集する前の、データを取得
		//変更するべきところだけ、セッターを使用してデータの変更をする
		//true を返す
		if(courseId == null) {
			return false;
		}else {
			CourseEntity course = courseDao.findByCourseId(courseId);
			course.setStartDate(startDate);
			course.setStartTime(startTime);
			course.setFinishTime(finishTime);
			course.setCourseName(courseName);
			course.setCourseDetail(courseDetail);
			course.setCourseFee(courseFee);
			course.setCourseImage(courseImage);
			course.setAdminId(adminId);
			courseDao.save(course);
			return true;
		}
	}
	//削除処理のチェック
	public boolean deleteCourse(Long courseId) {
		//もし、コントローラーからもらったcourseIdが nullであれば
		//削除できないことfalse
		//そうでない場合は、
		//deleteBycourseId を使用して商品の削除
		//true
		if(courseId == null) {
			return false;
		}else {
			courseDao.deleteByCourseId(courseId);
			return true;
		}
	}
	
}
