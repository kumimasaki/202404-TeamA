package ecSite.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecSite.example.model.entity.TransactionHistory;
import ecSite.example.model.entity.TransactionItem;

public interface TransactionItemDao extends JpaRepository<TransactionItem, Long> {
	
	// transactionIdで商品を取得
	List<TransactionItem> findByTransactionHistory(TransactionHistory transactionHistory);
}
