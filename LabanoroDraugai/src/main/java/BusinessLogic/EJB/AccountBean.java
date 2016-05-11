
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.JPA.Account;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Kristaliukas
 */
@ManagedBean
@RequestScoped
public class AccountBean {
    @EJB
    AccountDao accountEjb;
    private List<Account> accountList = new ArrayList<>();
    
    private List<Account> memberList = new ArrayList<>();
    
    private Account selectedAccount;
    
    private Account account;
    
    @ManagedProperty(value="#{loginBean}")
    LoginBean loginBean;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public List<Account> getAccountList() {
        this.loadAccounts();
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
    
    public List<Account> getMemberList() {
        this.loadMembers();
        return memberList;
    }

    public void setMemberList(List<Account> memberList) {
        this.memberList = memberList;
    }
    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }  

    public void loadAccounts() {
        this.accountList = accountEjb.findAllAccounts();
        for(Account item : accountList){
		item.setAge(estimateAge(item.getDateOfBirth()));
	}
    } 
    
    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
    
    private int estimateAge(Date bday) {
        Calendar dob = Calendar.getInstance();
        dob.setTime(bday);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        //System.out.println(account.getDateOfBirth().getDate() + " seni geri metai" + today.get(Calendar.DAY_OF_MONTH) + " mėn" + today.get(Calendar.DAY_OF_WEEK_IN_MONTH) + " diena" + today.get(Calendar.DAY_OF_WEEK) + " ssav diena");
        return age;
    }
    
    public void loadMembers() {
        this.memberList = accountEjb.findAllAccounts();
        for (int i=0; i<this.memberList.size(); i++) {
            if ("kandidatas".equals(this.memberList.get(i).getStatus().toLowerCase())) {
                this.memberList.remove(i);
                i--;
            }
        }
    }
    
    public boolean isCandidate() {
        account=accountEjb.findAccountById(loginBean.getId());
        return "Kandidatas".toLowerCase().equals(account.getStatus().toLowerCase());
    }
    
    public boolean isNotCandidate() {
        account=accountEjb.findAccountById(loginBean.getId());
        return !"Kandidatas".toLowerCase().equals(account.getStatus().toLowerCase());
    }
    
    public boolean isAdmin() {
        account=accountEjb.findAccountById(loginBean.getId());
        return "Administratorius".toLowerCase().equals(account.getStatus().toLowerCase());
    }
    
    public List<String> findMembers() {
        List<String> show = new ArrayList<>();
        this.loadMembers();
        for (int i=0; i<this.getMemberList().size(); i++) {
            show.add(i+1+". "+this.getMemberList().get(i).getFirstName()+" "+this.getMemberList().get(i).getLastName());
        }
        return show;
    }
    
    public String findMember(int id) {
        Account acc = accountEjb.findAccountById(id);
        return acc.getFirstName()+" "+acc.getLastName();
    }
}
