// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com


package engine.objects;

import engine.Enum.ShrineType;
import engine.gameManager.DbManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Boon {

    public static HashMap<Integer, ArrayList<Boon>> GetBoonsForItemBase = new HashMap<>();
    private ShrineType shrineType;
    private int amount;
    private int itemBaseID;


    /**
     * ResultSet Constructor
     */
    public Boon(ResultSet rs) throws SQLException {

        this.shrineType = ShrineType.valueOf(rs.getString("shrineType"));
        this.itemBaseID = rs.getInt("itemBaseID");
        this.amount = rs.getInt("amount");
    }

    public static void HandleBoonListsForItemBase(int itemBaseID) {
        ArrayList<Boon> boons = null;
        boons = DbManager.BoonQueries.GET_BOON_AMOUNTS_FOR_ITEMBASE(itemBaseID);
        if (boons != null)
            GetBoonsForItemBase.put(itemBaseID, boons);
    }

    public int getAmount() {
        return this.amount;
    }

    public int getItemBaseID() {
        return itemBaseID;
    }

    public ShrineType getShrineType() {
        return shrineType;
    }


}
