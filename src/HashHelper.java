import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;

public class HashHelper {
	
	public static String hashData(String data) {
		
		StringBuffer hex = new StringBuffer();
		
		try {
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte [] hash = md.digest(data.getBytes("UTF-8"));	
			
			// convert the resulting bytes into a hexadecimal value
			for (byte b : hash) {
				String hexValue = Integer.toHexString(0xff & b); // get last 8 bits
				
				if (hexValue.length() == 1) {
					hex.append('0');
				}
				
				hex.append(hexValue);
			}	
			
		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			handleError(ex);
		}
		
		return hex.toString();
	}
	
	public static String getPublicKeyString(PublicKey publicKey) {
		
		return Base64.getEncoder().encodeToString(publicKey.getEncoded());
	}
	
	private static void handleError(Exception exception) {
		
		System.out.println("An error occurred hashing/verification of the blockchain data:\n");
		exception.printStackTrace();
	}

	public static byte[] signDataWithPrivateKey (PrivateKey privateKey, String input) {
		
		byte [] signature = new byte[0];
		
		try {
			
			Signature dsa;
			
			// give it the private key
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			
			byte[] stringBytes = input.getBytes();
			dsa.update(stringBytes);
			
			signature = dsa.sign();			
		}
		catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
			handleError(ex);
		}
		
		return signature;
	}	

	public static Boolean verifySignature(PublicKey publicKey, String data, byte[] signature) {
		
		Boolean isVerified = false;
		
		try {
			
			Signature verificationSig;
			verificationSig = Signature.getInstance("ECDSA", "BC");
			verificationSig.initVerify(publicKey);
			verificationSig.update(data.getBytes());
			isVerified = verificationSig.verify(signature);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException ex) {
			handleError(ex);
		}
		
		return isVerified;
	}
}
