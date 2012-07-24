/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P379_CharacterCreateHeadAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills a StartCharacterCreation action
 * 
 * @author miracle444
 */
public class CharacterCreateHeadView
{

    public static P379_CharacterCreateHeadAction create(Session session)
    {
        P379_CharacterCreateHeadAction startCharacterCreation = new P379_CharacterCreateHeadAction();
        startCharacterCreation.init(session);
        return startCharacterCreation;
    }
}
