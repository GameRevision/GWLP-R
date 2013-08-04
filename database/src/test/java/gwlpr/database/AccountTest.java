/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database;

import gwlpr.database.entities.Account;
import gwlpr.database.jpa.AccountJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.gamerevision.gwlpr_database_jar_0.2.1PU");
         
         Account a = new AccountJpaController(emf).findAccount("root@gwlp.ps");
         
         assert a.getId() == 1;
    }
}
