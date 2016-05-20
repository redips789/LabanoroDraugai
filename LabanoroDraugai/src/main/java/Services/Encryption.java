package Services;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Kristaliukas
 */

public class Encryption {
    
    private static final String KEY = "KLLPA413ppllng91"; // 128 bit key
    private static final String INIT = "RandomInitVector"; // 16 bytes IV
    
    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            //System.out.println("encrypted string:" + DatatypeConverter.printBase64Binary(encrypted));                     
            return DatatypeConverter.printBase64Binary(encrypted);
        } catch (Exception ex) {
            System.out.println("Nepavyko užšifruoti duomenų.");
        }

        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            
            byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted));
            
            return new String(original);
        } catch (Exception ex) {
            System.out.println("Nepavyko atšifruoti duomenų.");
        }

        return null;
    }
   
}