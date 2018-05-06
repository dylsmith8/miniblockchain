import java.security.*;
import java.util.ArrayList;

public class Transaction {
	// the hash of the transaction
	public String transactionId;
	
	// public key of person sending data
	public PublicKey sender;
	
	// public key of person receiving data
	public PublicKey receiver;
	
	// data being sent - a numeric value in this case but could be anything
	public double value;
	
	// signature of transaction
	public byte[] sig;
	
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	// used to add extra randomness
	@SuppressWarnings("unused")
	private static int transactionCount = 0;
	
	public Transaction(PublicKey sender, PublicKey receiver, double value, ArrayList<TransactionInput> inputs) {
		this.sender = sender;
		this.receiver = receiver;
		this.value = value;
		this.inputs = inputs;
		
		this.transactionId = calculateTransactionId();
	}
	
	private String calculateTransactionId() {
		transactionCount++;
		return HashHelper.hashData(HashHelper.getKeyString(sender) + HashHelper.getKeyString(receiver) + Double.toString(value) + transactionId);
	}

	public void generateSignature(PrivateKey privateKey) {
		String data = HashHelper.getKeyString(sender) + HashHelper.getKeyString(receiver) + Double.toString(value);
		sig = HashHelper.signDataWithPrivateKey(privateKey, data);
	}

	public Boolean verifySignature() {
		String data = HashHelper.getKeyString(sender) + HashHelper.getKeyString(receiver) + Double.toString(value);
		return HashHelper.verifySignature(sender, data, sig);
	}
}
