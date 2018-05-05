import com.google.gson.*;
public class Client {

	public static void main(String[] args) {
		
		// first block so previous hash will be 0
		Block genesis = new Block("This is the first block", "0");
		System.out.println("genesis hash: " + genesis.hash);
		
		Block secondBlock = new Block("second block", genesis.hash);
		System.out.println("block 2 hash: " + secondBlock.hash);
		
		Block thirdBlock = new Block("third block", secondBlock.hash);
		System.out.println("block 3 hash: " + thirdBlock.hash);

	}

}
