/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.model.logic;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P004_AccountLoginAction;
import com.gamerevision.gwlpr.framework.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.loginshard.model.database.Account;
import java.nio.charset.Charset;


/**
 * This class checks the login information.
 * 
 * @author miracle444
 */
public class CheckLoginInfo
{
    
    private Account account;
    private String eMail;
    private String password;
    private String characterName;
    private int errorCode = 0;
    
    
    /*
     * Constructor.
     */
    public CheckLoginInfo(DatabaseConnectionProvider connectionProvider, P004_AccountLoginAction action)
    {
        this.eMail = new String(action.getEmail());
        this.password = new String(action.getPassword(), Charset.forName("UTF-16LE"));
        this.characterName = new String(action.getCharacterName());
        this.account = Account.getByEMail(connectionProvider, eMail);
    }
    
    
    /*
     * Returns boolean whether the login information is valid or not.
     * 
     * TODO: Extend it (better validation, banned, ...)
     */
    public boolean isValid()
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
