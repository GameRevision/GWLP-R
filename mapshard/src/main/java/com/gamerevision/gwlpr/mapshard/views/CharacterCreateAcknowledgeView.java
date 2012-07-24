/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P380_CharacterCreateAcknowledgeAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills a CreateCharacterAck action
 * 
 * @author miracle444
 */
public class CharacterCreateAcknowledgeView
{

    public static P380_CharacterCreateAcknowledgeAction create(Session session)
    {
        P380_CharacterCreateAcknowledgeAction createCharacterAck = new P380_CharacterCreateAcknowledgeAction();
        createCharacterAck.init(session);
        return createCharacterAck;
    }
}
