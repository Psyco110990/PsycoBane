// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com


package engine.loot;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BootySetEntry {

    public String bootyType;
    public int lowGold;
    public int highGold;
    public int itemBase;
    public int genTable;
    public float dropChance;

    /**
     * ResultSet Constructor
     */

    public BootySetEntry(ResultSet rs) throws SQLException {
        this.bootyType = (rs.getString("bootyType"));
        this.lowGold = (rs.getInt("lowGold"));
        this.highGold = (rs.getInt("highGold"));
        this.itemBase = (rs.getInt("itemBase"));
        this.genTable = (rs.getInt("genTable"));
        this.dropChance = (rs.getFloat("dropChance"));
    }

}
