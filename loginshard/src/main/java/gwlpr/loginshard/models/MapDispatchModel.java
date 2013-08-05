/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.models;

import gwlpr.database.jpa.MapJpaController;
import gwlpr.loginshard.models.enums.DistrictLanguage;
import gwlpr.loginshard.models.enums.DistrictRegion;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.gameapp.GameAppManager;
import realityshard.container.util.Handle;
import realityshard.container.util.HandleRegistry;


/**
 * Stores created mapshard references and manages them.
 * 
 * Hint: Whenever the internal IDs used here are publicized
 * 
 * TODO: If a mapshard is filled with people, create a new instance rather
 * than returning it when requested.
 * 
 * @author _rusty
 */
public final class MapDispatchModel 
{

    private static final Logger LOGGER = LoggerFactory.getLogger(MapDispatchModel.class);
    
    private final HandleRegistry<MapShardBean> mapShardRegisty = new HandleRegistry<>();
    private final GameAppContext context;
    
    
    /**
     * Constructor.
     * 
     * @param       context                 We need the context to create mapshards
     *                                      or shut them down...
     */
    public MapDispatchModel(GameAppContext context)
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
    public Handle<MapShardBean> getOrCreate(int gameMapId)
    {
        return getOrCreate(gameMapId, DistrictRegion.Default, DistrictLanguage.Default);
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
    public Handle<MapShardBean> getOrCreate(int gameMapId, DistrictRegion region, DistrictLanguage language)
    {
        DistrictRegion reg = region;
        DistrictLanguage lan = language;
        
        if (region == DistrictRegion.Default || language == DistrictLanguage.Default)
        {
            reg = DistrictRegion.Europe;
            lan = DistrictLanguage.English;
        }
        
        Handle<MapShardBean> result = null;
        
        // search for the mapshard...
        for (Handle<MapShardBean> mapShardHandle : mapShardRegisty.getAllHandles()) 
        {
            MapShardBean mapShard = mapShardHandle.get();
            
            if (   mapShard.getMap().getGameID() == gameMapId
                && mapShard.getRegion()          == reg
                && mapShard.getLanguage()        == lan)
            {
                // found the perfect shard...
                result = mapShardHandle;
            }
        }
        
        // none found, creating a new one
        if (result == null)
        {
            result = tryCreate(gameMapId, reg, lan, 1);
        }
        
        return result;
    }
    
    
    /**
     * We've instructed a server to accept a client, and wait for it to reply.
     * 
     * @param       clientHandle
     * @param       mapShardHandle  
     */
    public void clientWaitingFor(Handle<ClientBean> clientHandle, Handle<MapShardBean> mapShardHandle)
    {
        mapShardHandle.get().getWaitingClients().add(clientHandle);
    }
    
    
    /**
     * Try to resolve a pending client dispatch and add it to the
     * list of connected clients.
     * 
     * 
     * @param       clientHandle 
     * @param       mapShardHandle 
     * @param       accepted                True or false, depending on the client has
     *                                      been accepted by the mapshard.
     */
    public void clientGotAcceptedBy(Handle<ClientBean> clientHandle, Handle<MapShardBean> mapShardHandle, boolean accepted)
    {
        mapShardHandle.get().getWaitingClients().remove(clientHandle);
        
        if (accepted)
        {
            mapShardHandle.get().getPendingClients().add(clientHandle);
        }
    }
    
    
    /**
     * Needs to be called when the dispatch of a client is done.
     * This will remove the client from the pending clients and add it to the
     * connected clients list.
     * 
     * 
     * @param       clientHandle
     * @param       mapShardHandle  
     */
    public void dispatchDone(Handle<ClientBean> clientHandle, Handle<MapShardBean> mapShardHandle)
    {
        mapShardHandle.get().getWaitingClients().remove(clientHandle);
        mapShardHandle.get().getPendingClients().add(clientHandle);
    }
    
    
    /**
     * Try to create a new map shard (fails by returning null)
     */
    private Handle<MapShardBean> tryCreate(int gameMapId, DistrictRegion region, DistrictLanguage language, int instanceNumber) 
    {
        
        gwlpr.database.entities.Map mapEntity = MapJpaController.get().findByGameId(gameMapId);
        
        // failcheck
        if (mapEntity == null) { return null; }
        
        // try create the game app (with mapid parameter)
        GameAppManager manager = context.getManager();
        
        // failcheck
        if (manager == null || !manager.canCreateGameApp("MapShard")) { return null; }
        
        // create the bean
        MapShardBean mapShard = new MapShardBean(mapEntity, instanceNumber, region, language);
        
        // register it
        Handle<MapShardBean> mapShardHandle = mapShardRegisty.produce(mapShard);
        
        // create the gameapp
        HashMap<String,String> params = new HashMap<>();
        params.put("MapId", String.valueOf(mapEntity.getId()));
        params.put("UniqueId", String.valueOf(mapShardHandle.getUid().toString()));
        
        GameAppContext.Remote mapShardContext = manager.createGameApp("MapShard", (GameAppContext.Remote)context, params);
        
        // failcheck
        if (mapShardContext == null) { mapShardHandle.invalidate(); return null; }
        
        // set the context and return it
        mapShard.setMapShardContext(mapShardContext);
        
        return mapShardHandle;
    }
}
