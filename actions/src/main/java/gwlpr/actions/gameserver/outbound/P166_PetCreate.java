
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P166_PetCreate
    extends GenericAction
{

    private long pet;
    private long owner;
    private String name;
    private long nPCFile;
    private long modelFile;
    private long mode;

    public short getHeader() {
        return  166;
    }

    public void setPet(long pet) {
        this.pet = pet;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNPCFile(long nPCFile) {
        this.nPCFile = nPCFile;
    }

    public void setModelFile(long modelFile) {
        this.modelFile = modelFile;
    }

    public void setMode(long mode) {
        this.mode = mode;
    }

}
