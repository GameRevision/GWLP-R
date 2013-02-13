/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models;


/**
 * Use this model to store and retrieve map specific data.
 * 
 * @author _rusty
 */
public class MapData 
{
    
    private final int mapID;
    private final int mapFileHash;
    private final GWVector spawn;
    
    
    /**
     * Constructor.
     * 
     * @param       mapID                   The game-internal map ID of this mapshard
     * @param       mapFileHash             The GW-dat specific file hash of the mapfile
     * @param       spawn                   (Simplified) SpawnPoint TODO: this might be more than one!
     */
    public MapData(int mapID, int mapFileHash, GWVector spawn) 
    {
        this.mapID = mapID;
        this.mapFileHash = mapFileHash;
        this.spawn = spawn;
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
    public GWVector getSpawn()
    {
        return spawn;
    }
    
}
