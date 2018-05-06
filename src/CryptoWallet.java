import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class CryptoWallet {
	
	public PrivateKey privateKey;
	
	// will essentially act as our address
	public PublicKey publicKey;	
	
	public CryptoWallet() {
		generateKeyPair();
	}

	private void generateKeyPair() {
		
		try {
			// Elliptic curve algorithm 
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			
			// use sha1 to generate random numbers (pseudo RNG)
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			
			// generate the EC
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			
			keyGen.initialize(ecSpec, random);
        	KeyPair keyPair = keyGen.generateKeyPair();
        	
        	privateKey = keyPair.getPrivate();
        	publicKey = keyPair.getPublic();
			
		}
		catch (NoSuchProviderException ex) {
			handleError(ex);
		}
		catch (NoSuchAlgorithmException ex) {
			handleError(ex);
		}
		catch (InvalidAlgorithmParameterException ex) {
			handleError(ex);
		}
	}
	
	private static void handleError(Exception exception) {
		System.out.println("An error occurred generating wallet keypair:\n");
		exception.printStackTrace();
	}
}
