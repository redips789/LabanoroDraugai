
package Services;

/**
 *
 * @author Kristaliukas
 */

import java.math.BigInteger;
import java.security.SecureRandom;

public final class CodeGenerator {
    
    private static SecureRandom random = new SecureRandom();
    
    public static String ivitationCode() {
        return new BigInteger(50, random).toString(32);
    }
}
