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
	
	public Block (String data, String previousHash) {
		
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateDigitalSignature();
	}
	
	private String calculateDigitalSignature() {
		
		// hash is the combination of all the block's properties
		return HashHelper.hashData(data + Long.toString(timeStamp) + previousHash);
	}
}
