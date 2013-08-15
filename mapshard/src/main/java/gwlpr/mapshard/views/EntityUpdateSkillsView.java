/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.protocol.gameserver.outbound.P206_UpdateSkillBar;
import gwlpr.protocol.gameserver.outbound.P207_AvailableSkills;
import io.netty.channel.Channel;
import java.util.List;


/**
 * Update an agents skillbar
 * 
 * @author _rusty
 */
public class EntityUpdateSkillsView 
{
    
    public static void updateAvailableSkills(Channel channel, Entity entity)
    {
        // retrieve the entity related data we need...
        Skills skills = entity.get(Skills.class);
        
        // highest theoretical skill id is 3405
        // nearest whole byte count is 51 = 3408 bit
        // nearest whole int count is 107 = 438 byte = 3424
        
        P207_AvailableSkills updateAvailableSkills = new P207_AvailableSkills();
        updateAvailableSkills.init(channel);
        // static data: new int[] {0x000B0000, 0x0354FFFF, 0x043A043B, 0x00E8043A, 0x00000000, 0x00000000, 0x17000000}
        updateAvailableSkills.setSkillsBitfield(getSkillsBitfield(skills.availableSkills));

        channel.writeAndFlush(updateAvailableSkills);
        
    }
    
    public static void updateSkillbar(Channel channel, Entity entity)
    {
        // retrieve the entity related data we need...
        int agentID = entity.get(AgentIdentifiers.class).agentID;
        Skills skills = entity.get(Skills.class);
        
        // prepare the skill data
        P206_UpdateSkillBar.NestedSkillBar[] skillBar = new P206_UpdateSkillBar.NestedSkillBar[8];
        for (int i = 0; i < 8; i++) 
        {
            skillBar[i] = new P206_UpdateSkillBar.NestedSkillBar();
            skillBar[i].setUnknown1(0);
            
            if (skills.skillbar != null && (i < skills.skillbar.size()))
            {
                skillBar[i].setUnknown1(skills.skillbar.get(i));
            }
        }
        
        // TODO: the mask is instance specific... how is it implemented in the database? :P
        P206_UpdateSkillBar.NestedSkillBarPvPMask[] mask = new P206_UpdateSkillBar.NestedSkillBarPvPMask[8];
        for (int i = 0; i < 8; i++) 
        {
            mask[i] = new P206_UpdateSkillBar.NestedSkillBarPvPMask();
            mask[i].setUnknown1(0);
            
            if (skills.pvpmask != null && i < skills.pvpmask.size())
            {
                mask[i].setUnknown1(skills.pvpmask.get(i));
            }
        }
        
        
        P206_UpdateSkillBar updateSkillbar = new P206_UpdateSkillBar();
        updateSkillbar.init(channel);
        updateSkillbar.setAgentID(agentID);
        updateSkillbar.setSkillBar(skillBar);
        updateSkillbar.setSkillBarPvPMask(mask);
        updateSkillbar.setUnknown1((byte) 1);

        channel.writeAndFlush(updateSkillbar);
    }
    
    
    private static P207_AvailableSkills.NestedSkillsBitfield[] getSkillsBitfield(List<Short> skillIds)
    {
        int[] maxSkillsBits = new int[107];
        
        // first fill the array with bits
        for (Short skillId : skillIds) 
        {
            // get the integer we need to change
            int posInArray = skillId / 32;
            int posInInt = skillId % 32;
            
            maxSkillsBits[posInArray] |= 1 << posInInt;
        }
        
        // then determine the minimum length
        int minLen = maxSkillsBits.length; 
        for (int i = maxSkillsBits.length-1; i >= 0; i--) 
        {
            minLen = i+1;
            
            if (maxSkillsBits[i] != 0)
            {
                break;
            }
        }
        
        // finally create the array we can send to the client
        P207_AvailableSkills.NestedSkillsBitfield[] nested = new P207_AvailableSkills.NestedSkillsBitfield[minLen];
        for (int i = 0; i < nested.length; i++) 
        {
            nested[i] = new P207_AvailableSkills.NestedSkillsBitfield();
            nested[i].setUnknown1(maxSkillsBits[i]);            
        }
        
        return nested;
    }
}
