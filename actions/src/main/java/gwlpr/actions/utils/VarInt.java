/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions.utils;


/**
 * Variable sized integer (maximum 1 Int)
 * (This is currently a simple wrapper class with no functionality)
 * 
 * @author _rusty
 */
public class VarInt
{
    
    private int i;
    
        
    public VarInt(int i)
    {
        this.i = i;
    }

    
    public int get() 
    {
        return i;
    }

    
    public void set(int i) 
    {
        this.i = i;
    }
}
