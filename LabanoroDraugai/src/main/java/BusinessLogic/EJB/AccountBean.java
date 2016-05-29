package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.JPA.Account;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Kristaliukas
 */
@Named
@RequestScoped
public class AccountBean {

    private static final String CANDIDATE = "Kandidatas";
    private static final String ADMIN = "Administratorius";

    @Inject
    LoginBean loginBean;

    @Inject
    AccountCRUD accountEjb;
    private List<Account> accountList = new ArrayList<>();

    private List<Account> memberList = new ArrayList<>();

    private Account selectedAccount;

    private Account account;

    @PostConstruct
    public void init() {
        this.loadAccounts();
        this.accountList = this.getAccountList();
    }

    public List<Account> getAccountList() {
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
        List<Account> tempList = accountEjb.findAllAccounts();
        for (Account item : tempList) {

            if (item.getStatus().toLowerCase().equals(CANDIDATE.toLowerCase()) || item.getFacebookid().equals(loginBean.getFbid())) {
            } else {
                item.setAge(estimateAge(item.getDateOfBirth()));
                this.accountList.add(item);
            }

        }
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
        setRenderProperties();
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

    private void setRenderProperties() {
        if (selectedAccount.getPhotoImageid() == null) {
            selectedAccount.setRenderFb(true);
            selectedAccount.setRenderDb(false);
        } else {
            selectedAccount.setRenderFb(false);
            selectedAccount.setRenderDb(true);
        }
    }

    public void loadMembers() {
        this.memberList = accountEjb.findAllAccounts();
        for (int i = 0; i < this.memberList.size(); i++) {
            if (CANDIDATE.toLowerCase().equals(this.memberList.get(i).getStatus().toLowerCase())) {
                this.memberList.remove(i);
                i--;
            }
        }
    }

    public boolean isCandidate() {
        account = accountEjb.findAccountById(loginBean.getId());
        return CANDIDATE.toLowerCase().equals(account.getStatus().toLowerCase());
    }

    public boolean isNotCandidate() {
        account = accountEjb.findAccountById(loginBean.getId());
        return !CANDIDATE.toLowerCase().equals(account.getStatus().toLowerCase());
    }

    public boolean isAdmin() {
        account = accountEjb.findAccountById(loginBean.getId());
        return ADMIN.toLowerCase().equals(account.getStatus().toLowerCase());
    }

    public List<String> findMembers() {
        List<String> show = new ArrayList<>();
        this.loadMembers();
        for (int i = 0; i < this.getMemberList().size(); i++) {
            show.add(i + 1 + ". " + this.getMemberList().get(i).getFirstName() + " " + this.getMemberList().get(i).getLastName());
        }
        return show;
    }

    public String findMember(int id) {
        Account acc = accountEjb.findAccountById(id);
        return acc.getFirstName() + " " + acc.getLastName();
    }

    public String deleteAccount(Account ac) {
        accountEjb.deleteAccount(ac);
        return "membersReview?faces-redirect=true";
    }
}
