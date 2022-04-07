package ethos.model.players.combat.specials;

import ethos.model.entity.Entity;
import ethos.model.npcs.NPC;
import ethos.model.players.Player;
import ethos.model.players.combat.AttackNPC;
import ethos.model.players.combat.AttackPlayer;
import ethos.model.players.combat.CombatType;
import ethos.model.players.combat.Damage;
import ethos.model.players.combat.Special;
import ethos.model.players.combat.range.RangeData;

public class DragonKnife extends Special {

	public DragonKnife() {
		super(2.5, 1.00, 1.00, new int[] { 22804 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		//player.gfx100(699);
		player.startAnimation(0x814);
		player.projectileStage = 1;
		player.gfx100(player.getCombat().getRangeStartGFX());
		if (player.playerIndex > 0 && target instanceof Player) {
			player.getItems().dropArrowPlayer();
			RangeData.fireProjectileNpc(player, (NPC) target, 50, 70, 699, 28, 28, 37, 10);
			RangeData.fireProjectileNpc(player, (NPC) target, 50, 70, 699, 28, 28, 37, 10);
		} else if (player.npcIndex > 0 && target instanceof NPC) {
			player.getItems().dropArrowNpc((NPC) target);
			RangeData.fireProjectileNpc(player, (NPC) target, 50, 70, 699, 28, 28, 37, 10);
			RangeData.fireProjectileNpc(player, (NPC) target, 50, 70, 699, 28, 28, 37, 10);
		}
		player.getItems().deleteArrow();
		player.getItems().deleteArrow();
		if (player.fightMode == 2)
			player.attackTimer--;
		
		if (target instanceof NPC) {
			AttackNPC.calculateCombatDamage(player, (NPC) target, CombatType.RANGE, null);
		} else if (target instanceof Player) {
			AttackPlayer.calculateCombatDamage(player, (Player) target, CombatType.RANGE, null);
		}
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
