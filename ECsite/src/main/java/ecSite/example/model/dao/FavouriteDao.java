package ecSite.example.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecSite.example.model.entity.FavouriteEntity;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface FavouriteDao extends JpaRepository<FavouriteEntity , Long> {

	FavouriteEntity findByFavouriteId(Long favouriteId);
	
	//保存処理と更新処理insert and update
	FavouriteEntity save(FavouriteEntity favouriteEntity);
	
	//DELETE FROM course WHERE course_id = ?
	//用途：　削除処理に使用します
	void deleteByCourseId(Long courseId);

	FavouriteEntity findByCourseId(Long courseId);
	
}
