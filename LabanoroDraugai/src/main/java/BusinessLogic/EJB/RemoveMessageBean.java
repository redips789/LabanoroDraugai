/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.MessagesCRUD;
import DataAccess.JPA.Messages;
import Messages.Message;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class RemoveMessageBean {
    
    @Inject
    MessagesCRUD messagesCRUD;

    private List<Messages> messagesList;

    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    @PostConstruct
    public void init() {
        messagesList = messagesCRUD.findAllMessages();
        System.out.println("**************************Sukonstruojama******************************");
    }
    
    public void removeMessage(int messageId){
        try{
        Messages messages = messagesCRUD.findMessages(messageId);
        try{
            messagesCRUD.removeMessages(messages);
            messagesList.remove(messages);
            
            Message.addSuccessMessage("Pašalinimas sėkmingas");
        }catch(Exception ex2){
            Message.addErrorMessage("Įvyko klaida įrašant duomenis į duomenų bazę. Bandykite dar kartą");
        }
        }catch(Exception ex1){
            Message.addErrorMessage("Įvyko klaida nuskaitant duomenis iš duomenų bazės. Bandykite dar kartą");
            System.out.println("Nepaima user arba fee iš duomenu bazes");
        }
    }
}
