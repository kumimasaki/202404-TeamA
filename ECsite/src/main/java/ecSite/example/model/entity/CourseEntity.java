package ecSite.example.model.entity;

import java.sql.Date;
import java.sql.Time;

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
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long courseId;
	
	@NonNull
	private Date startDate;
	
	@NonNull
	private Time startTime;
	
	@NonNull
	private Time finishTime;
	
	@NonNull
	private String courseName;
	
	@NonNull
	private String courseDetail;
	
	@NonNull
	private String courseFee;
	
	@NonNull
	private String courseImage;
	
	@NonNull 
	private Time registerDate;
	
	@NonNull
	private Long adminId;
	
}
