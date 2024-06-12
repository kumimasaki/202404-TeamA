package ecSite.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecSite.example.model.entity.AdminEntity;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface AdminDao extends JpaRepository<AdminEntity, Long> {

	//adminService から渡されるadmin情報(メールアドレス)を条件にdb検索
	AdminEntity findByAdminEmail(String adminEmail);
	
	//adminService から渡されるadmin情報を基にdbへ保存する
	//dbになかったらinsert
	//DBにあったらupdate
	AdminEntity save(AdminEntity adminEntity);

	//admin情報一覧を取得S
	List<AdminEntity> findAll();
	
	//adminService から渡されるadmin情報(メールアドレスとパスワード)を条件にdb検索
	AdminEntity findByAdminEmailAndAdminPassword(String adminEmail, String adminPassword);

}
