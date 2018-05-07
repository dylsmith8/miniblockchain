import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	// UTXO passed in from Client and then returned after transaction has completed, otherwise exception thrown if invalid
	public HashMap<String, TransactionOutput> processTransaction(HashMap<String, TransactionOutput> UTXOs, double minimumTransaction) throws IllegalStateException  {
		
		if (verifySignature() == false) {
			
			throw new IllegalStateException("Transaction Signature failed to verify");
		}
		
		// ensure transaction is actually valid
		if (getInputsValue() < getInputsValue()) {
			
			throw new IllegalStateException("Transaction Inputs to small: " + getInputsValue());
		}
		
		HashMap<String, TransactionOutput> newUTXO = generateTransactionOutputs(UTXOs);
		return newUTXO;	
	}

	private HashMap<String, TransactionOutput> generateTransactionOutputs(HashMap<String, TransactionOutput> UTXOs) {

		// ensure UTXOs are in fact unspent
		for (TransactionInput input : inputs) {
			
			input.UTXO = UTXOs.get(input.transactionOutputId);
		}
		
		double leftOver = getInputsValue() - value;
		
		// may not be necessary since already done in constructor
		transactionId = calculateTransactionId(); 
		
		// this will send the value to the recipient
		outputs.add(new TransactionOutput(this.receiver, value, transactionId));
		
		// what the sender has left
		outputs.add(new TransactionOutput(this.sender, leftOver, transactionId));
		
		// what is left unspent
		for (TransactionOutput output : outputs) {
			UTXOs.put(output.id, output);
		}
		
		// remove what has been spent
		for (TransactionInput input : inputs) {
			
			if (input.UTXO == null) {
				continue;
			}
			
			UTXOs.remove(input.UTXO.id);
		}
		
		return UTXOs;
	}

	public double getInputsValue() {
		
		float total = 0;
		
		for(TransactionInput i : inputs) {
			
			if(i.UTXO == null) {
				
				continue; 
			}
			
			total += i.UTXO.value;
		}
		
		return total;
	}
	
	public float getOutputsValue() {
		
		float total = 0;
		
		for(TransactionOutput output : outputs) {
			
			total += output.value;
		}
		
		return total;
	}	
}
