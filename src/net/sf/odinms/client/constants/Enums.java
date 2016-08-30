/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.odinms.client.constants;

/**
 * @author V
 * Stupid mode types in MaplePacketCreator and in other areas. 
 * Storing everything together in here to be changed easily.
 */
public class Enums {
    public enum Storage {
        TakeOut(9),
        Store(12),
        StorageFull(16),
        StorageMeso(18),
        Storage(21);
        final byte code;

        private Storage(int code) {
            this.code = (byte) code;
        }

        public byte getCode() {
            return code;
        }
    }
    public enum Party {
        Invite(4),
        Create(8),
        Logoff(7),
        Leave(12),
        Expel(12),
        Disband(12),
        Join(15),
        ChangeLeader(26),
        SilentUpdate(33),
        Portal(34),
        PartyQuestInfo(36);
        final byte code;

        private Party(int code) {
            this.code = (byte) code;
        }

        public byte getCode() {
            return code;
        }
    }
    public enum Cash {
        LoadWishlist(51),
        CorrectCouponCode(58),
        ShowBoughtCashItem(59),
        WrongCouponCode(64);
        final byte code;

        private Cash(int code) {
            this.code = (byte) code;
        }

        public byte getCode() {
            return code;
        }
    }
    public enum Equipment {
        Hat(-1),
        Face_Accessory(-2),
        Eye_Accesory(-3),
        Earring(-4),
        Top(-5),
        Overall(-5),
        Bottom(-6),
        Shoes(-7),
        Glove(-8),
        Cape(-9),
        Shield(-10),
        Weapon(-11),
        Pendant(-17),
        Mount_Mob(-18),
        Mount_Saddle(-19),
        Cash_Hat(-101),
        Cash_Face_Accessory(-102),
        Cash_Eye_Acessory(-103),
        Cash_Earring(-104),
        Cash_Top(-105),
        Cash_Overall(-105),
        Cash_Bottom(-106),
        Cash_Shoes(-107),
        Cash_Glove(-108),
        Cash_Cape(-109),
        Cash_Shield(-110),
        Cash_One_Handed_Weapon(-111),
        Cash_Ring_1(-112),
        Cash_Ring_2(-113),
        Cash_Pet_Equip(-114),
        Cash_Ring_3(-115),
        Cash_Ring_4(-116),
        Cash_Pendant(-117),
        Cash_Mount_Mob(-118),
        Cash_Mount_Saddle(-119),
        Cash_Pet_Ring(-121),
        Cash_Pet_Item_Pouch(-122),
        Cash_Pet_Meso_Magnet(-123),
        Cash_Pet_Auto_HP(-124),
        Cash_Pet_Auto_MP(-125),
        Cash_Pet_Wing_Boots(-126),
        Cash_Pet_Binoculars(-127),
        Cash_Pet_Magic_Scales(-128),
        Cash_Pet_Item_Ignore(-129);
        final byte code;
        
        private Equipment(int code) {
            this.code = (byte) code;
        }
        public byte getCode() {
            return code;
        }
    }
    
}
