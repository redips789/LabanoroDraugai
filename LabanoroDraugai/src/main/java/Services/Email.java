
package Services;

/**
 *
 * @author Kristaliukas
 */
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email {
    public static void sendEmail(String to, String subject, String msg){
        
        String from = "labanorodraugai.lt@gmail.com";
        final String user = "labanorodraugai.lt@gmail.com";
        final String pass = "komandax2016";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(msg);
            Transport.send(message);
            System.out.println("Elektroninis paštas išsiųstas!");
        } catch (MessagingException e) {
            System.out.println("Klaidos email klasėje.");
            throw new RuntimeException(e);
        }
    }
    
    public static String createBody(String msg) {
        String greeting = "Sveiki,\n\n";
        String goodbye = "\n\nPagarbiai\nKomanda X";
        return greeting+msg+goodbye;
    }
    
    public static void emailReceivedRecommendation(String fullname, String email){
        String subject = "Rekomendacijos prašymas";
        String msg = "Informuojame, jog "+fullname+" išsiuntė Jums rekomedacijos prašymą.\n\n";
        msg = msg+"Norėdami patvirtinti šią rekomendaciją, prašome prisijungti prie sistemos. Prašymą galite rasti skiltyje \"Rekomendacijos\".\n\n";
        String body = createBody(msg);
        sendEmail(email, subject, body);       
    }
    
    public static void emailConfirmedRecommendation(String fullname, String email){
        String subject = "Rekomendacijos patvirtinimas";
        String msg = "Informuojame, jog "+fullname+" patvirtino Jūsų rekomedacijos prašymą.\n\n";
        String body = createBody(msg);
        sendEmail(email, subject, body);
    }
    
    public static void emailDeclinedRecommendation(String fullname, String email){
        String subject = "Rekomendacijos atmetimas";
        String msg = "Apgailestaujame, tačiau "+fullname+" atmetė Jūsų rekomedacijos prašymą.\n\n";
        String body = createBody(msg);
        sendEmail(email, subject, body);
    }
    
    public static void emailInviteFriend(String fullname, String email, String code){
        String subject = "Prisijunk prie mūsų!";
        String msg = fullname+" Jums siunčia pakvietimą prisijungti prie klubo \"Labanoro draugai\".\n\n";
        msg = msg+"Registracijos metu suvedus nurodytą kodą Jums bus automatiškai patvirtinta viena rekomendacija, priartinanti prie tapimo vienu iš mūsų.\n\n";
        msg = msg+"Kodas: "+code;
        String body = createBody(msg);
        sendEmail(email, subject, body);
    }
    
    public static void emailDeletedCandidate(String email){
        String subject = "Lūdna žinutė";
        String msg = "Informuojame, jog nors Jūsų rekomendacijos buvo patvirtintos, tačiau dėl pasiekto maksimalaus klubo naudotojų skaičiaus, Jūsų negalime priimti į klubą.\n\n";
        msg = msg+"Labai apgailestaujame.\n";
        String body = createBody(msg);
        sendEmail(email, subject, body);       
    }
}
