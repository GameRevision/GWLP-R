/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.intershard.utils;


/**
 * This enum holds the different languages an outpost might have.
 * (E.g. DistrictRegion: Europe, DistrictLanguage: French)
 * 
 * @author _rusty
 */
public enum DistrictLanguage 
{
    Default (-1),
    English (0),
    Korean  (1),
    French  (2),
    German  (3),
    Italian (4),
    Spanish (5),
    // TODO missing... chinese?
    Polish  (9),
    Russian (10);
        
    private final int id;

    private DistrictLanguage(int id) 
    {
        this.id = id;
    }
    
    public byte get()
    {
        return (byte)id;
    }
}
