/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.models;

import gwlpr.database.entities.Account;
import gwlpr.database.entities.Character;
import gwlpr.database.jpa.AccountJpaController;
import gwlpr.loginshard.models.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class checks the login information.
 * 
 * @author miracle444
 */
public class LoginModel
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(LoginModel.class);
    
    private Account account;
    private Character chara;
    private ErrorCode errorCode = ErrorCode.None;
    
    
    /**
     * Constructor.
     */
    public LoginModel(String eMail,  String charName)
    {
        account = AccountJpaController.get().findAccount(eMail);
        chara = null;
        
        if (account == null) { return; }
        
        for (Character character : account.getCharacterCollection()) 
        {
            // this way we can make sure that the character exists and the
            // account owns it
            if (charName.equals(character.getName())) { chara = character; }
        }
    }
    
    
    /**
     * Constructor.
     */
    public LoginModel(Account account, String charName)
    {
        this.account = account;
        chara = null;
        
        for (Character character : account.getCharacterCollection()) 
        {
            // this way we can make sure that the character exists and the
            // account owns it
            if (charName.equals(character.getName())) { chara = character; }
        }
    }
    
    
    /**
     * Returns whether the login information is valid or not.
     * 
     * TODO: Extend it (better validation, banned, ...)
     */
    public boolean isValid(String password)
    {
        if (account == null)
        {
            // there is no account with this eMail
            errorCode = ErrorCode.LoginFail;
            return false;
        }
        
        if (false && !password.startsWith(account.getPassword()))
        {
            // password doesnt match
            errorCode = ErrorCode.LoginFail;
            return false;
        }
        
        if (chara == null)
        {
            // there is no such character
            errorCode = ErrorCode.LoginFail;
            return false;
        }
        
        return true;
    }
    
    
    /**
     * Test if the account actually has a certain character
     * 
     * @return 
     */
    public boolean hasChar()
    {
        return chara != null;
    }
    
    
    /**
     * Getter.
     * 
     * @return     The latest error code. (ErrorCode.None if no error)
     */
    public ErrorCode getErrorCode()
    {
        return errorCode;
    }
    

    public Account getAccount() 
    {
        return account;
    }

    
    public Character getChara() 
    {
        return chara;
    }
}
