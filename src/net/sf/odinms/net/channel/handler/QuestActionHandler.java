package net.sf.odinms.net.channel.handler;

import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.net.AbstractMaplePacketHandler;
import net.sf.odinms.scripting.quest.QuestScriptManager;
import net.sf.odinms.server.quest.MapleQuest;
import net.sf.odinms.tools.MaplePacketCreator;
import net.sf.odinms.tools.data.input.SeekableLittleEndianAccessor;

public class QuestActionHandler extends AbstractMaplePacketHandler {

    /** Creates a new instance of QuestActionHandler */
    public QuestActionHandler() {
    }

    public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        c.getPlayer().resetAfkTime();
        byte action = slea.readByte();
        short questid = slea.readShort();
        MapleCharacter player = c.getPlayer();
        MapleQuest quest = MapleQuest.getInstance(questid);
        if (action == 1) { //Start Quest
            int npc = slea.readInt();
            if (slea.available() >= 4) {
                slea.readInt();
            }
            quest.start(player, npc);
        } else if (action == 2) { // Complete Quest
            int npc = slea.readInt();
            slea.readInt();
            if (slea.available() >= 2) {
                int selection = slea.readShort();
                quest.complete(player, npc, selection);
            } else {
                quest.complete(player, npc);
            }
        } else if (action == 3) {// forfeit quest
            quest.forfeit(player);
        } else if (action == 4) { // scripted start quest
            int npc = slea.readInt();
            slea.readInt();
		if(quest.canStart(player, npc)) {
                    QuestScriptManager.getInstance().start(c, questid, npc);
		}
        } else if (action == 5) { // scripted end quests
            //System.out.println(slea.toString());
            int npc = slea.readInt();
            slea.readInt();
		if(quest.canComplete(player, npc)) {
                    QuestScriptManager.getInstance().end(c, questid, npc);
		}
        }
    }
}