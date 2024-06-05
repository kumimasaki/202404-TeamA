package ecSite.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecSite.example.model.entity.UsersEntity;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UsersDao extends JpaRepository<UsersEntity, Long> {
	// 保存処理と更新処理
	UsersEntity save(UsersEntity usersEntity);
	
	// SELECT * FROM users WHERE user_email = ?
	// 用途：ユーザーの登録処理をするときに、同じメールアドレスがあったらば登録させないようにする
	// 一行だけしかコードは取得できない
	UsersEntity findByUserEmail(String userEmail);
	
	// SELECT * FROM users WHERE user_email AND user_password = ?
	// 用途：ログイン処理をするときに、入力したメールアドレスとパスワードが一致してるときだけデータを取得する
	UsersEntity findByUserEmailAndUserPassword(String userEmail, String userPassword);
	
	// user情報一覧を取得
	List<UsersEntity> findAll();
}
