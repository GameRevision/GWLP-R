/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * This annotation can be used to indicate that a given int attribute is 
 * actually a variable length integer (not an unsigned short).
 * 
 * A variable sized integer is basically a special integer format that uses the
 * integer bytes only up to what is needed of them.
 * 
 * @author _rusty
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IsVarInt
{
}
