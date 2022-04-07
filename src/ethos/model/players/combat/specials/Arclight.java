package ethos.model.players.combat.specials;

import ethos.model.entity.Entity;
import ethos.model.players.Player;
import ethos.model.players.combat.Damage;
import ethos.model.players.combat.Special;

public class Arclight extends Special {

	public Arclight() {
		super(5.0, 2.10, 1.30, new int[] { 19675 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.startAnimation(2890);
		player.gfx0(483);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		if (target instanceof Player) {
			if (damage.getAmount() >= 0) {
				if (((Player) target).playerLevel[1] > 0 || ((Player) target).playerLevel[0] > 0 || ((Player) target).playerLevel[2] > 0)
				((Player) target).playerLevel[1] -= ((Player) target).playerLevel[1] * .05;
				((Player) target).getPA().refreshSkill(1);
			((Player) target).playerLevel[0] -= ((Player) target).playerLevel[0] * .05;
			((Player) target).getPA().refreshSkill(0);
		((Player) target).playerLevel[2] -= ((Player) target).playerLevel[2] * .05;
		((Player) target).getPA().refreshSkill(2);
	}
		}

	}

}
