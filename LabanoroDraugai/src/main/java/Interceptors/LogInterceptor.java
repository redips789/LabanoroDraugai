/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interceptors;

import DataAccess.EJB.PaymentCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Payment;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;

/**
 *
 * @author darbas
 */
@Interceptor
@Interceptable
//@Priority(Interceptor.Priority.APPLICATION)
public class LogInterceptor implements Serializable {
    
    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    
    @PersistenceContext(type=PersistenceContextType.TRANSACTION, synchronization=SynchronizationType.UNSYNCHRONIZED) 
    EntityManager ac;
    
    private void addPayment(Payment payment){
        ac.persist(payment);
    }
    
    
    @AroundInvoke
    public Object intercept(InvocationContext context){
        
        System.out.println("SimpleInterceptor - Logging BEFORE calling method :"+context.getMethod().getName() );    
        String method = context.getMethod().getName();
        String methodClass = context.getMethod().getDeclaringClass().getName();
        
        /*Object[] parameters = context.getParameters();
        Account account = (Account) parameters[0];*/
        Payment payment = new Payment();
        Object result=null;
        System.out.println("-----------************------111111-------************----------");
        try{
            System.out.println("-----------************-----22222222--------************----------******");
            result = context.proceed();
            Account acc;
            acc = (Account) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("interceptorAccount");
            if(acc==null){
                System.out.println("-----------************-----NULL--------************----------******");
            }else{
                System.out.println(acc.getFacebookid());
                System.out.println(acc.getFirstName());
                System.out.println(acc.getLastName());
                payment.setClassName(methodClass);
                payment.setMethodName(method);
                payment.setFirstName(acc.getFirstName());
                payment.setLastName(acc.getLastName());
                payment.setStatus(acc.getStatus());
                payment.setDateTime(Calendar.getInstance().getTime());
                addPayment(payment);
            }
        }catch(Exception ex){
            System.out.println("-----------************-----33333333--------************----------******");
        }
        System.out.println("SimpleInterceptor - Logging AFTER calling method :"+context.getMethod().getName() );
        return result;
    }
}
