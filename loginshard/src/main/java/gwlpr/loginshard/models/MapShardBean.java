/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.models;

import gwlpr.database.entities.Map;
import gwlpr.protocol.intershard.utils.DistrictLanguage;
import gwlpr.protocol.intershard.utils.DistrictRegion;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.util.Handle;


/**
 * This bean represents the data of a remote mapshard.
 * 
 * @author _rusty
 */
public final class MapShardBean 
{
    
    private Handle<GameAppContext> mapShardContext;
    
    private final List<Handle<ClientBean>> waitingClients = new CopyOnWriteArrayList<>();
    private final List<Handle<ClientBean>> pendingClients = new CopyOnWriteArrayList<>();
    private final List<Handle<ClientBean>> connectedClients = new CopyOnWriteArrayList<>();
    
    private final Map map;
    private final int instanceNumber;
    private final DistrictRegion region;
    private final DistrictLanguage language;
    
    
    public MapShardBean(Handle<GameAppContext> mapShardContext, Map mapEntity, int instance, DistrictRegion region, DistrictLanguage language)
    {
        this.mapShardContext = mapShardContext;
        this.map = mapEntity;
        this.instanceNumber = instance;
        this.region = region;
        this.language = language;
    }

    
    public Handle<GameAppContext> getMapShardContext() 
    {
        return mapShardContext;
    }

    
    public List<Handle<ClientBean>> getWaitingClients() 
    {
        return waitingClients;
    }

    
    public List<Handle<ClientBean>> getPendingClients() 
    {
        return pendingClients;
    }

    
    public List<Handle<ClientBean>> getConnectedClients() 
    {
        return connectedClients;
    }

    
    public Map getMap() 
    {
        return map;
    }

    
    public int getInstanceNumber() 
    {
        return instanceNumber;
    }

    
    public DistrictRegion getRegion() 
    {
        return region;
    }

    
    public DistrictLanguage getLanguage() 
    {
        return language;
    }
}
