
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P053_SkillEffect
    extends GenericAction
{

    private long caster;
    private long target;
    private int skillID;
    private long unknown1;
    private long buffID;

    public short getHeader() {
        return  53;
    }

    public void setCaster(long caster) {
        this.caster = caster;
    }

    public void setTarget(long target) {
        this.target = target;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public void setUnknown1(long unknown1) {
        this.unknown1 = unknown1;
    }

    public void setBuffID(long buffID) {
        this.buffID = buffID;
    }

}
