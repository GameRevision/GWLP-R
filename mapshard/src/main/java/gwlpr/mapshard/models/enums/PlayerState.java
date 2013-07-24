/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models.enums;


/**
 * This enum defines the possible states of a connected player.
 * 
 * This is needed, because certain features of the server are only enabled
 * for players that have a certain state (like difference between loading into
 * an instance and actual playing on the instance)
 * 
 * @author _rusty
 */
public enum PlayerState 
{
    LoadingInstance,
    Playing
}
