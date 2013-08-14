/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models.enums;


/**
 * These are all known chat channels
 *
 * @author _rusty
 */
public enum ChatChannel
{
    All ('!'),
    Team ('#'),
    Trade ('$'),
    Guild ('@'),
    Alliance ('%'),
    Whisper ('"'),
    Command ('/');

    private final char prefix;

    ChatChannel(char prefix)
    {
        this.prefix = prefix;
    }


    /**
     * Getter.
     *
     * @return      The prefix used with this chat channel.
     */
    public char getPrefix()
    {
        return prefix;
    }


    /**
     * Try to find a chat channel for a given prefix.
     *
     * @param       pref                    The prefix
     * @return      The channel or null.
     */
    public static ChatChannel getFromPrefix(char pref)
    {
        for (ChatChannel chatChannel : ChatChannel.values())
        {
            if (chatChannel.getPrefix() == pref) { return chatChannel; }
        }

        // the prefix doesnt match our channels:
        return null;
    }
}
