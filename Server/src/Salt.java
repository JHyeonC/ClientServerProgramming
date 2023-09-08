import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Salt {
//	public String makeSalt(String passWord) throws NoSuchAlgorithmException {
//		
//		String hex = "";
//		// "SHA1PRNG"�� �˰��� �̸�
//		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//		byte[] bytes = new byte[16];
//		random.nextBytes(bytes);
//		
//		// SALT ����
//		String salt = new String(Base64.getEncoder().encode(bytes));
//		String rawAndSalt = passWord + salt;
//		
//		System.out.println("raw : " + passWord);
//		System.out.println("salt : " + salt);
//		
//		MessageDigest md = MessageDigest.getInstance("SHA-256");
////		// �� ��ȣȭ
////		md.update(passWord.getBytes());
////		hex = String.format("%064x", new BigInteger(1, md.digest()));
////		System.out.println("raw�� �ؽð� : "+hex);
//		
//		// ��+salt ��ȣȭ
//		md.update(rawAndSalt.getBytes());
//		hex = String.format("%064x", new BigInteger(1, md.digest()));
////		System.out.println("raw+salt�� �ؽð� : "+hex);
//		return hex;
//	}
	
	public String checkPassword(String passWord, String salt) throws NoSuchAlgorithmException {
		String passWordAndSalt = passWord + salt;
		String returnEncryption = "";
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		md.update(passWordAndSalt.getBytes()); // ��ȣȭ
		returnEncryption = String.format("%064x", new BigInteger(1, md.digest()));
		return returnEncryption;
	}
	
	public String makeToken(String checkResult) throws NoSuchAlgorithmException{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		String salt = new String(Base64.getEncoder().encode(bytes));
		String tokenValue = checkResult + salt;
		return tokenValue;
	}
}