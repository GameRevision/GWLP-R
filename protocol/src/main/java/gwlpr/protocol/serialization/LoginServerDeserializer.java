/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.serialization;

import gwlpr.actions.loginserver.ctos.P024_UnknownAction;
import gwlpr.actions.loginserver.ctos.P015_UnknownAction;
import gwlpr.actions.loginserver.ctos.P019_UnknownAction;
import gwlpr.actions.loginserver.ctos.P009_UnknownAction;
import gwlpr.actions.loginserver.ctos.P022_UnknownAction;
import gwlpr.actions.loginserver.ctos.P035_UnknownAction;
import gwlpr.actions.loginserver.ctos.P021_UnknownAction;
import gwlpr.actions.loginserver.ctos.P045_UnknownAction;
import gwlpr.actions.loginserver.ctos.P023_UnknownAction;
import gwlpr.actions.loginserver.ctos.P013_UnknownAction;
import gwlpr.actions.loginserver.ctos.P004_AccountLoginAction;
import gwlpr.actions.loginserver.ctos.P036_UnknownAction;
import gwlpr.actions.loginserver.ctos.P006_UnknownAction;
import gwlpr.actions.loginserver.ctos.P005_UnknownAction;
import gwlpr.actions.loginserver.ctos.P039_UnknownAction;
import gwlpr.actions.loginserver.ctos.P016_UnknownAction;
import gwlpr.actions.loginserver.ctos.P032_UnknownAction;
import gwlpr.actions.loginserver.ctos.P026_UnknownAction;
import gwlpr.actions.loginserver.ctos.P020_UnknownAction;
import gwlpr.actions.loginserver.ctos.P001_ComputerUserAction;
import gwlpr.actions.loginserver.ctos.P033_UnknownAction;
import gwlpr.actions.loginserver.ctos.P046_UnknownAction;
import gwlpr.actions.loginserver.ctos.P049_UnknownAction;
import gwlpr.actions.loginserver.ctos.P029_UnknownAction;
import gwlpr.actions.loginserver.ctos.P041_CharacterPlayInfoAction;
import gwlpr.actions.loginserver.ctos.P034_UnknownAction;
import gwlpr.actions.loginserver.ctos.P017_UnknownAction;
import gwlpr.actions.loginserver.ctos.P012_UnknownAction;
import gwlpr.actions.loginserver.ctos.P042_UnknownAction;
import gwlpr.actions.loginserver.ctos.P018_UnknownAction;
import gwlpr.actions.loginserver.ctos.P040_UnknownAction;
import gwlpr.actions.loginserver.ctos.P014_UnknownAction;
import gwlpr.actions.loginserver.ctos.P048_UnknownAction;
import gwlpr.actions.loginserver.ctos.P025_UnknownAction;
import gwlpr.actions.loginserver.ctos.P008_UnknownAction;
import gwlpr.actions.loginserver.ctos.P054_UnknownAction;
import gwlpr.actions.loginserver.ctos.P037_UnknownAction;
import gwlpr.actions.loginserver.ctos.P047_UnknownAction;
import gwlpr.actions.loginserver.ctos.P030_UnknownAction;
import gwlpr.actions.loginserver.ctos.P043_UnknownAction;
import gwlpr.actions.loginserver.ctos.P011_UnknownAction;
import gwlpr.actions.loginserver.ctos.P000_UnknownAction;
import gwlpr.actions.loginserver.ctos.P027_UnknownAction;
import gwlpr.actions.loginserver.ctos.P002_UnknownAction;
import gwlpr.actions.loginserver.ctos.P028_UnknownAction;
import gwlpr.actions.loginserver.ctos.P051_UnknownAction;
import gwlpr.actions.loginserver.ctos.P007_UnknownAction;
import gwlpr.actions.loginserver.ctos.P052_UnknownAction;
import gwlpr.actions.loginserver.ctos.P010_UnknownAction;
import gwlpr.actions.loginserver.ctos.P038_UnknownAction;
import gwlpr.actions.loginserver.ctos.P055_UnknownAction;
import gwlpr.actions.loginserver.ctos.P1024_ClientVersionAction;
import gwlpr.actions.loginserver.ctos.P16896_ClientSeedAction;
import gwlpr.actions.loginserver.ctos.P053_RequestResponseAction;
import gwlpr.actions.loginserver.ctos.P044_UnknownAction;
import gwlpr.actions.loginserver.ctos.P003_UnknownAction;
import gwlpr.actions.loginserver.ctos.P050_UnknownAction;
import gwlpr.actions.loginserver.ctos.P031_UnknownAction;
import gwlpr.protocol.serialization.Deserializer;
import realityshard.shardlet.Session;
import realityshard.shardlet.TriggerableAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Part of the deserialisation procedure.
 * Creates concrete actions and manages remaining data for LoginServer packets.
 * 
 * @author miracle444
 */
public class LoginServerDeserializer extends Deserializer
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(LoginServerDeserializer.class);
    
    
    /**
     * Constructor.
     * 
     * @param   session     the session this deserializer belongs to.
     */
    public LoginServerDeserializer(Session session)
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
                return new P001_ComputerUserAction();
            case 2:
                return new P002_UnknownAction();
            case 3:
                return new P003_UnknownAction();
            case 4:
                return new P004_AccountLoginAction();
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
                return new P041_CharacterPlayInfoAction();
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
                return new P052_UnknownAction();
            case 53:
                return new P053_RequestResponseAction();
            case 54:
                return new P054_UnknownAction();
            case 55:
                return new P055_UnknownAction();
            case 1024:
                return new P1024_ClientVersionAction();
            case 16896:
                return new P16896_ClientSeedAction();
            default:
                LOGGER.warn("Could not deserialize unknown header: {}", header);
                return null;
        }
    }
}
