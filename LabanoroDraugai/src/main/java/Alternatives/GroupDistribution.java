/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Alternatives;

import DataAccess.JPA.Account;
import DataAccess.JPA.Settings;

/**
 *
 * @author darbas
 */

public interface GroupDistribution {
    
    /**
     *
     * @param account
     * @param settings
     * @return
     */
    public boolean canGroupReserve(Account account, Settings settings);
    
}
