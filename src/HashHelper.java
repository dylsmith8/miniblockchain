import java.io.UnsupportedEncodingException;
import java.security.*;

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
		catch (NoSuchAlgorithmException ex) {
			handleError(ex);
		}
		catch (UnsupportedEncodingException ex) {
			handleError(ex);			
		}
		
		return hex.toString();
	}
	
	private static void handleError(Exception exception) {
		System.out.println("An error occurred hashing the blockchain data:\n");
		exception.printStackTrace();
	}
}
