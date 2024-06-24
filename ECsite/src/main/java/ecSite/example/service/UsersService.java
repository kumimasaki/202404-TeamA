package ecSite.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecSite.example.model.dao.UsersDao;
import ecSite.example.model.entity.UsersEntity;

@Service
public class UsersService {
	@Autowired
	private UsersDao usersDao;
	
	private UsersEntity notConfirmedUser;

	// ユーザー登録処理
	public boolean createNotConfirmedUser(
			String userName,
			String userEmail,
			String userPassword
				) {
		// もし、findByUserEmail==nullだったら
		// NotConfirmedUserに情報を渡して、trueを渡す
		// そうでない場合、ユーザー登録処理失敗 false
		if(usersDao.findByUserEmail(userEmail)==null) {
			notConfirmedUser = new UsersEntity(
					userName,
					userEmail,
					userPassword
					);
			return true;
		} else {
			return false;
		}
	}
	
	// 登録欲しいユーザー情報を取得
	public UsersEntity getNotConfirmedUser() {
		return notConfirmedUser;
	}
	
	// 保存処理と更新処理
	public void createConfirmedUser() {
		if (notConfirmedUser != null) {
			usersDao.save(notConfirmedUser);
			notConfirmedUser = null;
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
	
	// パスワードリセット確認処理
	public boolean resetCheck(String userName, String userEmail) {
		UsersEntity usersEntity = usersDao.findByUserNameAndUserEmail(userName, userEmail);
		//もし、userNameとuserEmailがfindByUserNameAndUserEmailを使用して存在しなかった場合==nullの場合、
		//その場合、存在しないnullであることをコントローラークラスに知らせる
		//そうでない場合、NotConfirmedUserにユーザーの情報を渡して、trueをコントローラークラスに渡す
		if (usersEntity == null) {
			return false;
		} else {
			notConfirmedUser = usersEntity;
			return true;
		}
	}
	
	// パスワードリセット処理
    public void resetUserPassword(String newUserPassword) {
    	notConfirmedUser.setUserPassword(newUserPassword);
    	usersDao.save(notConfirmedUser);
		notConfirmedUser = null;
    }
    
	// ユーザー情報更新処理
	public void updateUserInformation(Long userId, String userName, String userEmail) {
		// userが存在する場合だけ、情報が更新できます
		UsersEntity usersEntity = usersDao.findByUserId(userId);
		if (usersEntity != null) {
			usersEntity.setUserName(userName);
			usersEntity.setUserEmail(userEmail);
			usersDao.save(usersEntity);

		}
	}
}