// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com

package discord.handlers;

import discord.ChatChannel;
import discord.MagicBot;
import discord.RobotSpeak;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.pmw.tinylog.Logger;

public class ChatChannelHandler {

    public static void handleRequest(ChatChannel chatChannel, MessageReceivedEvent event, String[] args) {

        String chatText;
        String outString;

        // Early exit if database unavailable or is not an admin

        if (MagicBot.isAdminEvent(event) == false)
            return;

        // Nothing to send?

        if (args.length == 0)
            return;

        // Convert argument array into string;

        chatText = String.join(" ", args);

        // Build String

        if (chatText.startsWith("-r "))
            outString =
                    "```\n" + "Hello Players \n\n" +
                            chatText.substring(3) + "\n\n" +
                            RobotSpeak.getRobotSpeak() + "\n```";
        else
            outString = chatText;

        // Write string to changelog channel

        if (chatChannel.textChannel.canTalk())
            chatChannel.textChannel.sendMessage(outString).queue();

        Logger.info(event.getAuthor().getName() + "general: " + chatText);

    }
}
