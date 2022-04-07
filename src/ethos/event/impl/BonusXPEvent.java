package ethos.event.impl;

import ethos.Config;
import ethos.event.Event;
import ethos.model.content.bxp;
import ethos.model.content.wogw.Wogw;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

import java.util.concurrent.TimeUnit;

public class BonusXPEvent extends Event<Object> {

    private static final int INTERVAL = Misc.toCyclesOrDefault(2, 1, TimeUnit.SECONDS);

    public BonusXPEvent() {
        super(new String(), new Object(), INTERVAL);
    }

    @Override
    public void execute() {
        if (bxp.EXPERIENCE_TIMER > 0) {
            bxp.EXPERIENCE_TIMER--;
            if (bxp.EXPERIENCE_TIMER == 1) {
                PlayerHandler.executeGlobalMessage("@cr10@[@blu@BXP@bla@] <col=6666FF>Bonus EXP has ran out!");
                Config.bonusXP = false;
            }
            bxp.appendBonus();
        }
    }
}
