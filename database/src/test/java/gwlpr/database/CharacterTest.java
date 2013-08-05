/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database;

import gwlpr.database.jpa.CharacterJpaController;
import org.junit.Test;


/**
 *
 * @author _rusty
 */
public class CharacterTest 
{
    @Test
    public void test()
    {
        gwlpr.database.entities.Character chara = CharacterJpaController.get().findByName("Test Char");
        
        assert chara.getAccountID().getId() == 1;
    }
}
