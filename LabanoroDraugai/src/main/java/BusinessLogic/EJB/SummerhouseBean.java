
package BusinessLogic.EJB;

import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Summerhouse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Liudas 
 */

@Named
@RequestScoped
public class SummerhouseBean implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject 
    SummerhouseCRUD summerhouseCRUD;
    
    private List<Summerhouse> summerhouses;
    private List<Summerhouse> filteredSummerhouses;
    private double priceFrom;
    private double priceTo;
    private int bedsFrom;
    private int bedsTo;
    private Date availableFrom;
    private Date availableTo;

    @PostConstruct
    public void init() {
        filteredSummerhouses = new ArrayList();
        summerhouses = summerhouseCRUD.findAllSummerhouses();
    }
    
    public void setFilteredSummerhouses(List<Summerhouse> filteredSummerhouses) {
        this.filteredSummerhouses = filteredSummerhouses;
    }
    
    public void filterSummerhouses() {
        filteredSummerhouses.clear();       
        for (Summerhouse summerhouse : summerhouses) {
            if (summerhouse.getCost() >= priceFrom 
                    && ((priceTo > 0 && summerhouse.getCost() <= priceTo) || priceTo == 0)
                    && (summerhouse.getBeds() >= bedsFrom) 
                    && ((bedsTo > 0 && summerhouse.getBeds() <= bedsTo) || bedsTo == 0)
                    && (availableFrom == null || summerhouse.getValidityStart().after(availableFrom))
                    && (availableTo == null || summerhouse.getValidityEnd().before(availableTo))) {            
                filteredSummerhouses.add(summerhouse);
            }
        }
        summerhouses = filteredSummerhouses;
    }
    
    public void recoverSummerhouses() {
        summerhouses = summerhouseCRUD.findAllSummerhouses();
    }
    
    public List<Summerhouse> getSummerhouses() {
        return summerhouses;
    }

    public void setSummerhouses(List<Summerhouse> summerhouses) {
        this.summerhouses = summerhouses;
    }
    
    public double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(double priceTo) {
        this.priceTo = priceTo;
    }

    public int getBedsFrom() {
        return bedsFrom;
    }

    public void setBedsFrom(int bedsFrom) {
        this.bedsFrom = bedsFrom;
    }

    public int getBedsTo() {
        return bedsTo;
    }

    public void setBedsTo(int bedsTo) {
        this.bedsTo = bedsTo;
    }

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Date getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Date availableTo) {
        this.availableTo = availableTo;
    }

    public List<Summerhouse> getFilteredSummerhouses() {
        return filteredSummerhouses;
    }
        
}