import java.util.ArrayList;


public class ValidationHelpers {
	
	public static Boolean validateChain(ArrayList<Block> blockchain) {
		
		Block currentBlock;
		Block previousBlock;
		
		for(int i = 1; i < blockchain.size(); i++) {
		
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			
			// check if current block has been jippo-ed
			if (!currentBlock.hash.equals(currentBlock.calculateDigitalSignature())) {
				return false;
			}
			
			// compare previous block's hash
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				return false;
			}			
		}
		
		return true;
	}
}
