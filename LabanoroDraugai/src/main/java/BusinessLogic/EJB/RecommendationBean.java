
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.EJB.RecommendationDao;
import DataAccess.EJB.SettingsDao;
import DataAccess.JPA.Recomendation;
import DataAccess.JPA.RecomendationPK;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import Messages.MessageUtil;

/**
 *
 * @author Kristaliukas
 */

@ManagedBean
@RequestScoped
public class RecommendationBean implements Serializable { 
    
    @EJB
    RecommendationDao recommendationEjb;
    @EJB
    SettingsDao settingsEjb;
    @EJB
    AccountDao accountEjb;
    
    @ManagedProperty(value="#{loginBean}")
    LoginBean loginBean;
    
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    
    @ManagedProperty(value="#{accountBean}")
    AccountBean accountBean;
    
    public AccountBean getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(AccountBean accountBean) {
        this.accountBean = accountBean;
    }
    
    private Recomendation rec = new Recomendation();
    private RecomendationPK PK = new RecomendationPK();
    private String fullname = null;
    private List<Recomendation> receiverList;
    private List<Recomendation> giverList;  //sąrašas rekomendacijų, kurias tavęs prašo patvirtinti ir tu dar jų nepatvirtinai
    private List<Recomendation> selectedCandidates;
    private int validity_day;
    private int min_rec;
    private int max_rec;
    
    @PostConstruct
    public void init() {
        validity_day = settingsEjb.findSettings().getRecommendationsValidity();
        min_rec = settingsEjb.findSettings().getMinRecommendations();
        max_rec = settingsEjb.findSettings().getMaxRecommendations();
    }
    
    public List<Recomendation> getReceiverList() {
        this.setReceiverList(recommendationEjb.findRecByReceiver(loginBean.getId()));
        return receiverList;
    }

    public void setReceiverList(List<Recomendation> receiverList) {
        this.receiverList = receiverList;
    }

    public List<Recomendation> getGiverList() {
        this.setGiverList(recommendationEjb.findForConfirm(loginBean.getId(), Boolean.FALSE)); //kolkas hardkodinam
        return giverList;
    }

    public void setGiverList(List<Recomendation> giverList) {
        this.giverList = giverList;
    }

    public List<Recomendation> getSelectedCandidates() {
        return selectedCandidates;
    }

    public void setSelectedCandidates(List<Recomendation> selectedCandidates) {
        this.selectedCandidates = selectedCandidates;
    }
	  
    public String getFullname() {
	return fullname;
    }    
	
    public void setFullname(String fullname) {
	this.fullname = fullname;
    }

    public Recomendation getRec() {
        return rec;
    }

    public void setRec(Recomendation rec) {
        this.rec = rec;
    }

    public RecomendationPK getPK() {
        return PK;
    }

    public void setPK(RecomendationPK PK) {
        this.PK = PK;
    }
    
    public void clearRecommendation() {
	this.fullname = "";
	this.rec = new Recomendation();
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
                    int id = Integer.parseInt(arr[0].substring(0, 1));
                    this.PK.setReceiverFbid(loginBean.getId());
                    this.PK.setGiverFbid(this.accountBean.getMemberList().get(id-1).getFacebookid());
                    this.rec.setRecomendationPK(this.PK);
                    this.rec.setIsGiven(Boolean.FALSE);
                    this.rec.setRecDate(Calendar.getInstance().getTime());
                    recommendationEjb.addRecommendation(this.rec);
                    MessageUtil.addSuccessMessage("Rekomendacija sėkmingai išsiųsta!");
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
            for (int i=0; i<this.selectedCandidates.size(); i++) {
                recommendationEjb.updateRecommendation(this.selectedCandidates.get(i));
                checkBecomingMember(this.selectedCandidates.get(i).getRecomendationPK().getReceiverFbid());
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
            for (int i=0; i<this.selectedCandidates.size(); i++) {
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
    
    public String checkBecomingMember(String fbid) {
        try {
            List<Recomendation> recList = recommendationEjb.findRecByReceiver(fbid);
            int confirmAmount = 0;
            for (int i=0; i<recList.size(); i++) {
                if (Objects.equals(recList.get(i).getIsGiven(), Boolean.TRUE)) confirmAmount++;
            }
            if (confirmAmount >= this.min_rec) {
                accountEjb.updateAccountStatus(fbid);
                recommendationEjb.deleteRecommendations(fbid);
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
}
