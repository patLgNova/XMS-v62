package net.sf.odinms.net.channel.handler;

import net.sf.odinms.client.MapleClient;
import net.sf.odinms.net.AbstractMaplePacketHandler;
import net.sf.odinms.server.AutobanManager;
import net.sf.odinms.tools.data.input.SeekableLittleEndianAccessor;
import net.sf.odinms.tools.logging.LogSystem;

public class HealOvertimeHandler extends AbstractMaplePacketHandler {

    @Override
    public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        slea.readByte();
        slea.readShort();
        slea.readByte();
        int healHP = slea.readShort();
        if (healHP != 0) {
            if (healHP > 1000) {
                LogSystem.printLog(LogSystem.Cheaters + c.getPlayer().getName() + ".txt", "Attempted to heal for " +healHP+ " in map " + c.getPlayer().getMapId() + ".\n");
                return;
            }
            c.getPlayer().addHP(healHP);
        }
        int healMP = slea.readShort();
        if (healMP != 0) {
            if (healMP > 1000) {
                LogSystem.printLog(LogSystem.Cheaters + c.getPlayer().getName() + ".txt", "Attempted to heal for " +healMP+ " in map " + c.getPlayer().getMapId() + ".\n");
                return;
            }
            c.getPlayer().addMP(healMP);
        }
    }
}