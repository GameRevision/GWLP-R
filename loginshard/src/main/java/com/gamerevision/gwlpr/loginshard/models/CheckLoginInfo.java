/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.models;

import com.gamerevision.gwlpr.database.DBAccount;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class checks the login information.
 * 
 * @author miracle444
 */
public class CheckLoginInfo
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(CheckLoginInfo.class);
    
    private DBAccount account;
    private int errorCode = 0;
    
    
    /*
     * Constructor.
     */
    public CheckLoginInfo(DatabaseConnectionProvider connectionProvider, String eMail)
    {
        this.account = DBAccount.getByEMail(connectionProvider, eMail);
    }
    
    
    /*
     * Returns boolean whether the login information is valid or not.
     * 
     * TODO: Extend it (better validation, banned, ...)
     */
    public boolean isValid(String password)
    {
        if (account == null)
        {
            // there is no account with this eMail.
            errorCode = 227;
            return false;
        }
        
        if (password.startsWith(account.getPassword()))
        {
            // password matches.
            return true;
        }
        
        // unknown login information
        errorCode = 227; 
        
        return false;
    }
    
    
    /*
     * Getter.
     * 
     * Returns the latest error code.
     */
    public int getErrorCode()
    {
        return errorCode;
    }
}
