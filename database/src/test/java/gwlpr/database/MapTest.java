/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database;

import gwlpr.database.entities.Map;
import gwlpr.database.jpa.MapJpaController;
import org.junit.Test;


/**
 *
 * @author _rusty
 */
public class MapTest 
{
    @Test
    public void test()
    {
         Map map = MapJpaController.get().findByGameId(1);
        
        assert map.getId() == 1;
    }
}
