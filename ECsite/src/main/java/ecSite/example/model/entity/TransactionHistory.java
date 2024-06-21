package ecSite.example.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TransactionHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transactionId;
	
	@NonNull
	private Long userId;
	
	@NonNull
	private int amount;
	
	@NonNull
	private LocalDateTime transactionDate;
	
	// mappedBy : tableの関係を明確に示します,今回はtransactionHistoryはownerテーブルです
	// cascade = CascadeType.ALL : 二つのtableの操作(updating,deleting)は一致する
	// fetch = FetchType.LAZY : テーブルをアクセスとき、関連するentityだけ一緒にloadします
    @OneToMany(mappedBy = "transactionHistory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<TransactionItem> items = new ArrayList<>();
}
