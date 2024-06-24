package ecSite.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecSite.example.model.entity.TransactionHistory;

public interface TransactionHistoryDao extends JpaRepository<TransactionHistory, Long> {
	// 保存、更新処理
	TransactionHistory save(TransactionHistory transactionHistory);
	
	// userIdで履歴の一覧を取得
	List<TransactionHistory> findByUserId(Long userId);
	
	// transactionIdで履歴の一覧を取得
	TransactionHistory findByTransactionId(Long transactionId);
}
