package ecSite.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecSite.example.model.dao.UsersDao;
import ecSite.example.model.entity.UsersEntity;

@Service
public class UsersService {
	@Autowired
	private UsersDao usersDao;

	// 保存処理（登録処理）
	public boolean createUser(
			String userName,
			String userEmail,
			String userPassword,
			String userTelephone) {
		// もし、findByUserEmail==nullだったら登録処理処理します
		// saveメソッドを使用して登録処理をする
		// 保存ができたらtrue
		// そうでない場合、保存処理失敗 false
		if(usersDao.findByUserEmail(userEmail)==null) {
			usersDao.save(new UsersEntity(
					userName,
					userEmail,
					userPassword,
					userTelephone));
			return true;
		} else {
			return false;
		}
	}
	
	// ログイン処理
	public UsersEntity loginCheck(String userEmail, String userPassword) {
		UsersEntity usersEntity = usersDao.findByUserEmailAndUserPassword(userEmail, userPassword);
		//もし、emailとpasswordがfindByUserEmailAndUserPasswordを使用して存在しなかった場合==nullの場合、
		//その場合、存在しないnullであることをコントローラークラスに知らせる
		//そうでない場合、ログインしている人の情報をコントローラークラスに渡す
		if (usersEntity == null) {
			return null;
		} else {
			return usersEntity;
		}
	}
}
