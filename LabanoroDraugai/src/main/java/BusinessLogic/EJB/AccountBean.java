
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.JPA.Account;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Kristaliukas
 */

@ManagedBean(name = "accountBean")
@SessionScoped
public class AccountBean {

    @EJB
    AccountDao accountEjb;

    private List<Account> accountList = new ArrayList<>();
    private Account account = new Account();
    private int[] age;

    public List<Account> getAccountList() {
        this.loadAccounts();
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void loadAccounts() {
        this.accountList = accountEjb.findAllAccounts();
    }
}
