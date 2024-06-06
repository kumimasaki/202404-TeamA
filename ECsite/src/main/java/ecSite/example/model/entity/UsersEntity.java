package ecSite.example.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UsersEntity {
	//user_idの設定
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	
	//user_nameの設定
	private String userName;
	
	//user_emailの設定
	private String userEmail;
	
	//user_passwordの設定
	private String userPassword;
	
	//user_telephoneの設定
	private String userTelephone;

	// 空のコンストラクタ
	public UsersEntity() {
	}

	// コンストラクタ
	public UsersEntity(String userName, String userEmail, String userPassword, String userTelephone) {
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userTelephone = userTelephone;
	}

	// ゲッターとセッター
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserTelephone() {
		return userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}
	
	
	
}
