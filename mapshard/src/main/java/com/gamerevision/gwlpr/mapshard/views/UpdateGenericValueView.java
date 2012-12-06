/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P147_UpdateGenericValueIntAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P148_UpdateGenericValueTargetAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P150_UpdateGenericValueFloatAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P151_UpdateGenericValueModifierAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills the UpdateGenericValue action.
 * 
 * This is used to send different packets, depending on the input.
 * See the nested enum for the known values
 * 
 * @author miracle444, _rusty
 */
public class UpdateGenericValueView
{

    /**
     * This enum is used to store the different generic values that are used
     * within the GW1 protocol
     * 
     * TODO: This enum should be kept in sync with the wiki!
     */
    public enum Type
    {
        // 0
        Appearance,
        Unknown1,                // TODO: Generic Value missing!
        MeleeAttack,
        MeleeSkillAttack1,
        Attack,
        
        // 5
        Unknown2,                // TODO: Generic Value missing!
        ApplyAura,
        RemoveAura,
        FreezePlayer,
        ShakeScreen,
        
        // 10
        SkillDamage,
        ApplyMarker,
        RemoveMarker,
        Unknown3,
        AddArmor,               // TODO: Generic Value missing!
        
        // 15
        ArmorColor,
        DamageModifier1,
        DamageModifier2,
        Unknown4,                // TODO: Generic Value missing!
        Unknown5,                // TODO: Generic Value missing!
        
        // 20
        ApplyEffect1,
        ApplyEffect2,
        ApplyAnimation,
        DivineAura,
        Unknown6,                // TODO: Generic Value missing!
        
        // 25
        ShowWings,
        ShowRank,
        ShowZaishenRank,
        ApplyAnimationLoop,
        BossGlow,
        
        // 30
        ApplyGuild1,
        ApplyGuild2,
        Unknown7,                // TODO: Generic Value missing!
        EnergyModifier1,
        HealthModifier1,
        
        // 35
        Knockdown1,
        PublicLevel,
        LevelUp,
        AttackFail,
        PickUpItem,
        
        // 40
        Unknown8,                // TODO: Generic Value missing!
        Energy,
        Health,
        EnergyRegen,
        HealthRegen,
        
        // 45
        Unknown9,                // TODO: Generic Value missing!
        MeleeSkillAttack2,
        Unknown10,               // TODO: Generic Value missing!
        Unknown11,               // TODO: Generic Value missing!
        InterruptAttack,
        
        // 50
        CastAttackSkill,
        Unknown12,               // TODO: Generic Value missing!
        EnergyModifier2,
        EnergyModifier3,
        EnergyVisual,
        
        // 55
        HealthModifier2,
        HealthModifier3,
        Unknown13,               // TODO: Generic Value missing!
        FightStance,
        InterruptSkill,
        
        // 60
        CastSkill,
        CastTimeModifier,
        EnergyModifier4,
        Knockdown2,
        Unknown14,               // TODO: Generic Value missing!
        
        // 65
        PvPTeam;        
    }
    
    public static void create(Session session, int agentID, Type valueID, int value)
    {
        P147_UpdateGenericValueIntAction updateGenericValueInt = new P147_UpdateGenericValueIntAction();
        updateGenericValueInt.init(session);
        updateGenericValueInt.setValueID(valueID.ordinal());
        updateGenericValueInt.setAgentID(agentID);
        updateGenericValueInt.setValue(value);
        
        session.send(updateGenericValueInt);
    }
    
    public static void create(Session session, int agentID, Type valueID, float value)
    {
        P150_UpdateGenericValueFloatAction updateGenericValueFloat = new P150_UpdateGenericValueFloatAction();
        updateGenericValueFloat.init(session);
        updateGenericValueFloat.setValueID(valueID.ordinal());
        updateGenericValueFloat.setAgentID(agentID);
        updateGenericValueFloat.setValue(value);
        
        session.send(updateGenericValueFloat);
    }
    
    public static void create(Session session, int targetAgentID, int casterAgentID, Type valueID, int value)
    {
        P148_UpdateGenericValueTargetAction updateGenericValueTarget = new P148_UpdateGenericValueTargetAction();
        updateGenericValueTarget.init(session);
        updateGenericValueTarget.setValueID(valueID.ordinal());
        updateGenericValueTarget.setTarget(targetAgentID);
        updateGenericValueTarget.setCaster(casterAgentID);
        updateGenericValueTarget.setValue(value);
        
        session.send(updateGenericValueTarget);
    }
    
    public static void create(Session session, int targetAgentID, int casterAgentID, Type valueID, float value)
    {
        P151_UpdateGenericValueModifierAction updateGenericValueModifier = new P151_UpdateGenericValueModifierAction();
        updateGenericValueModifier.init(session);
        updateGenericValueModifier.setValueID(valueID.ordinal());
        updateGenericValueModifier.setTarget(targetAgentID);
        updateGenericValueModifier.setCaster(casterAgentID);
        //updateGenericValueModifier.setValue(value); // TODO: fixme
        
        session.send(updateGenericValueModifier);
    }
}
