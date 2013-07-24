/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.serialization;

import gwlpr.actions.gameserver.ctos.P136_UnknownAction;
import gwlpr.actions.gameserver.ctos.P176_UnknownAction;
import gwlpr.actions.gameserver.ctos.P141_UnknownAction;
import gwlpr.actions.gameserver.ctos.P109_UnknownAction;
import gwlpr.actions.gameserver.ctos.P089_UnknownAction;
import gwlpr.actions.gameserver.ctos.P005_UnknownAction;
import gwlpr.actions.gameserver.ctos.P139_UnknownAction;
import gwlpr.actions.gameserver.ctos.P052_PressDialogButtonAction;
import gwlpr.actions.gameserver.ctos.P030_UnknownAction;
import gwlpr.actions.gameserver.ctos.P094_UnknownAction;
import gwlpr.actions.gameserver.ctos.P132_UnknownAction;
import gwlpr.actions.gameserver.ctos.P082_UnknownAction;
import gwlpr.actions.gameserver.ctos.P171_UnknownAction;
import gwlpr.actions.gameserver.ctos.P000_UnknownAction;
import gwlpr.actions.gameserver.ctos.P102_UnknownAction;
import gwlpr.actions.gameserver.ctos.P116_UnknownAction;
import gwlpr.actions.gameserver.ctos.P088_CharacterCreateUpdateProfessionAndCampaignAction;
import gwlpr.actions.gameserver.ctos.P010_UnknownAction;
import gwlpr.actions.gameserver.ctos.P054_UnknownAction;
import gwlpr.actions.gameserver.ctos.P189_UnknownAction;
import gwlpr.actions.gameserver.ctos.P158_UnknownAction;
import gwlpr.actions.gameserver.ctos.P090_UnknownAction;
import gwlpr.actions.gameserver.ctos.P190_UnknownAction;
import gwlpr.actions.gameserver.ctos.P033_UnknownAction;
import gwlpr.actions.gameserver.ctos.P053_UnknownAction;
import gwlpr.actions.gameserver.ctos.P047_UnknownAction;
import gwlpr.actions.gameserver.ctos.P015_UnknownAction;
import gwlpr.actions.gameserver.ctos.P120_UnknownAction;
import gwlpr.actions.gameserver.ctos.P021_UnknownAction;
import gwlpr.actions.gameserver.ctos.P058_ChangeSecondProfessionAction;
import gwlpr.actions.gameserver.ctos.P128_UnknownAction;
import gwlpr.actions.gameserver.ctos.P172_UnknownAction;
import gwlpr.actions.gameserver.ctos.P022_UnknownAction;
import gwlpr.actions.gameserver.ctos.P1280_VerifyClientAction;
import gwlpr.actions.gameserver.ctos.P179_UnknownAction;
import gwlpr.actions.gameserver.ctos.P113_UnknownAction;
import gwlpr.actions.gameserver.ctos.P063_UnknownAction;
import gwlpr.actions.gameserver.ctos.P038_UnknownAction;
import gwlpr.actions.gameserver.ctos.P045_UnknownAction;
import gwlpr.actions.gameserver.ctos.P108_UnknownAction;
import gwlpr.actions.gameserver.ctos.P080_UnknownAction;
import gwlpr.actions.gameserver.ctos.P115_UnknownAction;
import gwlpr.actions.gameserver.ctos.P167_UnknownAction;
import gwlpr.actions.gameserver.ctos.P143_UnknownAction;
import gwlpr.actions.gameserver.ctos.P001_UnknownAction;
import gwlpr.actions.gameserver.ctos.P040_UnknownAction;
import gwlpr.actions.gameserver.ctos.P100_UnknownAction;
import gwlpr.actions.gameserver.ctos.P048_UnknownAction;
import gwlpr.actions.gameserver.ctos.P084_ReplaceSkillAction;
import gwlpr.actions.gameserver.ctos.P106_UnknownAction;
import gwlpr.actions.gameserver.ctos.P029_UnknownAction;
import gwlpr.actions.gameserver.ctos.P085_UnknownAction;
import gwlpr.actions.gameserver.ctos.P018_UnknownAction;
import gwlpr.actions.gameserver.ctos.P051_UnknownAction;
import gwlpr.actions.gameserver.ctos.P081_UnknownAction;
import gwlpr.actions.gameserver.ctos.P185_UnknownAction;
import gwlpr.actions.gameserver.ctos.P039_UnknownAction;
import gwlpr.actions.gameserver.ctos.P049_UnknownAction;
import gwlpr.actions.gameserver.ctos.P101_UnknownAction;
import gwlpr.actions.gameserver.ctos.P188_UnknownAction;
import gwlpr.actions.gameserver.ctos.P127_UnknownAction;
import gwlpr.actions.gameserver.ctos.P011_UnknownAction;
import gwlpr.actions.gameserver.ctos.P056_UnknownAction;
import gwlpr.actions.gameserver.ctos.P014_UnknownAction;
import gwlpr.actions.gameserver.ctos.P155_UnknownAction;
import gwlpr.actions.gameserver.ctos.P145_UnknownAction;
import gwlpr.actions.gameserver.ctos.P012_UnknownAction;
import gwlpr.actions.gameserver.ctos.P098_UnknownAction;
import gwlpr.actions.gameserver.ctos.P173_UnknownAction;
import gwlpr.actions.gameserver.ctos.P034_UnknownAction;
import gwlpr.actions.gameserver.ctos.P121_UnknownAction;
import gwlpr.actions.gameserver.ctos.P079_UnknownAction;
import gwlpr.actions.gameserver.ctos.P181_UnknownAction;
import gwlpr.actions.gameserver.ctos.P180_UnknownAction;
import gwlpr.actions.gameserver.ctos.P003_UnknownAction;
import gwlpr.actions.gameserver.ctos.P122_UnknownAction;
import gwlpr.actions.gameserver.ctos.P140_UnknownAction;
import gwlpr.actions.gameserver.ctos.P134_UnknownAction;
import gwlpr.actions.gameserver.ctos.P009_UnknownAction;
import gwlpr.actions.gameserver.ctos.P168_UnknownAction;
import gwlpr.actions.gameserver.ctos.P142_UnknownAction;
import gwlpr.actions.gameserver.ctos.P125_UnknownAction;
import gwlpr.actions.gameserver.ctos.P174_UnknownAction;
import gwlpr.actions.gameserver.ctos.P129_UnknownAction;
import gwlpr.actions.gameserver.ctos.P083_UnknownAction;
import gwlpr.actions.gameserver.ctos.P144_UnknownAction;
import gwlpr.actions.gameserver.ctos.P166_UnknownAction;
import gwlpr.actions.gameserver.ctos.P095_UnknownAction;
import gwlpr.actions.gameserver.ctos.P114_UnknownAction;
import gwlpr.actions.gameserver.ctos.P184_UnknownAction;
import gwlpr.actions.gameserver.ctos.P148_UnknownAction;
import gwlpr.actions.gameserver.ctos.P060_UnknownAction;
import gwlpr.actions.gameserver.ctos.P004_UnknownAction;
import gwlpr.actions.gameserver.ctos.P16896_ClientSeedAction;
import gwlpr.actions.gameserver.ctos.P177_UnknownAction;
import gwlpr.actions.gameserver.ctos.P023_UnknownAction;
import gwlpr.actions.gameserver.ctos.P111_UnknownAction;
import gwlpr.actions.gameserver.ctos.P055_UnknownAction;
import gwlpr.actions.gameserver.ctos.P073_UnknownAction;
import gwlpr.actions.gameserver.ctos.P119_UnknownAction;
import gwlpr.actions.gameserver.ctos.P147_UnknownAction;
import gwlpr.actions.gameserver.ctos.P157_UnknownAction;
import gwlpr.actions.gameserver.ctos.P044_UnknownAction;
import gwlpr.actions.gameserver.ctos.P037_UnknownAction;
import gwlpr.actions.gameserver.ctos.P057_UnknownAction;
import gwlpr.actions.gameserver.ctos.P024_UnknownAction;
import gwlpr.actions.gameserver.ctos.P070_UnknownAction;
import gwlpr.actions.gameserver.ctos.P107_UnknownAction;
import gwlpr.actions.gameserver.ctos.P124_UnknownAction;
import gwlpr.actions.gameserver.ctos.P126_UnknownAction;
import gwlpr.actions.gameserver.ctos.P133_UnknownAction;
import gwlpr.actions.gameserver.ctos.P013_UnknownAction;
import gwlpr.actions.gameserver.ctos.P066_UnknownAction;
import gwlpr.actions.gameserver.ctos.P076_UnknownAction;
import gwlpr.actions.gameserver.ctos.P078_UnknownAction;
import gwlpr.actions.gameserver.ctos.P156_UnknownAction;
import gwlpr.actions.gameserver.ctos.P050_UnknownAction;
import gwlpr.actions.gameserver.ctos.P007_UnknownAction;
import gwlpr.actions.gameserver.ctos.P154_UnknownAction;
import gwlpr.actions.gameserver.ctos.P069_UnknownAction;
import gwlpr.actions.gameserver.ctos.P008_UnknownAction;
import gwlpr.actions.gameserver.ctos.P086_SwapSkillsOnBarAction;
import gwlpr.actions.gameserver.ctos.P137_UnknownAction;
import gwlpr.actions.gameserver.ctos.P072_UnknownAction;
import gwlpr.actions.gameserver.ctos.P118_UnknownAction;
import gwlpr.actions.gameserver.ctos.P099_UnknownAction;
import gwlpr.actions.gameserver.ctos.P159_UnknownAction;
import gwlpr.actions.gameserver.ctos.P097_UnknownAction;
import gwlpr.actions.gameserver.ctos.P163_UnknownAction;
import gwlpr.actions.gameserver.ctos.P175_UnknownAction;
import gwlpr.actions.gameserver.ctos.P162_UnknownAction;
import gwlpr.actions.gameserver.ctos.P043_UnknownAction;
import gwlpr.actions.gameserver.ctos.P169_UnknownAction;
import gwlpr.actions.gameserver.ctos.P146_UnknownAction;
import gwlpr.actions.gameserver.ctos.P026_UnknownAction;
import gwlpr.actions.gameserver.ctos.P186_UnknownAction;
import gwlpr.actions.gameserver.ctos.P032_UnknownAction;
import gwlpr.actions.gameserver.ctos.P135_UnknownAction;
import gwlpr.actions.gameserver.ctos.P071_UnknownAction;
import gwlpr.actions.gameserver.ctos.P178_UnknownAction;
import gwlpr.actions.gameserver.ctos.P064_UnknownAction;
import gwlpr.actions.gameserver.ctos.P074_UnknownAction;
import gwlpr.actions.gameserver.ctos.P019_UnknownAction;
import gwlpr.actions.gameserver.ctos.P103_UnknownAction;
import gwlpr.actions.gameserver.ctos.P149_UnknownAction;
import gwlpr.actions.gameserver.ctos.P093_UnknownAction;
import gwlpr.actions.gameserver.ctos.P096_UnknownAction;
import gwlpr.actions.gameserver.ctos.P191_UnknownAction;
import gwlpr.actions.gameserver.ctos.P046_UnknownAction;
import gwlpr.actions.gameserver.ctos.P061_UnknownAction;
import gwlpr.actions.gameserver.ctos.P117_UnknownAction;
import gwlpr.actions.gameserver.ctos.P016_UnknownAction;
import gwlpr.actions.gameserver.ctos.P062_UnknownAction;
import gwlpr.actions.gameserver.ctos.P105_UnknownAction;
import gwlpr.actions.gameserver.ctos.P104_UnknownAction;
import gwlpr.actions.gameserver.ctos.P075_UnknownAction;
import gwlpr.actions.gameserver.ctos.P164_UnknownAction;
import gwlpr.actions.gameserver.ctos.P041_UnknownAction;
import gwlpr.actions.gameserver.ctos.P020_UnknownAction;
import gwlpr.actions.gameserver.ctos.P025_UnknownAction;
import gwlpr.actions.gameserver.ctos.P006_UnknownAction;
import gwlpr.actions.gameserver.ctos.P059_UnknownAction;
import gwlpr.actions.gameserver.ctos.P151_UnknownAction;
import gwlpr.actions.gameserver.ctos.P068_UnknownAction;
import gwlpr.actions.gameserver.ctos.P027_UnknownAction;
import gwlpr.actions.gameserver.ctos.P017_UnknownAction;
import gwlpr.actions.gameserver.ctos.P028_UnknownAction;
import gwlpr.actions.gameserver.ctos.P131_UnknownAction;
import gwlpr.actions.gameserver.ctos.P087_UnknownAction;
import gwlpr.actions.gameserver.ctos.P110_UnknownAction;
import gwlpr.actions.gameserver.ctos.P130_CreateNewCharacterAction;
import gwlpr.actions.gameserver.ctos.P077_UnknownAction;
import gwlpr.actions.gameserver.ctos.P031_UnknownAction;
import gwlpr.actions.gameserver.ctos.P002_UnknownAction;
import gwlpr.actions.gameserver.ctos.P153_UnknownAction;
import gwlpr.actions.gameserver.ctos.P183_UnknownAction;
import gwlpr.actions.gameserver.ctos.P161_UnknownAction;
import gwlpr.actions.gameserver.ctos.P138_UnknownAction;
import gwlpr.actions.gameserver.ctos.P192_UnknownAction;
import gwlpr.actions.gameserver.ctos.P042_UnknownAction;
import gwlpr.actions.gameserver.ctos.P067_UnknownAction;
import gwlpr.actions.gameserver.ctos.P150_UnknownAction;
import gwlpr.actions.gameserver.ctos.P182_UnknownAction;
import gwlpr.actions.gameserver.ctos.P036_UnknownAction;
import gwlpr.actions.gameserver.ctos.P187_UnknownAction;
import gwlpr.actions.gameserver.ctos.P165_UnknownAction;
import gwlpr.actions.gameserver.ctos.P160_UnknownAction;
import gwlpr.actions.gameserver.ctos.P035_UnknownAction;
import gwlpr.actions.gameserver.ctos.P091_UnknownAction;
import gwlpr.actions.gameserver.ctos.P170_UnknownAction;
import gwlpr.actions.gameserver.ctos.P065_UnknownAction;
import gwlpr.actions.gameserver.ctos.P152_UnknownAction;
import gwlpr.actions.gameserver.ctos.P123_UnknownAction;
import gwlpr.actions.gameserver.ctos.P092_UnknownAction;
import gwlpr.actions.gameserver.ctos.P112_UnknownAction;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.TriggerableAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Part of the deserialization procedure.
 * Creates concrete actions and manages remaining data for GameServer packets.
 * 
 * @author miracle444
 */
public class GameServerDeserializer extends Deserializer
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(GameServerDeserializer.class);
    
    
    /**
     * Constructor.
     * 
     * @param   session     the session this deserializer belongs to.
     */
    public GameServerDeserializer(Session session)
    {
        super(session);
    }    
    
    
    /**
     * Realizes a header->action mapping.
     * partially automated by the PacketCodeGen.
     * 
     * @param   header      the header to retrieve an action type from
     * @return              a new instance of an action type corresponding to the header
     */
    @Override
    protected TriggerableAction createAction(short header)
    {
        switch (header)
        {
            case 0:
                return new P000_UnknownAction();
            case 1:
                return new P001_UnknownAction();
            case 2:
                return new P002_UnknownAction();
            case 3:
                return new P003_UnknownAction();
            case 4:
                return new P004_UnknownAction();
            case 5:
                return new P005_UnknownAction();
            case 6:
                return new P006_UnknownAction();
            case 7:
                return new P007_UnknownAction();
            case 8:
                return new P008_UnknownAction();
            case 9:
                return new P009_UnknownAction();
            case 10:
                return new P010_UnknownAction();
            case 11:
                return new P011_UnknownAction();
            case 12:
                return new P012_UnknownAction();
            case 13:
                return new P013_UnknownAction();
            case 14:
                return new P014_UnknownAction();
            case 15:
                return new P015_UnknownAction();
            case 16:
                return new P016_UnknownAction();
            case 17:
                return new P017_UnknownAction();
            case 18:
                return new P018_UnknownAction();
            case 19:
                return new P019_UnknownAction();
            case 20:
                return new P020_UnknownAction();
            case 21:
                return new P021_UnknownAction();
            case 22:
                return new P022_UnknownAction();
            case 23:
                return new P023_UnknownAction();
            case 24:
                return new P024_UnknownAction();
            case 25:
                return new P025_UnknownAction();
            case 26:
                return new P026_UnknownAction();
            case 27:
                return new P027_UnknownAction();
            case 28:
                return new P028_UnknownAction();
            case 29:
                return new P029_UnknownAction();
            case 30:
                return new P030_UnknownAction();
            case 31:
                return new P031_UnknownAction();
            case 32:
                return new P032_UnknownAction();
            case 33:
                return new P033_UnknownAction();
            case 34:
                return new P034_UnknownAction();
            case 35:
                return new P035_UnknownAction();
            case 36:
                return new P036_UnknownAction();
            case 37:
                return new P037_UnknownAction();
            case 38:
                return new P038_UnknownAction();
            case 39:
                return new P039_UnknownAction();
            case 40:
                return new P040_UnknownAction();
            case 41:
                return new P041_UnknownAction();
            case 42:
                return new P042_UnknownAction();
            case 43:
                return new P043_UnknownAction();
            case 44:
                return new P044_UnknownAction();
            case 45:
                return new P045_UnknownAction();
            case 46:
                return new P046_UnknownAction();
            case 47:
                return new P047_UnknownAction();
            case 48:
                return new P048_UnknownAction();
            case 49:
                return new P049_UnknownAction();
            case 50:
                return new P050_UnknownAction();
            case 51:
                return new P051_UnknownAction();
            case 52:
                return new P052_PressDialogButtonAction();
            case 53:
                return new P053_UnknownAction();
            case 54:
                return new P054_UnknownAction();
            case 55:
                return new P055_UnknownAction();
            case 56:
                return new P056_UnknownAction();
            case 57:
                return new P057_UnknownAction();
            case 58:
                return new P058_ChangeSecondProfessionAction();
            case 59:
                return new P059_UnknownAction();
            case 60:
                return new P060_UnknownAction();
            case 61:
                return new P061_UnknownAction();
            case 62:
                return new P062_UnknownAction();
            case 63:
                return new P063_UnknownAction();
            case 64:
                return new P064_UnknownAction();
            case 65:
                return new P065_UnknownAction();
            case 66:
                return new P066_UnknownAction();
            case 67:
                return new P067_UnknownAction();
            case 68:
                return new P068_UnknownAction();
            case 69:
                return new P069_UnknownAction();
            case 70:
                return new P070_UnknownAction();
            case 71:
                return new P071_UnknownAction();
            case 72:
                return new P072_UnknownAction();
            case 73:
                return new P073_UnknownAction();
            case 74:
                return new P074_UnknownAction();
            case 75:
                return new P075_UnknownAction();
            case 76:
                return new P076_UnknownAction();
            case 77:
                return new P077_UnknownAction();
            case 78:
                return new P078_UnknownAction();
            case 79:
                return new P079_UnknownAction();
            case 80:
                return new P080_UnknownAction();
            case 81:
                return new P081_UnknownAction();
            case 82:
                return new P082_UnknownAction();
            case 83:
                return new P083_UnknownAction();
            case 84:
                return new P084_ReplaceSkillAction();
            case 85:
                return new P085_UnknownAction();
            case 86:
                return new P086_SwapSkillsOnBarAction();
            case 87:
                return new P087_UnknownAction();
            case 88:
                return new P088_CharacterCreateUpdateProfessionAndCampaignAction();
            case 89:
                return new P089_UnknownAction();
            case 90:
                return new P090_UnknownAction();
            case 91:
                return new P091_UnknownAction();
            case 92:
                return new P092_UnknownAction();
            case 93:
                return new P093_UnknownAction();
            case 94:
                return new P094_UnknownAction();
            case 95:
                return new P095_UnknownAction();
            case 96:
                return new P096_UnknownAction();
            case 97:
                return new P097_UnknownAction();
            case 98:
                return new P098_UnknownAction();
            case 99:
                return new P099_UnknownAction();
            case 100:
                return new P100_UnknownAction();
            case 101:
                return new P101_UnknownAction();
            case 102:
                return new P102_UnknownAction();
            case 103:
                return new P103_UnknownAction();
            case 104:
                return new P104_UnknownAction();
            case 105:
                return new P105_UnknownAction();
            case 106:
                return new P106_UnknownAction();
            case 107:
                return new P107_UnknownAction();
            case 108:
                return new P108_UnknownAction();
            case 109:
                return new P109_UnknownAction();
            case 110:
                return new P110_UnknownAction();
            case 111:
                return new P111_UnknownAction();
            case 112:
                return new P112_UnknownAction();
            case 113:
                return new P113_UnknownAction();
            case 114:
                return new P114_UnknownAction();
            case 115:
                return new P115_UnknownAction();
            case 116:
                return new P116_UnknownAction();
            case 117:
                return new P117_UnknownAction();
            case 118:
                return new P118_UnknownAction();
            case 119:
                return new P119_UnknownAction();
            case 120:
                return new P120_UnknownAction();
            case 121:
                return new P121_UnknownAction();
            case 122:
                return new P122_UnknownAction();
            case 123:
                return new P123_UnknownAction();
            case 124:
                return new P124_UnknownAction();
            case 125:
                return new P125_UnknownAction();
            case 126:
                return new P126_UnknownAction();
            case 127:
                return new P127_UnknownAction();
            case 128:
                return new P128_UnknownAction();
            case 129:
                return new P129_UnknownAction();
            case 130:
                return new P130_CreateNewCharacterAction();
            case 131:
                return new P131_UnknownAction();
            case 132:
                return new P132_UnknownAction();
            case 133:
                return new P133_UnknownAction();
            case 134:
                return new P134_UnknownAction();
            case 135:
                return new P135_UnknownAction();
            case 136:
                return new P136_UnknownAction();
            case 137:
                return new P137_UnknownAction();
            case 138:
                return new P138_UnknownAction();
            case 139:
                return new P139_UnknownAction();
            case 140:
                return new P140_UnknownAction();
            case 141:
                return new P141_UnknownAction();
            case 142:
                return new P142_UnknownAction();
            case 143:
                return new P143_UnknownAction();
            case 144:
                return new P144_UnknownAction();
            case 145:
                return new P145_UnknownAction();
            case 146:
                return new P146_UnknownAction();
            case 147:
                return new P147_UnknownAction();
            case 148:
                return new P148_UnknownAction();
            case 149:
                return new P149_UnknownAction();
            case 150:
                return new P150_UnknownAction();
            case 151:
                return new P151_UnknownAction();
            case 152:
                return new P152_UnknownAction();
            case 153:
                return new P153_UnknownAction();
            case 154:
                return new P154_UnknownAction();
            case 155:
                return new P155_UnknownAction();
            case 156:
                return new P156_UnknownAction();
            case 157:
                return new P157_UnknownAction();
            case 158:
                return new P158_UnknownAction();
            case 159:
                return new P159_UnknownAction();
            case 160:
                return new P160_UnknownAction();
            case 161:
                return new P161_UnknownAction();
            case 162:
                return new P162_UnknownAction();
            case 163:
                return new P163_UnknownAction();
            case 164:
                return new P164_UnknownAction();
            case 165:
                return new P165_UnknownAction();
            case 166:
                return new P166_UnknownAction();
            case 167:
                return new P167_UnknownAction();
            case 168:
                return new P168_UnknownAction();
            case 169:
                return new P169_UnknownAction();
            case 170:
                return new P170_UnknownAction();
            case 171:
                return new P171_UnknownAction();
            case 172:
                return new P172_UnknownAction();
            case 173:
                return new P173_UnknownAction();
            case 174:
                return new P174_UnknownAction();
            case 175:
                return new P175_UnknownAction();
            case 176:
                return new P176_UnknownAction();
            case 177:
                return new P177_UnknownAction();
            case 178:
                return new P178_UnknownAction();
            case 179:
                return new P179_UnknownAction();
            case 180:
                return new P180_UnknownAction();
            case 181:
                return new P181_UnknownAction();
            case 182:
                return new P182_UnknownAction();
            case 183:
                return new P183_UnknownAction();
            case 184:
                return new P184_UnknownAction();
            case 185:
                return new P185_UnknownAction();
            case 186:
                return new P186_UnknownAction();
            case 187:
                return new P187_UnknownAction();
            case 188:
                return new P188_UnknownAction();
            case 189:
                return new P189_UnknownAction();
            case 190:
                return new P190_UnknownAction();
            case 191:
                return new P191_UnknownAction();
            case 192:
                return new P192_UnknownAction();
            case 1280:
                return new P1280_VerifyClientAction();
            case 16896:
                return new P16896_ClientSeedAction();
            default:
                LOGGER.warn("Could not deserialize unknown header: {}", header);
                return null;
        }
    }
}
