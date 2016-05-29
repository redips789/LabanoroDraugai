/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.MessagesCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Messages;
import Messages.Message;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author darbas
 */
@Named
@RequestScoped
@Stateful
public class AddMessageBean {

    @Inject
    MessagesCRUD messagesCRUD;

    @Inject
    LoginBean loginBean;

    @Inject
    AccountCRUD accountEjb;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String addMessage() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            System.out.println(dateFormat.format(cal.getTime()));
            String dateString = dateFormat.format(cal.getTime());
            Date date = new Date();
            date = dateFormat.parse(dateString);

            Account account = accountEjb.findAccount(loginBean.getFbid());
            Messages messages = new Messages();

            messages.setMessage(message);
            messages.setAccountId(account);
            messages.setDate(date);
            
            messagesCRUD.addMessages(messages);
            Message.addSuccessMessage("Žinutė pridėta sėkmingai");
            return "home?faces-redirect=true";
        } catch (Exception ex) {
            Message.addErrorMessage("Nepavyko pridėti Jūsų žinutės");
            return "addMessage?faces-redirect=true";
        }

    }
}
