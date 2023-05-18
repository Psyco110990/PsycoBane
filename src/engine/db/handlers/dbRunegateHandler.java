// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com


package engine.db.handlers;

import engine.Enum;
import engine.gameManager.DbManager;
import engine.objects.Building;
import engine.objects.Portal;
import org.pmw.tinylog.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class dbRunegateHandler extends dbHandlerBase {

    public dbRunegateHandler() {

    }

    public ArrayList<Integer> GET_RUNEGATE_LIST() {

        ArrayList<Integer> gateList = new ArrayList<>();

        prepareCallable("SELECT DISTINCT `sourceBuilding` FROM `static_runegate_portals`;");

        try {
            ResultSet rs = executeQuery();
            while (rs.next()) {
                gateList.add(rs.getInt("sourceBuilding"));
            }
        } catch (SQLException e) {
        } finally {
            closeCallable();
        }
        return gateList;
    }

    public ArrayList<Portal> GET_PORTAL_LIST(int gateUID) {

        ArrayList<Portal> portalList = new ArrayList<>();
        Building sourceBuilding = (Building) DbManager.getObject(Enum.GameObjectType.Building, gateUID);

        prepareCallable("SELECT * FROM `static_runegate_portals` WHERE `sourceBuilding` = ?;");
        setInt(1, gateUID);

        try {
            ResultSet rs = executeQuery();

            while (rs.next()) {
                int targetBuildingID = rs.getInt("targetBuilding");
                Building targetBuilding = (Building) DbManager.getObject(Enum.GameObjectType.Building, targetBuildingID);
                Enum.PortalType portalType = Enum.PortalType.valueOf(rs.getString("portalType"));
                Portal portal = new Portal(sourceBuilding, portalType, targetBuilding);
                portalList.add(portal);
            }

        } catch (SQLException e) {
            Logger.error("Exception while loading runegate portals: " + e);
        } finally {
            closeCallable();
        }
        return portalList;
    }
}
