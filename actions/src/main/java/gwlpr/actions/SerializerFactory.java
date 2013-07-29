/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import realityshard.shardlet.Action;


/**
 * Creates a serializer chain for given generic action.
 * 
 * @author _rusty
 */
public class SerializerFactory 
{
    public <T extends Action> SerializationFilter produceSerializer(Class<T> clazz)
    {
        return null;
    }
}
