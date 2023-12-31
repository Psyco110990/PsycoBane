// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com

package engine.net.client.handlers;

import engine.Enum;
import engine.exception.MsgSendException;
import engine.gameManager.BuildingManager;
import engine.gameManager.ChatManager;
import engine.gameManager.DbManager;
import engine.gameManager.SessionManager;
import engine.net.client.ClientConnection;
import engine.net.client.msg.ArcMineWindowChangeMsg;
import engine.net.client.msg.ClientNetMsg;
import engine.net.client.msg.ErrorPopupMsg;
import engine.objects.Building;
import engine.objects.Guild;
import engine.objects.GuildStatusController;
import engine.objects.PlayerCharacter;
import engine.server.MBServerStatics;
import org.pmw.tinylog.Logger;

import java.time.LocalDateTime;

/*
 * @Author:
 * @Summary: Processes requests to change a mine's opendate
 */

public class MineWindowChangeHandler extends AbstractClientMsgHandler {

    public MineWindowChangeHandler() {
        super(ArcMineWindowChangeMsg.class);
    }

    @Override
    protected boolean _handleNetMsg(ClientNetMsg baseMsg, ClientConnection origin) throws MsgSendException {

        PlayerCharacter playerCharacter = SessionManager.getPlayerCharacter(origin);
        ArcMineWindowChangeMsg mineWindowChangeMsg = (ArcMineWindowChangeMsg) baseMsg;
        int newMineTime;

        if (playerCharacter == null)
            return true;

        Building treeOfLife = BuildingManager.getBuildingFromCache(mineWindowChangeMsg.getBuildingID());

        if (treeOfLife == null)
            return true;

        if (treeOfLife.getBlueprintUUID() == 0)
            return true;

        if (treeOfLife.getBlueprint().getBuildingGroup() != Enum.BuildingGroup.TOL)
            return true;

        Guild mineGuild = treeOfLife.getGuild();

        if (mineGuild.isEmptyGuild())
            return true;

        if (!Guild.sameGuild(mineGuild, playerCharacter.getGuild()))
            return true;  //must be same guild

        if (GuildStatusController.isInnerCouncil(playerCharacter.getGuildStatus()) == false) // is this only GL?
            return true;

        newMineTime = mineWindowChangeMsg.getTime();

        // Sanity check for possible slider value

        if (newMineTime == 24)
            newMineTime = 0;

        // Enforce time restriction between WOO edits

        if (mineGuild.wooWasModified) {
            ErrorPopupMsg.sendErrorMsg(playerCharacter, "You can only modify your WOO once per day.");
            return true;
        }

        //hodge podge sanity check to make sure they don't set it before early window and is not set at late window.

        if (newMineTime < MBServerStatics.MINE_EARLY_WINDOW &&
                newMineTime != MBServerStatics.MINE_LATE_WINDOW) {
            ErrorPopupMsg.sendErrorMsg(playerCharacter, "Mine time is outside the NA Woo window.");
            return true;
        }

        // Cannot set a time to a window that has closed if mines are currently open.

        if (LocalDateTime.now().getHour() >= MBServerStatics.MINE_EARLY_WINDOW &&
                LocalDateTime.now().getHour() != MBServerStatics.MINE_LATE_WINDOW) {

            if (newMineTime <= LocalDateTime.now().getHour())
                ErrorPopupMsg.sendErrorMsg(playerCharacter, "Cannot set mines to a previous window.");
            return true;
        }

        // Update guild mine time

        if (!DbManager.GuildQueries.UPDATE_MINETIME(mineGuild.getObjectUUID(), newMineTime)) {
            Logger.error("MineWindowChange", "Failed to update mine time for guild " + mineGuild.getObjectUUID());
            ChatManager.chatGuildError(playerCharacter, "Failed to update the mine time");
            return true;
        }

        mineGuild.setMineTime(newMineTime);
        mineGuild.wooWasModified = true;

        ChatManager.chatGuildInfo(playerCharacter, "Mine time updated.");

        return true;
    }

}