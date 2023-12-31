// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com

package discord.handlers;

import discord.Database;
import discord.MagicBot;
import engine.gameManager.ConfigManager;
import engine.server.login.LoginServer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StatusRequestHandler {

    public static void handleRequest(MessageReceivedEvent event) {

        String outString;

        // Add server status info
        outString = "Server Status: ";

        if (LoginServer.isPortInUse(Integer.parseInt(ConfigManager.MB_WORLD_PORT.getValue())))
            outString += "ONLINE\n";
        else
            outString += "OFFLINE\n";

        if (Database.online == true)
            outString += MagicBot.database.getPopulationString();
        else
            outString += "Database offline: no population data.";

        MagicBot.sendResponse(event, outString);
    }
}
