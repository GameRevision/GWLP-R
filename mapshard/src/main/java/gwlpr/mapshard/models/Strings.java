/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models;


/**
 * Use this to gather and store all constant strings used for messages
 * that are intended to be send to the client.
 *
 * @author _rusty
 */
public enum Strings
{
    CmdNotAvail ("The command does not exist or you dont have sufficient permissions to execute it.");

    private String text;
    private Strings(String text) { this.text = text; }
    public String text() { return text; }
}
