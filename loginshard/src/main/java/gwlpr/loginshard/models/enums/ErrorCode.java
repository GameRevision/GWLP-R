/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.models.enums;


/**
 * This enum holds some of the error codes we use to inform the client
 * that something has gone wrong (internally or externally, see message)
 * 
 * @see http://wiki.guildwars.com/wiki/Error_code
 * 
 * @author _rusty
 */
public enum ErrorCode 
{
    None                (000, "No error."),
    InternalServerError (004, "The server was unable to communicate with a dependent system/server."), // also: 010, 013, 015, 040, 042, 050, 072
    AccountInUse        (035, "The client has been logged out, because another client logged in on the same account."),
    Banned              (045, "The account has been temporarily or permanently banned."),
    LoginFail           (227, "The given email, password or character were incorrect.");
        
    private final int id;
    private final String message;

    private ErrorCode(int id, String message) 
    {
        this.id = id;
        this.message = message;
    }
    
    public int get()
    {
        return id;
    }
    
    public String message()
    {
        return message;
    }
}
