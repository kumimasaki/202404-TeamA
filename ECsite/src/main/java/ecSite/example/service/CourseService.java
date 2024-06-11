package ecSite.example.service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
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
	
	//講座一覧のチェック
	//もし、adminId== null　戻り値としてnull
	//そうでない場合は、findAll 内容をコントローラークラスに渡す
	public List<CourseEntity>selectAllCourseList(Long adminId){
		if (adminId == null) {
			return null;
		}else {
			return courseDao.findAll();
		}
	}
	
	//講座の登録処理チェック
	//もし、findByCourseNameが==nullだったら、
	//保存処理
	//そうでない場合は
	//false
	public boolean createCourse(Date startDate,LocalTime startTime,
			LocalTime finishTime, String courseName, String courseDetail,
			String courseFee, String courseImage, 
			Long adminId) {
		if(courseDao.findByCourseName(courseName) == null) {
			courseDao.save(new CourseEntity(startDate, startTime, finishTime,
					courseName, courseDetail, courseFee, courseImage, 
					adminId));
			return true;
		}else {
			return false;
		}
	}
	
}
