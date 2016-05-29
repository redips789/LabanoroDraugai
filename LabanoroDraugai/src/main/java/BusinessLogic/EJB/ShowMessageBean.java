/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.MessagesCRUD;
import DataAccess.JPA.Messages;
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
public class ShowMessageBean {

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

}
