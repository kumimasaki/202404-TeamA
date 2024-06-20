package ecSite.example.model.entity;

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
@Table (name = "favourite")
public class FavouriteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long favouriteId;
	
	@NonNull
	private Long courseId;
	
	@NonNull
	private Long userId;

}
