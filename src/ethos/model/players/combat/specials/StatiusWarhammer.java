package ethos.model.players.combat.specials;

import ethos.model.entity.Entity;
import ethos.model.players.Player;
import ethos.model.players.combat.Damage;
import ethos.model.players.combat.Special;

public class StatiusWarhammer extends Special {

	public StatiusWarhammer() {
		super(5.0, 10.25, 1.25, new int[] { 22622 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.gfx0(1241);
		player.startAnimation(7296);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
