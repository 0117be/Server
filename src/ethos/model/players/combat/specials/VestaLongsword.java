package ethos.model.players.combat.specials;

import ethos.model.entity.Entity;
import ethos.model.players.Player;
import ethos.model.players.combat.Damage;
import ethos.model.players.combat.Special;

public class VestaLongsword extends Special {

	public VestaLongsword() {
		super(2.5, 1.3, 1.20, new int[] { 22613 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.startAnimation(7295);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
