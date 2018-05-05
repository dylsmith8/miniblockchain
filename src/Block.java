import java.util.*;

public class Block {
	
	// will contain the data. Possibly convert to some other class?
	private String data; 
	
	// time stamp block created
	private long timeStamp;
	
	// current block's hash (digital sig)
	public String hash;
	
	// previous block's hash
	public String previousHash;
	
	private int nonce;
	
	public Block (String data, String previousHash) {
		
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateDigitalSignature();
	}
	
	// public because used in validation
	public String calculateDigitalSignature() {
		
		// hash is the combination of all the block's properties
		return HashHelper.hashData(data + Long.toString(timeStamp) + previousHash + Integer.toString(nonce));
	}
	
	// difficulty = number of 0s to solve for
	public void mineBlock(int difficulty) {
		
		String target = new String(new char[difficulty]).replace('\0', '0');
		
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateDigitalSignature();
		}
		
		System.out.println("Successfully mined a block: " + hash);
	}
}
