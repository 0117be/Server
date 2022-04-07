package ethos.model.npcs.bosses.vorkath.impl;

import java.util.Optional;

import ethos.model.entity.HealthStatus;
import ethos.model.npcs.NPCHandler;
import ethos.model.npcs.bosses.vorkath.VorkathAttack;
import ethos.model.players.Player;
import ethos.model.players.combat.CombatType;

public class VenomAttack implements VorkathAttack{
	@Override
	public void execute(Player player) {
		if(player.debugMessage) {
			player.sendMessage("Attacking with venomattack");
		}
		NPCHandler.startAnimation(getAnimation(), player.getVorkath().getIndex());
		
		int nX = NPCHandler.npcs[player.getVorkath().getIndex()].getX() + NPCHandler.offset(player.getVorkath().getIndex());
		int nY = NPCHandler.npcs[player.getVorkath().getIndex()].getY() + NPCHandler.offset(player.getVorkath().getIndex());
		int pX = player.getX();
		int pY = player.getY();
		int offX = (nX - pX) * -1;
		int offY = (nY - pY) * -1;
		int centerX = nX + NPCHandler.npcs[player.getVorkath().getIndex()].getSize() / 2;
		int centerY = nY + NPCHandler.npcs[player.getVorkath().getIndex()].getSize() / 2;
		player.getPA().createPlayersProjectile(centerX, centerY, offX, offY, 50, 85,
				getProjectileId(), 60,
				15, -player.getIndex() - 1, 65,
				0);
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MAGE;
	}

	@Override
	public int getProjectileId() {
		return 1470;
	}

	@Override
	public int getEndProjectileId() {
		return 1472;
	}

	@Override
	public int getAnimation() {
		return 7952;
	}

	@Override
	public void executeAfterhit(Player player) {
		player.gfx(getEndProjectileId(), 65);
		player.getHealth().proposeStatus(HealthStatus.VENOM, 6, Optional.of(player.vorkath));
	}

	@Override
	public boolean appliesDamage(Player player) {
		return true;
	}

	@Override
	public int getMaxHit() {
		return 30;
	}

}
