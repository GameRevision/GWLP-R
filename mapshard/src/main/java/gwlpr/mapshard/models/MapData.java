/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;


/**
 * Use this model to store and retrieve map specific data.
 * 
 * @author _rusty
 */
public class MapData 
{
    
    private final int mapID;
    private final int gameMapID;
    private final int mapFileHash;
    private final Collection<GWVector> spawns;
    
    
    /**
     * Constructor.
     * 
     * @param       mapID                   The game-internal map ID of this mapshard
     * @param gameMapID 
     * @param       mapFileHash             The GW-dat specific file hash of the mapfile
     * @param       spawns                  (Simplified) SpawnPoints
     */
    public MapData(int mapID, int gameMapID, int mapFileHash, Collection<GWVector> spawns) 
    {
        this.mapID = mapID;
        this.gameMapID = gameMapID;
        this.mapFileHash = mapFileHash;
        this.spawns = spawns;
    }
        

    /**
     * Getter.
     */
    public int getMapID() 
    {
        return mapID;
    }

    
    /**
     * Getter.
     */
    public int getMapFileHash() 
    {
        return mapFileHash;
    }

    
    /**
     * Getter.
     * Absolute (meaning this is a actually point, not a vector)
     */
    public Collection<GWVector> getSpawns()
    {
        return spawns;
    }
    
    
    /**
     * Getter.
     * Returns a random spawn of all that are available.
     */
    public GWVector getRandomSpawn()
    {
        return (GWVector) spawns.toArray()[new Random().nextInt(spawns.size())];
    }
}
