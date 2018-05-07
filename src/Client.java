import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.*;

public class Client {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	final static int difficulty = 1;

	public static CryptoWallet walletA;
	public static CryptoWallet walletB;
	
	//list of all unspent transactions (UTXOs). 
	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>(); 
	
	public static void main(String[] args) {
		TestMethod();
		TestMethod2();
		TestMethod3();		
	}
	
	private static void TestMethod() {

		// first block so previous hash will be 0
		Block genesis = new Block("This is the first block", "0");
		System.out.println("genesis hash: " + genesis.hash);
		
		Block secondBlock = new Block("second block", genesis.hash);
		System.out.println("block 2 hash: " + secondBlock.hash);
		
		Block thirdBlock = new Block("third block", secondBlock.hash);
		System.out.println("block 3 hash: " + thirdBlock.hash);
	}

	private static void TestMethod2() {

		blockchain.add(new Block("This is the first block", "0"));
		blockchain.add(new Block("second block", blockchain.get(blockchain.size()-1).hash));
		blockchain.add(new Block("third block", blockchain.get(blockchain.size()-1).hash));
		
		String bcJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println(bcJson);

		if (ValidationHelpers.validateChain(blockchain)) {
			System.out.println("Chain is valid");
		}
		
		// mine each block 
		for (Block b : blockchain) {
			b.mineBlock(difficulty);
		}
	}

	private static void TestMethod3() {
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		
		walletA = new CryptoWallet();
		walletB = new CryptoWallet();

		System.out.println("Wallet A private and public keys:\n");
		System.out.println(HashHelper.getKeyString(walletA.privateKey));
		System.out.println(HashHelper.getKeyString(walletA.publicKey));

		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
	
		System.out.println("Is signature verified:\n");
		System.out.println(transaction.verifySignature());
	}
}