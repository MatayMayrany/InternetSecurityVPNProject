import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class SessionEncrypter {
    SessionKey sessionKey;
    byte[] iv = new byte[16];
    public SessionEncrypter(Integer keylength) throws InvalidKeyException {
        sessionKey = new SessionKey(keylength);
        iv = initializeVector();
    }

    public byte[] initializeVector(){
        Random random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

     public CipherOutputStream openCipherOutputStream(FileOutputStream fileOutputStream){
        // write the encrypted data to fileOutputStream\

         try {
             Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
             IvParameterSpec ivSpec = new IvParameterSpec(iv);
             cipher.init(Cipher.ENCRYPT_MODE, sessionKey.secretKey, ivSpec);
             CipherOutputStream cryptOut = new CipherOutputStream(fileOutputStream, cipher);
             return cryptOut;
         } catch (NoSuchPaddingException e) {
             e.printStackTrace();
         } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
         } catch (InvalidKeyException e) {
             e.printStackTrace();
         } catch (InvalidAlgorithmParameterException e) {
             e.printStackTrace();
         }
         return null;
    }

    public String encodeKey(){
        return sessionKey.encodeKey();
    }

    public String encodeIV(){
        return Base64.getEncoder().encodeToString(iv);
    }

}
