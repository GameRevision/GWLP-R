/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.models;

import com.gamerevision.gwlpr.database.AccountEntity;
import com.gamerevision.gwlpr.database.CharacterEntity;
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
    
    private AccountEntity account;
    private CharacterEntity chara;
    private int errorCode = 0;
    
    
    /**
     * Constructor.
     */
    public CheckLoginInfo(
            DatabaseConnectionProvider connectionProvider, 
            String eMail,  
            String charName)
    {
        this.account = AccountEntity.getByEMail(connectionProvider, eMail);
        this.chara = CharacterEntity.getCharacter(connectionProvider, charName);
    }
    
    
    /**
     * Returns boolean whether the login information is valid or not.
     * 
     * TODO: Extend it (better validation, banned, ...)
     */
    public boolean isValid(String password)
    {
        if (account == null)
        {
            // there is no account with this eMail
            errorCode = 227;
            return false;
        }
        
        if (false && !password.startsWith(account.getPassword()))
        {
            // password doesnt match
            errorCode = 227; 
        
            return false;
        }
        
        if (chara == null)
        {
            // there is no such character
            errorCode = 227;
            return false;
        }
        
        return true;
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
