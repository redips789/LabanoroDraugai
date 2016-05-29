/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.FeeCRUD;
import DataAccess.JPA.Fee;
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
@RequestScoped
@Named
@Stateful
public class AddRemovePaymentsBean {
    
    @Inject
    FeeCRUD feeCRUD;
    
    private double amount;
    private String description = "Pateikite mokėjimo aprašymą čia";
    private int points;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    private List<Fee> feeList;

    public List<Fee> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<Fee> feeList) {
        this.feeList = feeList;
    }
    
    @PostConstruct
    public void init() {
        feeList = feeCRUD.findAllFees();
        System.out.println("**************************Sukonstruojama******************************");
    }
    
    public void removeFee(int feeid){
        Fee fee = feeCRUD.findFee(feeid);
        try{
        feeCRUD.removeFee(fee);
        feeList.remove(fee);
        Message.addSuccessMessage("Mokėjimas pašalintas sėkmingai");
        }catch(Exception ex){
            Message.addErrorMessage("Nepavyko pašalinti mokėjimo. Mokėjimas yra naudojamas. Turite patvirtinti visus šio tipo mokėjimus");
        }
    }
    
    public void addFee(){
        try{
        Fee fee = new Fee();
        fee.setAmount(amount);
        fee.setDescription(description);
        fee.setPoints(points);
        amount  = 0;
        description = "Pateikite mokėjimo aprašyma čia";
        points = 0;
        try{
        feeCRUD.addFee(fee);
        feeList.add(fee);
        Message.addSuccessMessage("Mokėjimas pridėtas sėkmingai");
        
        }catch(Exception ex2){
            Message.addErrorMessage("Nepavyko pridėti mokėjimo. Pabandykite užpildyti iš naujo");
        }
        }catch(Exception ex1){
        Message.addErrorMessage("Nepavyko pridėti mokėjimo. Blogai užpildyti laukai. Pabandykite užpildyti iš naujo");
        }
    }
}
