/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Alternatives;

import DataAccess.JPA.Account;
import DataAccess.JPA.Settings;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

/**
 *
 * @author darbas
 */
@Alternative
@Stateless
public class GroupByDays implements GroupDistribution, Serializable {

    /**
     *
     * @param account
     * @param settings
     * @return
     */
    @Override
    public boolean canGroupReserve(Account account, Settings settings) {
        /// Algoritmas turėtų būti čia
        return true; 
    }

    

    

  
}
