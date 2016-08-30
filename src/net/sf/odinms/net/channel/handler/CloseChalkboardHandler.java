package net.sf.odinms.net.channel.handler;

import net.sf.odinms.client.MapleClient;
import net.sf.odinms.net.AbstractMaplePacketHandler;
import net.sf.odinms.tools.data.input.SeekableLittleEndianAccessor;

public class CloseChalkboardHandler extends AbstractMaplePacketHandler {

    @Override
    public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        c.getPlayer().resetAfkTime();
        if (c.getPlayer().getChalkboard() != null) {
            c.getPlayer().setChalkboard(null);
        } else {
            c.getPlayer().dropMessage("Your chalkboard is already closed.");
        }
    }
}