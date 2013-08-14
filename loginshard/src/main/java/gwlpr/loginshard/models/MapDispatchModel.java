/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.models;

import gwlpr.database.jpa.MapJpaController;
import gwlpr.protocol.intershard.utils.DistrictLanguage;
import gwlpr.protocol.intershard.utils.DistrictRegion;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.gameapp.GameAppManager;
import realityshard.container.util.Handle;


/**
 * Stores created mapshard references and manages them.
 * 
 * TODO: If a mapshard is filled with people, create a new instance rather
 * than returning it when requested.
 * 
 * TODO: Enhance this to better manage the mapshards
 * 
 * TODO: Add fields for pvp and outpost to the mapshards
 * 
 * @author _rusty
 */
public final class MapDispatchModel 
{

    private static final Logger LOGGER = LoggerFactory.getLogger(MapDispatchModel.class);
    
    private final Map<Handle<GameAppContext>, MapShardBean> mapShardMetaInfo = new ConcurrentHashMap<>();
    private final Handle<GameAppContext> context;
    
    
    /**
     * Constructor.
     * 
     * @param       context                 We need the context to create mapshards
     *                                      or shut them down...
     */
    public MapDispatchModel(Handle<GameAppContext> context)
    {
        this.context = context;
    }
    
    
    /**
     * Get or create a map shard context with the game-map-id and default
     * region / language settings.
     * 
     * @param       gameMapId
     * @return      The context's bean, or null if it couldn't be created.
     */
    public Handle<GameAppContext> getOrCreateExplorable(int gameMapId)
    {
        return getOrCreate(gameMapId, DistrictRegion.Default, DistrictLanguage.Default, 0, false);
    }
    
    
    /**
     * Get or create a map shard context with the game-map-id and specified
     * region / language settings.
     * 
     * @param       gameMapId
     * @param       region
     * @param       language
     * @return      The context, or null if it couldn't be created.
     */
    public Handle<GameAppContext> getOrCreate(int gameMapId, DistrictRegion region, DistrictLanguage language, int instanceNum, boolean isOutpost)
    {
        DistrictRegion reg = region;
        DistrictLanguage lan = language;
        
        if (region == DistrictRegion.Default || language == DistrictLanguage.Default)
        {
            reg = DistrictRegion.Europe;
            lan = DistrictLanguage.English;
        }
        
        MapShardBean result = null;
        
        // search for the mapshard...
        for (MapShardBean mapShard : mapShardMetaInfo.values()) 
        {
            if (   mapShard.getMap().getGameID() == gameMapId
                && mapShard.getInstanceNumber()  == instanceNum
                && mapShard.getRegion()          == reg
                && mapShard.getLanguage()        == lan)
            {
                // found the perfect shard...
                result = mapShard;
            }
        }
        
        // none found, creating a new one
        if (result == null)
        {
            result = tryCreate(gameMapId, reg, lan, instanceNum, isOutpost);
            
            // failcheck
            if (result == null) { return null; }
        }
        
        return result.getMapShardContext();
    }
    
    
    /**
     * We've instructed a server to accept a client, and wait for it to reply.
     * 
     * @param       clientHandle
     * @param       mapShardContext  
     */
    public void clientWaitingFor(Handle<ClientBean> clientHandle, Handle<GameAppContext> mapShardContext)
    {
        MapShardBean mapShard = mapShardMetaInfo.get(mapShardContext);
        
        if (mapShard != null)
        {
                mapShard.getWaitingClients().add(clientHandle);
        }
    }
    
    
    /**
     * Try to resolve a pending client dispatch and add it to the
     * list of connected clients.
     * 
     * 
     * @param       clientHandle 
     * @param       mapShardContext 
     * @param       accepted                True or false, depending on the client has
     *                                      been accepted by the mapshard.
     */
    public void clientGotAcceptedBy(Handle<ClientBean> clientHandle, Handle<GameAppContext> mapShardContext, boolean accepted)
    {
        MapShardBean mapShard = mapShardMetaInfo.get(mapShardContext);
        
        if (mapShard != null)
        {
            mapShard.getWaitingClients().remove(clientHandle);
        
            if (accepted)
            {
                mapShard.getPendingClients().add(clientHandle);
            }
        }
    }
    
    
    /**
     * Needs to be called when the dispatch of a client is done.
     * This will remove the client from the pending clients and add it to the
     * connected clients list.
     * 
     * 
     * @param       clientHandle
     * @param       mapShardContext  
     */
    public void dispatchDone(Handle<ClientBean> clientHandle, Handle<GameAppContext> mapShardContext)
    {
        MapShardBean mapShard = mapShardMetaInfo.get(mapShardContext);
        
        if (mapShard != null)
        {
            mapShard.getWaitingClients().remove(clientHandle);
            mapShard.getPendingClients().add(clientHandle);
        }
    }
    
    
    /**
     * Getter.
     * 
     * @param       mapShardContext
     * @return      Get the bean attached to this mapshard handle.
     */ 
    public MapShardBean getBean(Handle<GameAppContext> mapShardContext)
    {
        return mapShardMetaInfo.get(mapShardContext);
    }
    
    
    /**
     * Try to create a new map shard (fails by returning null)
     * TODO: allow pvp mapshards here. 
    */
    private MapShardBean tryCreate(int gameMapId, DistrictRegion region, DistrictLanguage language, int instanceNumber, boolean isOutpost) 
    {
        
        gwlpr.database.entities.Map mapEntity = MapJpaController.get().findByGameId(gameMapId);
        
        // failcheck
        if (mapEntity == null) { return null; }
        
        // try create the game app (with mapid parameter)
        GameAppManager manager = context.get().getManager();
        
        // failcheck
        if (manager == null || !manager.canCreateGameApp("MapShard")) { return null; }
        
        // create the gameapp
        HashMap<String,String> params = new HashMap<>();
        params.put("MapId",             String.valueOf(mapEntity.getId()));
        params.put("IsPvP",             String.valueOf(false));
        params.put("IsOutpost",         String.valueOf(isOutpost));
        params.put("InstanceNumber",    String.valueOf(instanceNumber));
        params.put("DistrictRegion",    region.toString());
        params.put("DistrictLanguage",  language.toString());
        
        Handle<GameAppContext> mapShardContext = manager.createGameApp("MapShard", context, params);
        
        // failcheck
        if (mapShardContext == null) { return null; }
        
        // create the bean
        MapShardBean mapShard = new MapShardBean(mapShardContext, mapEntity, instanceNumber, region, language);
        
        // register it
        mapShardMetaInfo.put(mapShardContext, mapShard);
        
        return mapShard;
    }
}
