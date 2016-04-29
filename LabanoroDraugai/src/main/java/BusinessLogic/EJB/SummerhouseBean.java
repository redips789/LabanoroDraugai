
package BusinessLogic.EJB;

import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Summerhouse;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Liudas 
 */

@Named
@ManagedBean
public class SummerhouseBean implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB SummerhouseCRUD summerhouseCRUD;
    
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
        filteredSummerhouses = summerhouseCRUD.findAllSummerhouses();
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

    public void setFilteredSummerhouses(List<Summerhouse> filteredSummerhouses) {
        this.filteredSummerhouses = filteredSummerhouses;
    }
    
    
    
    public void filterSummerhouses() {
        filteredSummerhouses.clear();       
        for (Summerhouse summerhouse : summerhouses) {
            //System.out.println("cia turi but isspausdinti visi pavadinimai"+summerhouse.getTitle());
           
            if (summerhouse.getCost() >= priceFrom 
                    && ((priceTo > 0 && summerhouse.getCost() <= priceTo) || priceTo==0)
                    && (summerhouse.getBeds() >= bedsFrom) 
                    && ((bedsTo > 0 && summerhouse.getBeds() <= bedsTo) || bedsTo==0)) {
               // System.out.println("remove because pricefrom" + priceFrom + summerhouse.getTitle());
                filteredSummerhouses.add(summerhouse);
            }
        }
        summerhouses = filteredSummerhouses;
    }
    
    public void recoverSummerhouses() {
        summerhouses = summerhouseCRUD.findAllSummerhouses();
    }
      
}

//for (Summerhouse summerhouse : summerhouses) {
//            System.out.println("cia turi but isspausdinti visi pavadinimai"+summerhouse.getTitle());
//            if (summerhouse.getCost() < priceFrom) {
//                System.out.println("remove because pricefrom" + priceFrom + summerhouse.getTitle());
//                summerhouses.remove(summerhouse);
//            }
//            if (priceTo > 0 && summerhouse.getCost() > priceTo) {
//                System.out.println("remove because priceTo" + priceTo + summerhouse.getTitle());
//                summerhouses.remove(summerhouse);
//            }
//            if (summerhouse.getBeds() < bedsFrom) {
//                System.out.println("remove because bedsfrom" + bedsFrom + summerhouse.getTitle());
//                summerhouses.remove(summerhouse);
//            }
//            if (bedsTo > 0 && summerhouse.getBeds() > bedsTo) {
//                System.out.println("remove because bedsTo" + bedsTo + summerhouse.getTitle());
//                summerhouses.remove(summerhouse);
//            }
//        }




//System.out.println("atejo i metoda");
//        for (int i = 0; i < summerhouses.size(); i++) {
//            System.out.println("cia turi but isspausdinti visi pavadinimai"+summerhouses.get(i).getTitle());
//            if ((summerhouses.get(i).getCost() < priceFrom) || (priceTo > 0 && summerhouses.get(i).getCost() > priceTo) 
//                    || (summerhouses.get(i).getBeds() < bedsFrom) || (bedsTo > 0 && summerhouses.get(i).getBeds() > bedsTo)) {
//                //System.out.println("remove because pricefrom" + priceFrom + summerhouses.get(i).getTitle());
//                summerhouses.remove(i);
//                i--;
//            }
//        }