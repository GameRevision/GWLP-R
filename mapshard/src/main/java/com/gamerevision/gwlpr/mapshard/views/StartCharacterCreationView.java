/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P379_StartCharacterCreationAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills a StartCharacterCreation action
 * 
 * @author miracle444
 */
public class StartCharacterCreationView
{

    public static P379_StartCharacterCreationAction create(Session session)
    {
        P379_StartCharacterCreationAction startCharacterCreation = new P379_StartCharacterCreationAction();
        startCharacterCreation.init(session);
        return startCharacterCreation;
    }
}
