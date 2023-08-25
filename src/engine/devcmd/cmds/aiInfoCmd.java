// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com


package engine.devcmd.cmds;

import engine.Enum.GameObjectType;
import engine.devcmd.AbstractDevCmd;
import engine.objects.AbstractGameObject;
import engine.objects.Mob;
import engine.objects.PlayerCharacter;

import java.util.Map;


/**
 * @author
 */
public class aiInfoCmd extends AbstractDevCmd {

    public aiInfoCmd() {
        super("aiinfo");
    }

    @Override
    protected void _doCmd(PlayerCharacter playerCharacter, String[] words,
                          AbstractGameObject target) {

        // Arg Count Check

        if (words.length != 1) {
            this.sendUsage(playerCharacter);
            return;
        }

        if (playerCharacter == null)
            return;

        String newline = "\r\n ";


        GameObjectType objType = target.getObjectType();
        String output;

        if (objType != GameObjectType.Mob) {
            output = "Please Select A Mob For AI Info" + newline;
            throwbackInfo(playerCharacter, output);
            return;
        }

        Mob mob = (Mob) target;
        output = "Mob AI Information:" + newline;
        output += mob.getName() + newline;
        if (mob.behaviourType != null) {
            output += "BehaviourType: " + mob.behaviourType.toString() + newline;
            if (mob.behaviourType.BehaviourHelperType != null) {
                output += "Behaviour Helper Type: " + mob.behaviourType.BehaviourHelperType.toString() + newline;
            } else {
                output += "Behaviour Helper Type: NULL" + newline;
            }
            output += "Wimpy: " + mob.behaviourType.isWimpy + newline;
            output += "Agressive: " + mob.behaviourType.isAgressive + newline;
            output += "Can Roam: " + mob.behaviourType.canRoam + newline;
            output += "Calls For Help: " + mob.behaviourType.callsForHelp + newline;
            output += "Responds To Call For Help: " + mob.behaviourType.respondsToCallForHelp + newline;
        } else {
            output += "BehaviourType: NULL" + newline;
            }
            output += "Aggro Range: " + mob.getAggroRange() + newline;
            output += "Player Aggro Map Size: " + mob.playerAgroMap.size() + newline;
        if (mob.playerAgroMap.size() > 0) {
            output += "Players Loaded:" + newline;
        }
        for (Map.Entry<Integer, Boolean> entry : mob.playerAgroMap.entrySet()) {
            output += "Player ID: " + entry.getKey() + " Hate Value: " + (PlayerCharacter.getPlayerCharacter(entry.getKey())).getHateValue() + newline;
        }
        if (mob.getCombatTarget() != null)
            output += "Current Target: " + mob.getCombatTarget().getName() + newline;
        else
            output += "Current Target: NULL" + newline;

        if (mob.guardedCity != null)
            output += mob.guardedCity.getCityName() + newline;

        for (int token : mob.mobPowers.keySet())
            output += token + newline;

        throwbackInfo(playerCharacter, output);
    }

    @Override
    protected String _getHelpString() {
        return "Gets AI information on a Mob.";
    }

    @Override
    protected String _getUsageString() {
        return "' /aiinfo targetID'";
    }

}