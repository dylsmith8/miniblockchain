import java.security.PublicKey;

public class TransactionOutput {
	public String id;
	
	// the new owner of value sent
	public PublicKey receiver;
	
	// value owned
	public double value;
	
	// ID of transaction this output was created for
	public String parentTransactionId;
	
	public TransactionOutput(PublicKey receiver, double value, String parentTransactionId) {
		this.receiver = receiver;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		this.id = HashHelper.hashData(HashHelper.getKeyString(receiver) + Double.toString(value) + parentTransactionId);
	}
	
	public Boolean isThisMine(PublicKey publicKey) {
		return receiver == publicKey;
	}
}
