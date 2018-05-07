
public class TransactionInput {
	public String transactionOutputId; 
	
	// unspent transaction output
	public TransactionOutput UTXO;
	
	public TransactionInput(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}
}
