// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com


package engine.devcmd.cmds;


import engine.devcmd.AbstractDevCmd;
import engine.gameManager.BuildingManager;
import engine.objects.AbstractGameObject;
import engine.objects.Building;
import engine.objects.Mine;
import engine.objects.PlayerCharacter;
import engine.workthreads.HourlyJobThread;

/**
 *
 */
public class MineActiveCmd extends AbstractDevCmd {

    public MineActiveCmd() {
        super("mineactive");
    }

    @Override
    protected void _doCmd(PlayerCharacter pcSender, String[] args,
                          AbstractGameObject target) {
        Building mineBuilding = BuildingManager.getBuilding(target.getObjectUUID());
        if (mineBuilding == null)
            return;

        Mine mine = Mine.getMineFromTower(mineBuilding.getObjectUUID());
        if (mine == null)
            return;

        String trigger = args[0];
        switch (trigger) {
            case "true":
                HourlyJobThread.mineWindowOpen(mine);
                break;
            case "false":
                HourlyJobThread.mineWindowClose(mine);
                break;
            default:
                this.sendUsage(pcSender);
                break;

        }
    }

    @Override
    protected String _getUsageString() {
        return "' /mineactive true|false";
    }

    @Override
    protected String _getHelpString() {
        return "Temporarily add visual effects to Character";
    }

}
