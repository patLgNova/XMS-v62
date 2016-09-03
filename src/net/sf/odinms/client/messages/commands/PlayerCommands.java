package net.sf.odinms.client.messages.commands;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.client.messages.Command;
import net.sf.odinms.client.messages.CommandDefinition;
import net.sf.odinms.client.messages.MessageCallback;
import net.sf.odinms.scripting.npc.NPCScriptManager;
import net.sf.odinms.tools.MaplePacketCreator;
import net.sf.odinms.tools.StringUtil;
import net.sf.odinms.tools.logging.LogSystem;

public class PlayerCommands implements Command {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    
    @Override
    public void execute(MapleClient c, MessageCallback mc, String[] splitted) throws Exception {
        splitted[0] = splitted[0].toLowerCase();
        MapleCharacter player = c.getPlayer();
        if (splitted[0].equals("@command") || splitted[0].equals("@commands") || splitted[0].equals("@help")) {
            mc.dropMessage("@save | Saves your progress.");
            mc.dropMessage("@dispose | Unstucks you.");
            mc.dropMessage("@togglesmega | Turn megaphones OFF/ON.");
            mc.dropMessage("@gm <message> | Sends a message to all online GMs.");
            mc.dropMessage("@afk | Shows how long a person has been AFK.");
            mc.dropMessage("@onlinetime | Shows how long a person has been online.");
            mc.dropMessage("@points | Shows how much NX & Donor Points you have.");
        }   else if (splitted[0].equals("@points")) {
            mc.dropMessage("You currently have:");
            mc.dropMessage("NX: " + player.getCSPoints(1));
        } else if (splitted[0].equals("@save")) {
            if (!player.getCheatTracker().Spam(900000, 0)) { // 15 minutes
                player.saveToDB(true, true);
                mc.dropMessage("Saved.");
            } else {
                mc.dropMessage("You cannot save more than once every 15 minutes.");
            }
        } else if (splitted[0].equals("@dispose")) {
            NPCScriptManager.getInstance().dispose(c);
            mc.dropMessage("You have been disposed.");
        } else if (splitted[0].equals("@togglesmega")) {
                player.setSmegaEnabled(!player.getSmegaEnabled());
                String text = (!player.getSmegaEnabled() ? "[Disable] Megaphones are now disabled." : "[Enable] Megaphones are now enabled.");
                mc.dropMessage(text);
        } else if (splitted[0].equals("@gm")) {
            if (!player.getCheatTracker().Spam(300000, 0)) { // 5 minutes.
                try {
                    c.getChannelServer().getWorldInterface().broadcastGMMessage(null, MaplePacketCreator.serverNotice(6, "Channel: " + c.getChannel() + "  " + player.getName() + ": " + StringUtil.joinStringFrom(splitted, 1)).getBytes());
                    LogSystem.printLog(LogSystem.GMLog + "GMLog" + ".txt", 
                            "["+sdf.format(System.currentTimeMillis())+"]"
                        + "["+sdf2.format(System.currentTimeMillis()) +"]" + player.getName() + ": " + StringUtil.joinStringFrom(splitted, 1)  + "\n");
                } catch (RemoteException ex) {
                    c.getChannelServer().reconnectWorld();
                }
                mc.dropMessage("Message sent.");
            }
        }
    }

    @Override
    public CommandDefinition[] getDefinition() {
        return new CommandDefinition[]{
            new CommandDefinition("command", 0),
            new CommandDefinition("commands", 0),
            new CommandDefinition("help", 0),
            new CommandDefinition("save", 0),
            new CommandDefinition("dispose", 0),
            new CommandDefinition("togglesmega", 0),
            new CommandDefinition("gm", 0),
            new CommandDefinition("afk", 0),
            new CommandDefinition("onlinetime", 0),
            new CommandDefinition("points", 0),
        };
    }
}