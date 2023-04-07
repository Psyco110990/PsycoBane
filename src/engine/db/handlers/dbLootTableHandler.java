// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com


package engine.db.handlers;

import engine.loot.LootManager;
import engine.objects.Item;
import engine.objects.LootTable;
import org.pmw.tinylog.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class dbLootTableHandler extends dbHandlerBase {

    public dbLootTableHandler() {

    }


    public void populateLootGroups() {
        int recordsRead = 0;
        prepareCallable("SELECT `groupID`, `minRoll`, `maxRoll`, `lootTableID`, `pModTableID`, `sModTableID` FROM `static_lootgroups`");

        try {
            ResultSet rs = executeQuery();
            if (rs != null)
                while (rs.next()) {
                    recordsRead++;
                    LootTable lootTable = LootTable.getLootGroup(rs.getInt("groupID"));
                    lootTable.addRow(rs.getFloat("minRoll"), rs.getFloat("maxRoll"), rs.getInt("lootTableID"), rs.getInt("pModTableID"), rs.getInt("sModTableID"), "");
                }

            Logger.info("read: " + recordsRead + " cached: " + LootTable.getLootGroups().size());
        } catch (SQLException e) {
        } finally {
            closeCallable();
        }
    }

    public void populateLootTables() {
        int recordsRead = 0;

        prepareCallable("SELECT `lootTable`, `minRoll`, `maxRoll`, `itemBaseUUID`, `minSpawn`, `maxSpawn` FROM `static_loottables`");

        try {
            ResultSet rs = executeQuery();
            if (rs != null)
                while (rs.next()) {
                    recordsRead++;
                    LootTable lootTable = LootTable.getLootTable(rs.getInt("lootTable"));
                    lootTable.addRow(rs.getFloat("minRoll"), rs.getFloat("maxRoll"), rs.getInt("itemBaseUUID"), rs.getInt("minSpawn"), rs.getInt("maxSpawn"), "");
                }

            Logger.info("read: " + recordsRead + " cached: " + LootTable.getLootTables().size());
        } catch (SQLException e) {
        } finally {
            closeCallable();
        }
    }

    public void populateModTables() {

        int recordsRead = 0;

        prepareCallable("SELECT `modTable`,`minRoll`,`maxRoll`,`value`,`action` FROM `static_modtables`");

        try {
            ResultSet rs = executeQuery();
            if (rs != null)
                while (rs.next()) {
                    recordsRead++;
                    LootTable lootTable = LootTable.getModTable(rs.getInt("modTable"));
                    lootTable.addRow(rs.getFloat("minRoll"), rs.getFloat("maxRoll"), rs.getInt("value"), 0, 0, rs.getString("action"));
                }
            Logger.info("read: " + recordsRead + " cached: " + LootTable.getModTables().size());
        } catch (SQLException e) {
        } finally {
            closeCallable();
        }
    }

    public void populateModGroups() {

        int recordsRead = 0;

        prepareCallable("SELECT `modGroup`,`minRoll`,`maxRoll`,`subTableID` FROM `static_modgroups`");

        try {
            ResultSet rs = executeQuery();
            if (rs != null)
                while (rs.next()) {
                    recordsRead++;
                    LootTable lootTable = LootTable.getModGroup(rs.getInt("modGroup"));
                    lootTable.addRow(rs.getFloat("minRoll"), rs.getFloat("maxRoll"), rs.getInt("subTableID"), 0, 0, "");
                }
            Logger.info("read: " + recordsRead + " cached: " + LootTable.getModGroups().size());
        } catch (SQLException e) {
        } finally {
            closeCallable();
        }
    }

    public void LOAD_ENCHANT_VALUES() {

        prepareCallable("SELECT `IDString`, `minMod` FROM `static_power_effectmod` WHERE `modType` = ?");
        setString(1,"Value");

        try {
            ResultSet rs = executeQuery();
            while (rs.next()) {
                Item.addEnchantValue(rs.getString("IDString"), rs.getInt("minMod"));
            }
        } catch (SQLException e) {
            Logger.error( e);
        } finally {
            closeCallable();
        }
    }

    public void LOAD_ALL_LOOTGROUPS() {
        int recordsRead = 0;

        prepareCallable("SELECT * FROM static_lootgroups");

        try {
            ResultSet rs = executeQuery();

            while (rs.next()) {
                LootManager.GenTable gt = new LootManager.GenTable();
                LootManager.AddGenTableRow(rs.getInt("groupID"),gt,new LootManager.GenTableRow(rs));
            }

            Logger.info( "read: " + recordsRead);

        } catch (SQLException e) {
            Logger.error( e.getErrorCode() + ' ' + e.getMessage(), e);
        } finally {
            closeCallable();
        }
    }

    public void LOAD_ALL_LOOTTABLES() {

        int recordsRead = 0;

        prepareCallable("SELECT * FROM static_loottables");

        try {
            ResultSet rs = executeQuery();

            while (rs.next()) {

                recordsRead++;
                LootManager.ItemTable it = new LootManager.ItemTable();
                LootManager.AddItemTableRow(rs.getInt("lootTable"),it,new LootManager.ItemTableRow(rs));
            }

            Logger.info("read: " + recordsRead);

        } catch (SQLException e) {
            Logger.error( e.getErrorCode() + ' ' + e.getMessage(), e);
        } finally {
            closeCallable();
        }
    }

    public void LOAD_ALL_MODGROUPS() {
        int recordsRead = 0;

        prepareCallable("SELECT * FROM static_modgroups");

        try {
            ResultSet rs = executeQuery();

            while (rs.next()) {

                recordsRead++;
                LootManager.ModTypeTable mtt = new LootManager.ModTypeTable();
                LootManager.AddModTypeTableRow(rs.getInt("modGroup"),mtt,new LootManager.ModTypeTableRow(rs));
            }

            Logger.info( "read: " + recordsRead);

        } catch (SQLException e) {
            Logger.error(e.getErrorCode() + ' ' + e.getMessage(), e);
        } finally {
            closeCallable();
        }
    }

    public void LOAD_ALL_MODTABLES() {
        int recordsRead = 0;

        prepareCallable("SELECT * FROM static_modtables");

        try {
            ResultSet rs = executeQuery();

            while (rs.next()) {

                recordsRead++;
                LootManager.ModTable mt = new LootManager.ModTable();
                LootManager.AddModTableRow(rs.getInt("modTable"),mt,new LootManager.ModTableRow(rs));
            }

            Logger.info( "read: " + recordsRead);

        } catch (SQLException e) {
            Logger.error( e.getErrorCode() + ' ' + e.getMessage(), e);
        } finally {
            closeCallable();
        }
    }
}
