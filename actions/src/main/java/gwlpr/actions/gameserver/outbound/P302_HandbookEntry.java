
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P302_HandbookEntry
    extends GenericAction
{

    private long handbookID;
    private short page;
    private String pageName1;
    private String pageName2;
    private String pageText1;
    private String pageText2;

    public short getHeader() {
        return  302;
    }

    public void setHandbookID(long handbookID) {
        this.handbookID = handbookID;
    }

    public void setPage(short page) {
        this.page = page;
    }

    public void setPageName1(String pageName1) {
        this.pageName1 = pageName1;
    }

    public void setPageName2(String pageName2) {
        this.pageName2 = pageName2;
    }

    public void setPageText1(String pageText1) {
        this.pageText1 = pageText1;
    }

    public void setPageText2(String pageText2) {
        this.pageText2 = pageText2;
    }

}
