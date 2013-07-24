/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Account entity for the ORM tool.
 * This was not generated ;)
 * 
 * @author _rusty
 */
@Entity
public class Account 
{
    
    @Id
    private int accountId;
    private String email;
    private String password;

    
    public int getAccountId()   { return accountId; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    
    
    public void setAccountId(int accountId)     { this.accountId = accountId; }
    public void setEmail(String email)          { this.email = email; }
    public void setPassword(String password)    { this.password = password; }
}
