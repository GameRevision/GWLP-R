/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P380_CreateCharacterAckAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills a CreateCharacterAck action
 * 
 * @author miracle444
 */
public class CreateCharacterAckView
{

    public static P380_CreateCharacterAckAction create(Session session)
    {
        P380_CreateCharacterAckAction createCharacterAck = new P380_CreateCharacterAckAction();
        createCharacterAck.init(session);
        return createCharacterAck;
    }
}
