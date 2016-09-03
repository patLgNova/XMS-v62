package net.sf.odinms.net.channel.handler;

import java.text.SimpleDateFormat;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.net.AbstractMaplePacketHandler;
import net.sf.odinms.tools.data.input.SeekableLittleEndianAccessor;
import net.sf.odinms.tools.logging.LogSystem;

public class HealOvertimeHandler extends AbstractMaplePacketHandler {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    @Override
    public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        int unk  = slea.readInt();
        int healHP = slea.readShort();
        if (healHP != 0) {
            if (healHP > 1000) {
                LogSystem.printLog(LogSystem.Cheaters + c.getPlayer().getName() + ".txt", 
                         "["+sdf.format(System.currentTimeMillis())+"]"
                        + "["+sdf2.format(System.currentTimeMillis()) +"]Attempted to heal for " +healHP+ " in map " + c.getPlayer().getMapId() + ".\n");
                return;
            }
            c.getPlayer().addHP(healHP);
        }
        int healMP = slea.readShort();
        if (healMP != 0) {
            if (healMP > 1000) {
                LogSystem.printLog(LogSystem.Cheaters + c.getPlayer().getName() + ".txt", 
                         "["+sdf.format(System.currentTimeMillis())+"]"
                        + "["+sdf2.format(System.currentTimeMillis()) +"]Attempted to heal for " +healMP+ " in map " + c.getPlayer().getMapId() + ".\n");
                return;
            }
            c.getPlayer().addMP(healMP);
        }
        byte healByte = slea.readByte();
    }
}