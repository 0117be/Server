package ethos.model.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ethos.Server;
import ethos.clip.Region;
import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.Position;
import ethos.model.players.combat.AttackPlayer;
import ethos.model.players.combat.CombatType;
import ethos.model.players.combat.Damage;
import ethos.model.players.combat.Hitmark;
import ethos.util.Misc;
import ethos.world.objects.GlobalObject;


public class Dwarfcannon {

	private static final int MAX_DAMAGE = 29;

	private static final int MAX_RANGE = 8;

	private static final int DISTANCE_TO_BUILD = 10;

	private static List<GlobalObject> cannons = new ArrayList<GlobalObject>();

	private Player player;

	private Setup stage = Setup.NO_CANNON;

	private Rotation rotation;

	public GlobalObject cannon;

	private int balls;

	private boolean shooting;

	public Dwarfcannon(Player player) {
		this.player = player;
	}

	private enum Setup {
		NO_CANNON, BASE, STAND, BARRELS, COMPLETE
	}

	private enum Rotation {
		NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST
	}

	public boolean canSetUp() {
		if (Boundary.isIn(player, Boundary.CATACOMBS))
			return false;
		if (Boundary.isIn(player, Boundary.SLAYER_TOWER_BOUNDARY))
			return false;
		if (Boundary.isIn(player, Boundary.FIGHT_CAVE))
			return false;
		if (Boundary.isIn(player, Boundary.DAGANNOTH_KINGS))
			return false;
		if (Boundary.isIn(player, Boundary.KBD_AREA))
			return false;
		if (Boundary.isIn(player, Boundary.KALPHITE_QUEEN) && player.heightLevel != 2)
			return false;//thermo
		if (Boundary.isIn(player, Boundary.DEMONIC_RUINS_BOUNDARY))
			return false;
		if (Boundary.isIn(player, Boundary.DEMONIC))
			return false;
		if (Boundary.isIn(player, Boundary.EDGEVILLE_PERIMETER))
			return false;
		if (Boundary.isIn(player, Boundary.PEST_CONTROL_AREA))
			return false;
		if (Boundary.isIn(player, Boundary.GODWARS_BOSSROOMS))
			return false;
		if (Region.getClipping(player.absX, player.absY, player.heightLevel) != 0
				|| Server.getGlobalObjects().anyExists(player.absX, player.absY, player.heightLevel) || player.inBank()
				|| Boundary.isIn(player, Boundary.DUEL_ARENA) || Boundary.isIn(player, Boundary.HALLOWEEN_ORDER_MINIGAME)) {
			return false;
		}
		return true;
	}
	public void setup() {
		
		if (!canSetUp()) {
			player.sendMessage("You cannot set up your cannon here.");
			return;
		}

		findCannon();
		
		if (stage.ordinal() != 0) {
			player.sendMessage("You have already started setting up a cannon!");
			return;
		}

		for (GlobalObject other : cannons) {
			if (other.getPosition().withinDistance(new Position(player.absX, player.absY, player.heightLevel),
					DISTANCE_TO_BUILD)) {
				player.sendMessage("You are trying to build too close to another cannon!");
				return;
			}
		}

		if (player.getItems().playerHasItem(6, 1) && player.getItems().playerHasItem(8, 1)
				&& player.getItems().playerHasItem(10, 1) && player.getItems().playerHasItem(12, 1)) {

			player.stopMovement();

			player.startAnimation(827);
			GlobalObject base = new GlobalObject(player, 7, player.absX, player.absY, player.heightLevel);
			player.turnPlayerTo(base.getPosition().getX(), base.getPosition().getY());
			Server.getGlobalObjects().add(base);
			player.getItems().deleteItem(6, 1);
			cannons.add(base);
			cannon = base;
			stage = Setup.BASE;

			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (player.disconnected) {
						container.stop();
						return;
					}

					player.startAnimation(827);
					GlobalObject stand = new GlobalObject(player, 8, player.absX, player.absY, player.heightLevel);
					Server.getGlobalObjects().replace(cannon, stand);
					player.getItems().deleteItem(8, 1);
					cannon = stand;
					stage = Setup.STAND;
					container.stop();
				}
			}, 3);

			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (player.disconnected) {
						container.stop();
						return;
					}

					player.startAnimation(827);
					GlobalObject newCannon = new GlobalObject(player, 9, player.absX, player.absY, player.heightLevel);
					Server.getGlobalObjects().replace(cannon, newCannon);
					player.getItems().deleteItem(10, 1);
					cannon = newCannon;
					stage = Setup.BARRELS;
					container.stop();
				}
			}, 6);

			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (player.disconnected) {
						container.stop();
						return;
					}

					player.startAnimation(827);
					GlobalObject barrel = new GlobalObject(player, 6, player.absX, player.absY, player.heightLevel);
					Server.getGlobalObjects().replace(cannon, barrel);
					player.getItems().deleteItem(12, 1);
					cannon = barrel;
					stage = Setup.COMPLETE;
					container.stop();
				}
			}, 9);
		}
	}

	private void findCannon() {
		if (cannon == null)
			cannon = Server.getGlobalObjects().findCannon(player.getName());
		else
			return;
		if (cannon == null)
			return;
		else if (cannon.getObjectId() == 6)
			stage = Setup.COMPLETE;
		else if (cannon.getObjectId() == 7)
			stage = Setup.BASE;
		else if (cannon.getObjectId() == 8)
			stage = Setup.STAND;
		else if (cannon.getObjectId() == 9)
			stage = Setup.BARRELS;
	}
	
	public void pickup(Position p) {
		if (isBulding())
			return;
		findCannon();
		if (cannon == null)
			return;
		if (cannon.getPosition().getX() == p.getX() && cannon.getPosition().getY() == p.getY()
				&& cannon.getPosition().getHeight() == p.getHeight()) {

			shooting = false;
			player.startAnimation(827); 
			Server.getGlobalObjects().remove(cannon);
			remove();
			cannon = null; 
			player.getItems().addItemUnderAnyCircumstance(6, 1);
			if (stage == Setup.STAND || stage == Setup.BARRELS || stage == Setup.COMPLETE)
				player.getItems().addItemUnderAnyCircumstance(8, 1);
			if (stage == Setup.BARRELS || stage == Setup.COMPLETE)
				player.getItems().addItemUnderAnyCircumstance(10, 1);
			if (stage == Setup.COMPLETE)
				player.getItems().addItemUnderAnyCircumstance(12, 1);

			if (balls > 0) {
				player.getItems().addItemUnderAnyCircumstance(2, balls);
				balls = 0;
			}

			player.sendMessage("You have picked up your cannon!");
			stage = Setup.NO_CANNON;
		} else {
			player.sendMessage("This is not your cannon to pickup!");
		}
	}

	public void shoot(Position p) {
		findCannon();
		if (cannon == null)		
			return;
		if (cannon.getPosition().getX() == p.getX() && cannon.getPosition().getY() == p.getY()
				&& cannon.getPosition().getHeight() == p.getHeight()) {

			if (shooting) {
				player.sendMessage("Your cannon is already firing!");
				System.out.println("Cannon Is already Firing");
				return;
			}

			if (balls < 1) {
				int amountOfCannonBalls = Math.min(player.getItems().getItemAmount(2), 30);

				if (amountOfCannonBalls < 1) {
					player.sendMessage("You need ammo to shoot this cannon!");
					return;
				}

				balls = amountOfCannonBalls;
				player.getItems().deleteItem(2, amountOfCannonBalls);
				shooting = true;

				
				
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (player.disconnected || !shooting) {
							container.stop();
							//System.out.println("Not shooting");
							return;
						}

						if (balls < 1) {
							player.sendMessage("Your cannon has run out of ammo!");
							System.out.println("No more ammo");
							shooting = false;
							container.stop();
							return;
						} else {
							if (rotation == null) {
								System.out.println("@red@Reload npcs rotate");
				                reloadNPCs();
							//	rotation = Rotation.NORTH;
							//	rotate(cannon);
								fireAtMobs();
								return;
							}

							switch (rotation) {
							case NORTH: // north
								rotation = Rotation.NORTH_EAST;
								break;
							case NORTH_EAST: // north-east
								rotation = Rotation.EAST;
								break;
							case EAST: // east
								rotation = Rotation.SOUTH_EAST;
								break;
							case SOUTH_EAST: // south-east
								rotation = Rotation.SOUTH;
								break;
							case SOUTH: // south
								rotation = Rotation.SOUTH_WEST;
								break;
							case SOUTH_WEST: // south-west
								rotation = Rotation.WEST;
								break;
							case WEST: // west
								rotation = Rotation.NORTH_WEST;
								break;
							case NORTH_WEST: // north-west
								rotation = null;
								break;
							}

							rotate(cannon);
							fireAtMobs();
						}
					}
				}, 1);
			}
		} else {
			player.sendMessage("This is not your cannon to fire!");
		}
	}

    private void rotate(GlobalObject cannon2) {
        switch (rotation) {
            case NORTH: // north
                player.getPA().object(cannon2.getPosition().getX(), cannon2.getPosition().getY(), 516, 10, -1);
            	break;
            case NORTH_EAST: // north-east
                player.getPA().object(cannon2.getPosition().getX(), cannon2.getPosition().getY(), 517, 10, -1);
            	break;
            case EAST: // east
                player.getPA().object(cannon2.getPosition().getX(), cannon2.getPosition().getY(), 518, 10, -1);
            	break;
            case SOUTH_EAST: // south-east
                player.getPA().object(cannon2.getPosition().getX(), cannon2.getPosition().getY(), 519, 10, -1);
            	break;
            case SOUTH: // south
                player.getPA().object(cannon2.getPosition().getX(), cannon2.getPosition().getY(), 520, 10, -1);
                break;
            case SOUTH_WEST: // south-west
                player.getPA().object(cannon2.getPosition().getX(), cannon2.getPosition().getY(), 521, 10, -1);
            	break;
            case WEST: // west
                player.getPA().object(cannon2.getPosition().getX(), cannon2.getPosition().getY(), 514, 10, -1);
                break;
            case NORTH_WEST: // north-west
                player.getPA().object(cannon2.getPosition().getX(), cannon2.getPosition().getY(), 515, 10, -1);
            	rotation = null;
                break;
        }
    }
    private void reloadNPCs() {
		for (int i = 0; i < NPCHandler.npcs.length; i++) {
			if (NPCHandler.npcs[i] == null) {
				continue;
			}
			if (Misc.distanceBetween(NPCHandler.npcs[i], cannon) > MAX_RANGE * 2)
				continue;
			npcs.add(NPCHandler.npcs[i]);
		}
    }
	private void fireAtMobs() {

		NPC hit = targetMob(player, new Position(cannon.getPosition().getX(), cannon.getPosition().getY(),
				cannon.getPosition().getHeight()));

		int damage = Misc.random(MAX_DAMAGE)+1;

		if (hit != null) {
			if (player.inMulti() && Boundary.isIn(player, Boundary.WILDERNESS)) {
				cannonProjectile(player, cannon, hit);
				hit.hitDiff = damage;
				hit.hitUpdateRequired = true;
				System.out.println("@red@Test 1");
				AttackPlayer.addCombatXP(player, CombatType.DWARF_CANNON, damage);
				
				player.getDamageQueue().add(new Damage(hit, damage, 3, 
						player.playerEquipment, Hitmark.HIT, CombatType.RANGE));
				hit.killerId = player.getIndex();
				
			} else {
				if (hit.underAttack && hit.killerId != player.getIndex())
					return;
				cannonProjectile(player, cannon, hit);
				hit.hitDiff = damage;
				AttackPlayer.addCombatXP(player, CombatType.RANGE, damage);
				System.out.println("@red@Test 2");
				player.getDamageQueue().add(new Damage(hit, damage, 3, 
						player.playerEquipment, Hitmark.HIT, CombatType.RANGE));
				hit.killerId = player.getIndex();
			}

			balls--;
		}
	}

	public ArrayList<NPC> npcs = new ArrayList<NPC>();
	
	private NPC targetMob(Player player, Position p) {
		for (NPC npc : npcs) {
			if (npc == null)
				continue;
			if (npc != NPCHandler.npcs[npc.getIndex()])
				continue;
			if (Misc.distanceBetween(npc, cannon) > MAX_RANGE)
				continue;
			int myX = cannon.getPosition().getX();
			int myY = cannon.getPosition().getY();
			int theirX = npc.absX;
			int theirY = npc.absY;

			Position npcPos = new Position(theirX, theirY);

			if (!npc.isDead && npc.heightLevel == p.getHeight() && !npc.isDead && npc.getHealth().getAmount() > 0
					&& npc.npcType != 1266 && npc.npcType != 1268
					&& npcPos.withinDistance(new Position(myX, myY), MAX_RANGE)) {

				if (!player.inMulti())
					if (player.underAttackBy2 > 0)
						if (npc.getIndex() != player.underAttackBy2 && 
							!NPCHandler.npcs[player.underAttackBy2].isDead &&
								NPCHandler.npcs[player.underAttackBy2].getHealth().getAmount() > 0)
							continue;
				if (rotation == null) {
					rotation = Rotation.NORTH;
				}

				switch (rotation) {
				case NORTH:
					if (theirY > myY && theirX >= myX - 1 && theirX <= myX + 1)
						return npc;
					break;
				case NORTH_EAST:
					if (theirX >= myX + 1 && theirY >= myY + 1)
						return npc;
					break;
				case EAST:
					if (theirX > myX && theirY >= myY - 1 && theirY <= myY + 1)
						return npc;
					break;
				case SOUTH_EAST:
					if (theirY <= myY - 1 && theirX >= myX + 1)
						return npc;
					break;
				case SOUTH:
					if (theirY < myY && theirX >= myX - 1 && theirX <= myX + 1)
						return npc;
					break;
				case SOUTH_WEST:
					if (theirX <= myX - 1 && theirY <= myY - 1)
						return npc;
					break;
				case WEST:
					if (theirX < myX && theirY >= myY - 1 && theirY <= myY + 1)
						return npc;
					break;
				case NORTH_WEST:
					if (theirX <= myX - 1 && theirY >= myY + 1)
						return npc;
					break;
				}
			}
		}
		return null;
	}

	private void cannonProjectile(Player player, GlobalObject cannon, NPC n) {
		int oX = cannon.getPosition().getX();
		int oY = cannon.getPosition().getY();
		int offX = ((oX - n.absX) * -1);
		int offY = ((oY - n.absY) * -1);

		player.getPA().createPlayersProjectile(oX, oY, offY, offX, 50, 60, 53, 20, 20, -player.oldNpcIndex + 1, 30);
	}

	public boolean isBulding() {
		if (stage.equals(Setup.NO_CANNON) || stage.equals(Setup.COMPLETE))
			return false;
		else
			return true;
	}

	public void remove() {
		for (Iterator<GlobalObject> iter = cannons.iterator(); iter.hasNext();) {
			GlobalObject m = iter.next();

			if (m == null) {
				continue;
			}

			if (m.owner.equals(player.getName())) {
				iter.remove();
			}
		}
	}

	public List<GlobalObject> getCannons() {
		return cannons;
	}
}