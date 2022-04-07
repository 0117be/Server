package ethos.model.npcs.bosses.vorkath.impl;

import java.util.ArrayList;
import java.util.List;

import ethos.clip.Region;
import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.npcs.NPCHandler;
import ethos.model.npcs.bosses.vorkath.VorkathAttack;
import ethos.model.npcs.bosses.vorkath.VorkathInstance;
import ethos.model.players.Player;
import ethos.model.players.combat.CombatType;
import ethos.model.players.combat.Hitmark;
import ethos.util.Misc;

public class PoolAttack implements VorkathAttack{

	public boolean finished = false;

	@Override
	public void execute(Player player) {
		if(player.debugMessage) {
			player.sendMessage("Attacking with poolattack"); //
		}
		NPCHandler.startAnimation(getAnimation(), player.getVorkath().getIndex());

		finished = false;

		List<int[]> pools = new ArrayList<int[]>();

		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
			int count = 0;

			@Override
			public void execute(CycleEventContainer container) {
				if(player.getVorkath().isDead)
				{
					finished = true;
					player.getVorkath().numberOfAttacks = 0;
					for(int[] point : pools)
					{
						player.getPA().removeObject(point[0], point[1]);
					}
					
					container.stop();
				}
				
				if(count == 0)
				{
					for(int i = 0; i < 100; i++)
					{
						int count = 0;

						while(true)
						{
							int x = Misc.random(2256, 2287);
							int y = Misc.random(4054, 4082);

							if(Region.getClipping(x, y, player.height) == 0)
							{
								int nX = player.getVorkath().absX + 2;
								int nY = player.getVorkath().absY + 2;
								int x1 = x + 1;
								int y1 = y + 2;
								int offY = (nX - x1) * -1;
								int offX = (nY - y1) * -1;

								player.getPA().createPlayersProjectile(nX, nY, offX, offY, 40, 100,
										1483, 31, 0, -1, 5);

								pools.add(new int[] {x,y});

								break;
							}

							if(count > 20)
								break;

							count++;
						}
					}

					count++;
				}
				else if(count == 1)
				{
					for(int[] point : pools)
					{
						player.getPA().object(32000, point[0], point[1], Misc.random(0,4)%4, 10);
					}
					container.stop();
				}
			}

		}, 3);

		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
			int count = 0;

			@Override
			public void execute(CycleEventContainer container) {
				count++;

				if(count <= 6)
					return;

				for(int[] point : pools)
				{
					if(player.absX == point[0] && player.absY == point[1])
					{
						int damage = Misc.random(1, 10);

						player.appendDamage(damage, Hitmark.HIT);

						player.getVorkath().getHealth().increase(damage);
					}
				}

				if(player != null && player.getVorkath() != null && player.getVorkath().isDead) //checks for nullpointer
				{
					finished = true;
					player.getVorkath().numberOfAttacks = 0;
					for(int[] point : pools)
					{
						player.getPA().removeObject(point[0], point[1]);
					}
					
					container.stop();
				}
				
				if(player.getVorkath() != null){
				//	NPCHandler.startAnimation(7957, player.getVorkath().getIndex());
				}
				//1482
				int x = player.absX;
				int y = player.absY;

				int nX = player.getVorkath().absX + 2;
				int nY = player.getVorkath().absY + 2;
				int x1 = player.absX + 1;
				int y1 = player.absY + 2;
				int offY = (nX - x1) * -1;
				int offX = (nY - y1) * -1;

				
				player.getPA().createPlayersProjectile(nX, nY, offX, offY, 40, 60,
						1482, 31, 0, -1, 5);
				
				CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						player.getPA().createPlayersStillGfx(131, x, y, player.heightLevel, 0);
						
						if(player.absX == x && player.absY == y)
						{
							player.appendDamage(Misc.random(1, 30), Hitmark.HIT);
						}
						
						container.stop();
					}

				}, 1);
				
				if(player.getVorkath().isDead)
				{
					finished = true;
					player.getVorkath().numberOfAttacks = 0;
					for(int[] point : pools)
					{
						player.getPA().removeObject(point[0], point[1]);
					}
					
					container.stop();
				}
				
				if(count > 36)
				{
					finished = true;
					player.getVorkath().numberOfAttacks = 0;
					for(int[] point : pools)
					{
						player.getPA().removeObject(point[0], point[1]);
					}
					
					container.stop();
				}
			}
		}, 1);
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
		return 7960;
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
