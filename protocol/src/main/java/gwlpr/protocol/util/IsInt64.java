/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * This annotation can be used to indicate that a given long attribute is 
 * actually really a long (otherwise it will be interpreted as an unsigned integer)
 * 
 * @author _rusty
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IsInt64
{
}
