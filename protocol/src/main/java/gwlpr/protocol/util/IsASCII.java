/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * This annotation can be used to indicate that a given string attribute is 
 * actually really an ASCII string (otherwise it will be interpreted as UTF16)
 * 
 * @author _rusty
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IsASCII
{
}
