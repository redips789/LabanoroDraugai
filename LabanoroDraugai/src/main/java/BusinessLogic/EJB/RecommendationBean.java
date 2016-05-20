
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.EJB.InvitationCRUD;
import DataAccess.EJB.RecommendationDao;
import DataAccess.EJB.SettingsDao;
import DataAccess.JPA.Account;
import DataAccess.JPA.Invitation;
import DataAccess.JPA.Recommendation;
import DataAccess.JPA.RecommendationPK;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import Messages.MessageUtil;
import Services.CodeGenerator;
import Services.Email;
import Services.Encryption;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Kristaliukas
 */

@RequestScoped
@Named
public class RecommendationBean implements Serializable { 
    
    @Inject
    RecommendationDao recommendationEjb;
    
    @Inject
    SettingsDao settingsEjb;
    
    @Inject
    AccountDao accountEjb;
    
    @Inject
    InvitationCRUD invitationEjb;
    
    @Inject
    LoginBean loginBean;
    
    @Inject
    AccountBean accountBean;
    
    public LoginBean getLoginBean() {
        return loginBean;
    }
    
    public AccountBean getAccountBean() {
        return accountBean;
    }
    
    private Recommendation rec = new Recommendation();
    private RecommendationPK PK = new RecommendationPK();
    private Invitation invitation = new Invitation();
    private String fullname = null;
    private List<Recommendation> receiverList;
    private List<Recommendation> giverList;  //sąrašas rekomendacijų, kurias tavęs prašo patvirtinti ir tu dar jų nepatvirtinai
    private List<Recommendation> selectedCandidates;
    private int validity_day;
    private int min_rec;
    private int max_rec;
    
    private String email;
    
    @PostConstruct
    public void init() {
        validity_day = settingsEjb.findSettings().getRecommendationsValidity();
        min_rec = settingsEjb.findSettings().getMinRecommendations();
        max_rec = settingsEjb.findSettings().getMaxRecommendations();
    }
    
    public List<Recommendation> getReceiverList() {
        this.setReceiverList(recommendationEjb.findRecByReceiver(loginBean.getId()));
        return receiverList;
    }

    public void setReceiverList(List<Recommendation> receiverList) {
        this.receiverList = receiverList;
    }

    public List<Recommendation> getGiverList() {
        this.setGiverList(recommendationEjb.findForConfirm(loginBean.getId(), Boolean.FALSE)); //kolkas hardkodinam
        return giverList;
    }

    public void setGiverList(List<Recommendation> giverList) {
        this.giverList = giverList;
    }

    public List<Recommendation> getSelectedCandidates() {
        return selectedCandidates;
    }

    public void setSelectedCandidates(List<Recommendation> selectedCandidates) {
        this.selectedCandidates = selectedCandidates;
    }
	  
    public String getFullname() {
	return fullname;
    }    
	
    public void setFullname(String fullname) {
	this.fullname = fullname;
    }

    public Recommendation getRec() {
        return rec;
    }

    public void setRec(Recommendation rec) {
        this.rec = rec;
    }

    public RecommendationPK getPK() {
        return PK;
    }

    public void setPK(RecommendationPK PK) {
        this.PK = PK;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String addRecommendation() {
        try {
            String[] arr = this.getFullname().split(" ");
            if (this.receiverList.size()>=this.max_rec){
                MessageUtil.addErrorMessage("Maksimalus išsiųstų rekomendacijų prašymų limitas pasiektas! Prašome laukti, kol rekomendacijos bus patvirtinots arba baigsis jų galiojimo laikas.");
                return "";
            }
            else {
                try {
                    Account acc = accountEjb.findAccountById(loginBean.getId());
                    int id = Integer.parseInt(arr[0].substring(0, 1));
                    this.PK.setReceiverAccountid(loginBean.getId());
                    this.PK.setGiverAccountid(this.accountBean.getMemberList().get(id-1).getId());
                    this.rec.setRecommendationPK(this.PK);
                    this.rec.setIsGiven(Boolean.FALSE);
                    this.rec.setSendDate(Calendar.getInstance().getTime());
                    recommendationEjb.addRecommendation(this.rec);
                    MessageUtil.addSuccessMessage("Rekomendacija sėkmingai išsiųsta!");
                    Email.emailReceivedRecommendation(acc.getFirstName()+" "+acc.getLastName(), this.accountBean.getMemberList().get(id-1).getEmail());
                    return "";
                } catch (Exception e) {
                    MessageUtil.addErrorMessage("Šiam žmogui rekomendacija jau buvo išsiųsta anksčiau. Pasirinkite kitą klubo narį!");
                    return "";
                }	
            }
        } catch (NullPointerException e) {
            MessageUtil.addErrorMessage("Norėdami išsiųsti rekomendacijos prašymą, privalote pasirinkti kurį nors klubo narį!");
            return "";
        }			
    }
    
    public String confirmRecommendation() {
        try {
            Account acc = accountEjb.findAccountById(loginBean.getId());
            for (int i=0; i<this.selectedCandidates.size(); i++) {
                recommendationEjb.updateRecommendation(this.selectedCandidates.get(i));
                Account candidate = accountEjb.findAccountById(this.selectedCandidates.get(i).getRecommendationPK().getReceiverAccountid());
                Email.emailConfirmedRecommendation(acc.getFirstName()+" "+acc.getLastName(), candidate.getEmail());
                checkBecomingMember(candidate.getId());
            }
            if (this.selectedCandidates.size() == 1) MessageUtil.addSuccessMessage("Rekomendacija sėkmingai patvirtinta!");
            else if (this.selectedCandidates.size() > 1) MessageUtil.addSuccessMessage("Rekomendacijos sėkmingai patvirtintos!");
            else MessageUtil.addWarningMessage("Norėdami patvirtinti, privalote pasrinkti bent vieną rekomendacijos prašymą!");
            return "";
        } catch (Exception e) {
            MessageUtil.addErrorMessage("Įvyko nenumatyta klaida!");
            return "";
        }    
    }
    
    public String rejectRecommendation(){
        try {
            Account acc = accountEjb.findAccountById(loginBean.getId());
            for (int i=0; i<this.selectedCandidates.size(); i++) {
                Account candidate = accountEjb.findAccountById(this.selectedCandidates.get(i).getRecommendationPK().getReceiverAccountid());
                Email.emailConfirmedRecommendation(acc.getFirstName()+" "+acc.getLastName(), candidate.getEmail());
                recommendationEjb.deleteRecommendation(this.selectedCandidates.get(i));
            }
            if (this.selectedCandidates.size() == 1) MessageUtil.addSuccessMessage("Rekomendacija sėkmingai atmesta!");
            else if (this.selectedCandidates.size() > 1) MessageUtil.addSuccessMessage("Rekomendacijos sėkmingai atmestos!");
            else MessageUtil.addWarningMessage("Norėdami atmesti, privalote pasrinkti bent vieną rekomendacijos prašymą!");
            return "";
        } catch (Exception e) {
            MessageUtil.addErrorMessage("Įvyko nenumatyta klaida!");
            return "";
        }  
    }
    
    public String checkBecomingMember(int id) {
        try {
            List<Recommendation> recList = recommendationEjb.findRecByReceiver(id);
            int confirmAmount = 0;
            for (int i=0; i<recList.size(); i++) {
                if (Objects.equals(recList.get(i).getIsGiven(), Boolean.TRUE)) confirmAmount++;
            }
            if (confirmAmount >= this.min_rec) {
                accountEjb.updateAccountStatus(id);
                recommendationEjb.deleteRecommendations(id);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
    
    public String showDate(Date dat){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        String strDate = sdfDate.format(dat);
        return strDate;
    }
    
    public String showEndDate(Date dat){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Calendar c = Calendar.getInstance();
        c.setTime(dat);
        
        c.add(Calendar.DATE, validity_day); 
        String strDate = sdfDate.format(c.getTime());
        return strDate;
    }
    
    public String showStatus(Boolean status) {
        if (Objects.equals(status, Boolean.TRUE)) return "Taip";
        else return "Ne";
    }
    
    public String showRecMessage() {
        String message;    
        message = "Norėdami tapti klubo nariu privalote išsiųsti mažiausiai " + min_rec;
        // ifai su prielaida, kad min rekomendaciju skaicius bus iki 20
        if (min_rec == 1) message = message.concat(" rekomendacijos prašymą. ");
        else if (min_rec >=2 && min_rec <=9) message = message.concat(" rekomendacijų prašymus. ");
        else message = message.concat(" rekomendacijų prašymų. ");
        message = message.concat("Didžiausias rekomendacijų prašymų skaičius - "+max_rec+". ");
        message = message.concat("Klubo nariu tapsite tik tada, kai minimalus skaičius Jūsų     išsiųstų prašymų bus patvirtinti.");
        return message;
    }
    
    public boolean isNotEmptyGiverList() {
         if (this.giverList.isEmpty()) return Boolean.FALSE;
         else return Boolean.TRUE;
    }
    
    public void inviteFriend() {
        Account acc = accountEjb.findAccountById(loginBean.getId());
        
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Boolean b = email.matches(EMAIL_REGEX);
        if (Objects.equals(b, Boolean.TRUE)) {
            Account temp = accountEjb.findAccountByEmail(email);
           // if (temp == null) { 
                try{
                    String code = CodeGenerator.ivitationCode();                    
                    String encryptedCode = Encryption.encrypt(code);
                    this.invitation.setCode(encryptedCode);
                    this.invitation.setInviterAccountid(acc);
                    invitationEjb.addInvitation(this.invitation);                    
              
                    Email.emailInviteFriend(acc.getFirstName()+" "+acc.getLastName(), email, code);
                    MessageUtil.addSuccessMessage("Pakvietimas sėkmingai išsiųstas!");
                    
                } catch (Exception e) {
                    MessageUtil.addErrorMessage("Įvyko kažkas keisto!");
                }
                this.email = "";
           /* }
            else {
                MessageUtil.addErrorMessage("Šio žmogaus nereikia kviesti, jis jau naudojasi mūsų sistema!");
            }*/
        }
        else {
            MessageUtil.addErrorMessage("Įveskite el. pašto adresą!");
        }
    }
}
