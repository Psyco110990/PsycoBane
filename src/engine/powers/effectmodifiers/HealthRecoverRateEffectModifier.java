// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com


package engine.powers.effectmodifiers;

import engine.jobs.AbstractEffectJob;
import engine.objects.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HealthRecoverRateEffectModifier extends AbstractEffectModifier {

    public HealthRecoverRateEffectModifier(ResultSet rs) throws SQLException {
        super(rs);
    }

    @Override
    protected void _applyEffectModifier(AbstractCharacter source, AbstractWorldObject awo, int trains, AbstractEffectJob effect) {

    }

    @Override
    public void applyBonus(AbstractCharacter ac, int trains) {

        ac.update();
        Float amount = 0f;
        PlayerBonuses bonus = ac.getBonuses();
        if (this.useRampAdd)
            amount = this.percentMod + (this.ramp * trains);
        else
            amount = this.percentMod * (1 + (this.ramp * trains));
        amount = amount / 100;
        bonus.multRegen(this.modType, amount); //positive regen modifiers
    }

    @Override
    public void applyBonus(Item item, int trains) {
    }

    @Override
    public void applyBonus(Building building, int trains) {
    }
}
