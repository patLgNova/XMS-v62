package net.sf.odinms.tools.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogSystem {

    public static final String 
            ACCOUNT_STUCK = "accountStuck.txt",
            EXCEPTION_CAUGHT = "exceptionCaught.txt",
            CLIENT_START = "clientStartError.txt",
            ADD_PLAYER = "addPlayer.txt",
            MAPLE_MAP = "mapleMap.txt",
            ERROR38 = "error38.txt",
            susMac = "susMac/",
            CHEATER = "Cheaters.txt",
            PACKET_LOG = "log.txt",
            EXCEPTION = "exceptions.txt",
            Exceptions = "exceptioned.txt",
            PACKET_HANDLER = "PacketHandler/",
            Portal = "portals/",
            ImproperHeal  = "improperHeal/",
            NPC = "npcs/",
            SpecialMoveHandler = "SpecialMoveHandler/",
            NPCe = "npcsE/",
            GMLog = "GMLog/",
            Damage = "Damage/",
            Login = "Login/",
            Cheaters = "Cheaters/",
            TrackedItems = "trackedItems/",
            TrackedDLogin = "Dlogin/",
            MoreTalkLog = "MoreTalkLog/",
            LevelUp = "Levelup/",
            Bosses = "BossKills/",
            INVOCABLE = "invocable/",
            REACTOR = "reactors/",
            Quest = "quests/",
            ITEM = "items/",
            FastAttack = "FastAttack/",
            MOB_MOVEMENT = "mobmovement.txt",
            MAP_SCRIPT = "mapscript/",
            DIRECTION = "directions/",
            SAVE_CHAR = "saveToDB.txt",
            INSERT_CHAR = "insertCharacter.txt",
            LOAD_CHAR = "loadCharFromDB.txt",
            SESSION = "sessions.txt";
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private static final String FILE_PATH = "LogSystem/" + sdf.format(Calendar.getInstance().getTime()) + "/";
    private static final String ERROR = "errors/";
    private static final String LOG = "logs/";

    public static void printLog(final String name, final String s) {
        FileOutputStream out = null;
        final String file = FILE_PATH + LOG + name;
        try {
            File outputFile = new File(file);
                if (outputFile.getParentFile() != null) {
                    outputFile.getParentFile().mkdirs();
                }  
            out = new FileOutputStream(file, true);
            out.write(s.getBytes());
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }
    
    public static void printError(final String name, final Throwable t) {
        FileOutputStream out = null;
        final String file = FILE_PATH + ERROR + name;
        try {
            File outputFile = new File(file);
                if (outputFile.getParentFile() != null) {
                    outputFile.getParentFile().mkdirs();
                }  
            out = new FileOutputStream(file, true);
            out.write(getString(t).getBytes());
            out.write("\n---------------------------------\r\n".getBytes());
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static void printError(final String name, final Throwable t, final String info) {
        FileOutputStream out = null;
        final String file = FILE_PATH + ERROR + name;
        try {
            File outputFile = new File(file);
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            out.write((info + "\r\n").getBytes());
            out.write(getString(t).getBytes());
            out.write("\n---------------------------------\r\n".getBytes());
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static void printError(final String name, final String s) {
        FileOutputStream out = null;
        final String file = FILE_PATH + ERROR + name;
        try {
            File outputFile = new File(file);
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            out.write(s.getBytes());
            //out.write("\n---------------------------------\n".getBytes());
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static void print(final String name, final String s) {
        print(name, s, true);
    }

    public static void print(final String name, final String s, boolean line) {
        FileOutputStream out = null;
        String file = FILE_PATH + name;
        try {
            File outputFile = new File(file);
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            out.write(s.getBytes());
            out.write("\r\n".getBytes());
            if (line) {
                out.write("---------------------------------\r\n".getBytes());
            }
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    private static String getString(final Throwable e) {
        String retValue = null;
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            retValue = sw.toString();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (sw != null) {
                    sw.close();
                }
            } catch (IOException ignore) {
            }
        }
        return retValue;
    }
}