package ecSite.example.model.entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table (name = "course")
public class CourseEntity {
	
	//course_id の設定
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long courseId;
	
	//start_dateの設定
	@NonNull
	private Date startDate;
	
	//start_timeの設定
	@NonNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	//finish_time の設定
	@NonNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime finishTime;
	
	//course_name の設定
	@NonNull
	private String courseName;
	
	//course_detailの設定
	@NonNull
	private String courseDetail;
	
	//course_feeの設定
	@NonNull
	private String courseFee;
	
	//course_imageの設定
	@NonNull
	private String courseImage;
	
	//admin_id の設定
	@NonNull
	private Long adminId;
	
	
	
}
