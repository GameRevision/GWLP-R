/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
        List<Integer> l = new ArrayList<>();
        
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);
        
        ListIterator<Integer> it = l.listIterator();
        
        assert it.hasNext();
        assert !it.hasPrevious();
        
        assert 1 == it.next();
        
        assert it.hasNext();
        assert it.hasPrevious();
        
        it.next();
        assert 3 == it.next();
        assert 3 == it.previous();
        assert 3 == it.next();
        
        
    }
}
