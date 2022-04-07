package ethos.model.npcs.bosses.vorkath.impl;

import java.util.concurrent.TimeUnit;

import ethos.Server;
import ethos.clip.Region;
import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.npcs.NPCHandler;
import ethos.model.npcs.bosses.vorkath.VorkathAttack;
import ethos.model.npcs.bosses.vorkath.VorkathInstance;
import ethos.model.players.ClientGameTimer;
import ethos.model.players.Player;
import ethos.model.players.combat.CombatType;
import ethos.util.Misc;

public class SpawnAttack implements VorkathAttack{

	public boolean finished = false;

	@Override
	public void execute(Player player) {
		if(player.debugMessage) {
			player.sendMessage("Attacking with spawnattack");
		}
		NPCHandler.startAnimation(getAnimation(), player.getVorkath().getIndex());
		
		finished = false;
		
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

		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
			int count = 0;
			
			int zombieX, zombieY;
			
			@Override
			public void execute(CycleEventContainer container) {
				if(count == 0)
				{
					player.gfx(369, 45);
					player.freezeTimer = 20;
					player.sendMessage("@red@You have been frozen!");
					player.getPA().sendGameTimer(ClientGameTimer.FROZEN, TimeUnit.SECONDS, 20);
					count++;
				}
				else if(count == 1)
				{
					NPCHandler.startAnimation(7960, player.getVorkath().getIndex());
					count++;
				}
				else if(count == 2)
				{
					int nX = player.getVorkath().absX + 2;
					int nY = player.getVorkath().absY + 2;
					
					int[] loc = getRandomLocation(player);
					
					int x1 = loc[0] + 1;
					int y1 = loc[1] + 2;
					int offY = (nX - x1) * -1;
					int offX = (nY - y1) * -1;

					zombieX = loc[0];
					zombieY = loc[1];
					
					player.getPA().createPlayersProjectile(nX, nY, offX, offY, 40, 60,
							1479, 31, 0, -1, 5);
					
					count++;
				}
				else if(count == 3)
				{
					Server.npcHandler.spawnNpc(player, 8062, zombieX, zombieY, player.heightLevel, 0, 38, 60, 0, -100, true, false);
					finished = true;
					player.getVorkath().numberOfAttacks = 0;
					container.stop();
				}
			}

		}, 3);
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.SPECIAL;
	}

	@Override
	public int getProjectileId() {
		return 395;
	}

	@Override
	public int getEndProjectileId() {
		return 0;
	}

	@Override
	public int getAnimation() {
		return 7952;
	}

	@Override
	public void executeAfterhit(Player player) {
	}

	@Override
	public boolean appliesDamage(Player player) {
		return false;
	}

	@Override
	public int getMaxHit() {
		return 0;
	}

	public int[] getRandomLocation(Player player)
	{
		int count = 0;
		while(true)
		{
			int x = Misc.random(player.absX - 10, player.absX + 10);
			int y = Misc.random(player.absY - 10, player.absY + 10);
			
			if(Region.getClipping(x, y, player.height) == 0 && VorkathInstance.inVorkath(player) && getDistance(player.absX, player.absY, x, y) > 6)
			{
				return new int[] {x, y};
			}
			
			if(count > 100)
				return new int[] {0,0};
			
			count++;
		}
	}
	
    /**
     * Gets the distance between this position and another position. Only X and
     * Y are considered (i.e. 2 dimensions).
     *
     * @param other The other position.
     * @return The distance.
     */
    public int getDistance(int x, int y, int x1, int y1) {
        int deltaX = x1 - x;
        int deltaY = y1 - x;
        return (int) Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
    }
	
}
