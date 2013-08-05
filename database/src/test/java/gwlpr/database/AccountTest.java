/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database;

import gwlpr.database.entities.Account;
import gwlpr.database.jpa.AccountJpaController;
import org.junit.Test;


/**
 * 
 * @author _rusty
 */
public class AccountTest 
{
    @Test
    public void Test()
    {
         Account a = AccountJpaController.get().findAccount("root@gwlp.ps");
         
         assert a.getId() == 1;
    }
}
