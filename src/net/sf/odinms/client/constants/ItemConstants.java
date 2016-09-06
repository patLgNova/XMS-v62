package net.sf.odinms.client.constants;

import net.sf.odinms.client.MapleInventoryType;

public class ItemConstants {

    
    
public static boolean disallowedGMItems(int itemid){
    switch (itemid){
        case 2340000:
        case 2049100:
        return true;
    }
    return false;
}    
public static boolean trackedItems(int itemid){
    switch (itemid){
        case 2340000:
        case 2049100:
        case 2022179:
        case 5222000:
        case 2022282:
        case 2043003:
            return true;
    }
    return false;
}
public static boolean blockedNPCs(int npcid){
    switch (npcid){
        case 9330045:
        case 9270043:
        return true;
    }
    return false;
}
public static boolean isRechargable(int itemId) {
    return itemId / 10000 == 233 || itemId / 10000 == 207;
}
    public static MapleInventoryType getInventoryType(final int itemId) {
        final byte type = (byte) (itemId / 1000000);
        if (type < 1 || type > 5) {
            return MapleInventoryType.UNDEFINED;
        }
        return MapleInventoryType.getByType(type);
    }
    public final static int PET_COME = 0x80;
    public final static int ACCOUNT_SHARING = 0x100;
    
    public static int getFlagByInt(int type) {
        if (type == 128) {
            return PET_COME;
        } else if (type == 256) {
            return ACCOUNT_SHARING;
        }
        return 0;
    }    
}