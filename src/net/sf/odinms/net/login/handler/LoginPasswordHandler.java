package net.sf.odinms.net.login.handler;

import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.net.MaplePacketHandler;
import net.sf.odinms.net.login.LoginServer;
import net.sf.odinms.net.login.LoginWorker;
import net.sf.odinms.server.AutoRegister;
import net.sf.odinms.tools.MaplePacketCreator;
import net.sf.odinms.tools.data.input.SeekableLittleEndianAccessor;
import net.sf.odinms.tools.KoreanDateUtil;
import java.util.Calendar;
import net.sf.odinms.tools.logging.LogSystem;

public class LoginPasswordHandler implements MaplePacketHandler {
    @Override
    public boolean validateState(MapleClient c) {
        return !c.isLoggedIn();
    }

    @Override
    public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        String username = slea.readMapleAsciiString();
        String password = slea.readMapleAsciiString();
        int int1, int2, int3, int4;
        int1 = slea.readInt();
        int2 = slea.readInt();
        int3 = slea.readInt();
        int4 = slea.readInt();
        long hwid = int1+int2+int3+int4;
        hwid = hwid + 0x7FFFFFFF;
        int gameRoomCode = slea.readInt();
        byte gameStartMode = slea.readByte();
        byte adminClient = slea.readByte();
        byte gmClient = slea.readByte();
            if ((adminClient != 0 || gmClient != 0) & !c.isGm()) {
                LogSystem.printLog(LogSystem.Login + username +".txt", 
                        username + " has a gmClient or adminClient byte that isn't 1.");
            }
        int partnerCode = slea.readInt();
        c.setAccountName(username);
        int loginok = 0;
        boolean ipBan = c.hasBannedIP();
        boolean macBan = c.hasBannedMac();
        if (AutoRegister.getAccountExists(username)) {
            loginok = c.login(username, password, ipBan || macBan);
        } else if (LoginServer.getInstance().AutoRegister() && (!ipBan || !macBan)) {
            AutoRegister.createAccount(username, password, c.getSession().getRemoteAddress().toString());
            if (AutoRegister.success) {
                loginok = c.login(username, password, ipBan || macBan);
            }
        } else loginok = c.login(username, password, ipBan || macBan);
        Calendar tempbannedTill = c.getTempBanCalendar();
        if (loginok == 0 && (ipBan || macBan)) {
            loginok = 3;
            if (macBan) {
                String[] ipSplit = c.getSession().getRemoteAddress().toString().split(":");
                MapleCharacter.ban(ipSplit[0], "Enforcing account ban, account " + username, false);
            }
        }
        if (loginok == 3) {
            c.getSession().write(MaplePacketCreator.getPermBan(c.getBanReason()));
            return;
        } else if (loginok != 0) {
            c.getSession().write(MaplePacketCreator.getLoginFailed(loginok));
            return;
        } else if (tempbannedTill.getTimeInMillis() != 0) {
            long tempban = KoreanDateUtil.getTempBanTimestamp(tempbannedTill.getTimeInMillis());
            byte reason = c.getBanReason();
            c.getSession().write(MaplePacketCreator.getTempBan(tempban, reason));
            return;
        }
        if (c.isGm()) {
            LoginWorker.getInstance().registerGMClient(c);
        } else {
            LoginWorker.getInstance().registerClient(c);
        }
    }
}