package ecSite.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecSite.example.model.dao.AdminDao;
import ecSite.example.model.entity.AdminEntity;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecSite.example.model.dao.AdminDao;
import ecSite.example.model.entity.AdminEntity;

@Service
public class AdminService {

	/*
	 * @Autowiredアノテーションを使って 
	 * AdminDao 変数を用意してAdminDao クラスのメソッド使えるようにする
	 */
	@Autowired
	private AdminDao adminDao;
	//保存処理（登録処理）
	public boolean createAdmin(String adminName, String adminEmail, String adminPassword) {
		/*
		 * もし、findByAdminEmail == null だったら、登録処理をします 
		 *  save メソッドを使用して登録処理をします //
		 * 保存できたらtrue
		 * そうでない場合は、保存処理結果false
		 */	
		if( adminDao.findByAdminEmail(adminEmail) == null ) {
			adminDao.save(new AdminEntity(adminName, adminEmail, adminPassword ));
			return true;
		}else {
			return false;
		}
	}
	//usercheck処理（処理）
		public boolean userCheck( String adminEmail) {
			/*
			 * もし、findByAdminEmail == null だったら、登録処理をします 
			 *  save メソッドを使用して登録処理をします //
			 * 保存できたらtrue
			 * そうでない場合は、保存処理結果false
			 */	
			if( adminDao.findByAdminEmail(adminEmail) == null) {
				return true;
			}else {
				return false;
			}
		}
		
		//ログイン処理
		//もし、emailとpassword がfindByUserEmailAndPasswordを使用して存在しない場合==nullの場合
		//その場合は、存在しないnullであることをコントローラークラスに知らせる
		//そうでない場合ログインしている人に情報をコントローラークラスに返す
		public AdminEntity loginCheck(String adminEmail, String adminPassword) {
			AdminEntity adminEntity = adminDao.findByAdminEmailAndAdminPassword(adminEmail, adminPassword);
			if(adminEntity == null) {
				return null;
			}else {
				return adminEntity;
			}
		}
}
