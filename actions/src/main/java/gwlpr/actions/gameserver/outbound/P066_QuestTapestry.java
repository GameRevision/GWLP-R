
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P066_QuestTapestry
    extends GenericAction
{

    private long experienceEarned;
    private long goldEarned;
    private long skillPointsEarned;

    public short getHeader() {
        return  66;
    }

    public void setExperienceEarned(long experienceEarned) {
        this.experienceEarned = experienceEarned;
    }

    public void setGoldEarned(long goldEarned) {
        this.goldEarned = goldEarned;
    }

    public void setSkillPointsEarned(long skillPointsEarned) {
        this.skillPointsEarned = skillPointsEarned;
    }

}
