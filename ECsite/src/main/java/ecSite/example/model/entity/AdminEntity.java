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
@Table (name = "admin")
public class AdminEntity {
	
	//admin_idの設定
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long adminId;
	
	//admin_name の設定
	@NonNull
	private String adminName;
	
	//admin_email の設定
	@NonNull
	private String adminEmail;
	
	//admin_passwordの設定
	@NonNull
	private String adminPassword;
	
}
