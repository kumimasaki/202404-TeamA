package ecSite.example.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TransactionItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long itemId;
	
	@NonNull
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    private CourseEntity course;
	
    @ManyToOne
    @JoinColumn(name = "transaction_id") 
    @ToString.Exclude
    private TransactionHistory transactionHistory;
}
