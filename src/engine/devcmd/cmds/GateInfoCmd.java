package engine.devcmd.cmds;

import engine.Enum.BuildingGroup;
import engine.Enum.GameObjectType;
import engine.devcmd.AbstractDevCmd;
import engine.math.Vector3fImmutable;
import engine.objects.*;

public class GateInfoCmd extends AbstractDevCmd {

    public GateInfoCmd() {
        super("gateinfo");
    }

    // AbstractDevCmd Overridden methods

    @Override
    protected void _doCmd(PlayerCharacter player, String[] args,
                          AbstractGameObject target) {

        Building targetBuilding;
        String outString;
        Runegate runeGate;
        Blueprint blueprint;
        String newline = "\r\n ";
        targetBuilding = (Building) target;

        if (targetBuilding.getObjectType() != GameObjectType.Building) {
            throwbackInfo(player, "GateInfo: target must be a Building");
            throwbackInfo(player, "Found" + targetBuilding.getObjectType().toString());
            return;
        }

        blueprint = Blueprint._meshLookup.get(targetBuilding.getMeshUUID());

        if (blueprint == null ||
                (blueprint.getBuildingGroup() != BuildingGroup.RUNEGATE)) {
            throwbackInfo(player, "showgate: target must be a Runegate");
            return;
        }


        runeGate = Runegate._runegates.get(targetBuilding.getObjectUUID());

        outString = "RungateType: " + runeGate.gateBuilding.getName();
        outString += newline;

        outString += "Portal State:";
        outString += newline;

        for (Portal portal : runeGate.getPortals()) {

            outString += "Portal: " + portal.portalType.name();
            outString += " Active: " + portal.isActive();
            outString += " Dest: " + portal.targetGate.getName();
            outString += newline;
            outString += " Origin: " + portal.getPortalLocation().x + 'x';
            outString += " " + portal.getPortalLocation().y + 'y';
            outString += newline;

            Vector3fImmutable offset = portal.getPortalLocation().subtract(targetBuilding.getLoc());
            outString += " Offset: " + offset.x + "x " + offset.z + 'y';
            outString += newline;
            outString += newline;

        }
        outString += newline;
        throwbackInfo(player, outString);
    }

    @Override
    protected String _getHelpString() {
        return "Displays a runegate's gate status";
    }

    @Override
    protected String _getUsageString() {


        return "/gateinfo <target runegate> \n";
    }


}
