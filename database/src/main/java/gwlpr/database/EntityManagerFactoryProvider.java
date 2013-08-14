/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Static stuff so we dont need to initialize the JPA controllers
 * 
 * TODO: enable configuration and such...
 * 
 * @author _rusty
 */
public class EntityManagerFactoryProvider 
{
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.gamerevision.gwlpr_database_jar_0.2.1PU");
    
    public static EntityManagerFactory get()
    {
        return emf;
    }
}
