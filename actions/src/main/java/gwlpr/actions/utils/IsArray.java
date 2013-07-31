/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * This annotation can be used with in the packets to specify details of an array.
 * (Code taken from iDemmel (with permission))
 * 
 * @author _rusty
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IsArray 
{
    boolean constant();
    int size();
    int prefixLength(); // in byte-count
}
