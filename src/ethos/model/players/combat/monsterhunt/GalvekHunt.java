package ethos.model.players.combat.monsterhunt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.net.discord.DiscordMessager;
import ethos.util.Misc;

/**
 * GalvekHunt.java
 * 
 * @author Robbie
 * 
 * A class to spawn Galvek at different locations in the world.
 *
 */

public class GalvekHunt {
	private static final int GALVEK_ID = 8095;

	public enum Npc {
        //NAME(NPC ID, "WHAT YOU WANT NPC TO BE CALLED" ,HP ,MAXHIT, ATTACK, DEFENCE);
		GALVEK(8095, "Galvek", 3200, 75, 450, 425);
		//GALVEK2(8095, "Galvek", 3200, 75, 450, 425);

		private final int npcId;

		private final String monsterName;

		private final int hp;

		private final int maxHit;

		private final int attack;

		private final int defence;

		private Npc(final int npcId, final String monsterName, final int hp, final int maxHit, final int attack, final int defence) {
			this.npcId = npcId;
			this.monsterName = monsterName;
			this.hp = hp;
			this.maxHit = maxHit;
			this.attack = attack;
			this.defence = defence;
		}

		public int getNpcId() {
			return npcId;
		}

		public String getMonsterName() {
			return monsterName;
		}

		public int getHp() {
			return hp;
		}

		public int getMaxHit() {
			return maxHit;
		}

		public int getAttack() {
			return attack;
		}

		public int getDefence() {
			return defence;
		}
	}

	/**
	 * The spawnNPC method which handles the spawning of the NPC and the global
	 * message sent.
	 * 
	 * @param c
	 */

	private static boolean spawned;
	
	private static int npcType;
	
	private static GalvekHuntLocation[] locations =
			new GalvekHuntLocation[]{new GalvekHuntLocation(3503, 3284, 0, "east of the Barrows mounds"), 
									 new GalvekHuntLocation(2626, 3120, 0, "north-east of Yanille"),
									 new GalvekHuntLocation(2656, 3617, 0, "south of Rellekka")};

	private static GalvekHuntLocation currentLocation;
			
	private static String name;
	
	public static void spawnNPC() {
		CycleEventHandler.getSingleton().addEvent(spawned, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if(spawned) {
					NPCHandler.kill(npcType, 2);
					spawned = false;
					currentLocation = null;
					DiscordMessager.sendAnnouncement("```[PVM] Galvek has despawned! Better luck next time!```");
					return;
				}
				List<GalvekHuntLocation> locationsList = Arrays.asList(locations);
				GalvekHuntLocation randomLocation = Misc.randomTypeOfList(locationsList);
				currentLocation = randomLocation;
				List<Npc> npc = new ArrayList<>(EnumSet.allOf(Npc.class));
				Npc galvek = Npc.GALVEK;
				NPCHandler.spawnNpc(GALVEK_ID, randomLocation.getX(), randomLocation.getY(), 2, 1, galvek.getHp(), galvek.getMaxHit(), galvek.getAttack(), galvek.getDefence()/*, false*/);
				//List<GalvekHuntLocation> locationsList = Arrays.asList(locations);
				//GalvekHuntLocation randomLocation = Misc.randomTypeOfList(locationsList);
				//currentLocation = randomLocation;
				//List<Npcs> npcs = new ArrayList<>(EnumSet.allOf(Npcs.class));
				//Npcs galvek = Misc.randomTypeOfList(npcs);
				//name = galvek.getMonsterName();
				//npcType = galvek.getNpcId();
			//	NPCHandler.spawnNpc(galvek.getNpcId(), randomLocation.getX(), randomLocation.getY(), 0, 1, galvek.getHp(), galvek.getMaxHit(), galvek.getAttack(), galvek.getDefence()/*, false*/);
				PlayerHandler.executeGlobalMessage("<col=6666FF>" + galvek.getMonsterName() + " has been spotted "+ randomLocation.getLocationName() +"!");
				DiscordMessager.sendAnnouncement("```[PVM] " + galvek.getMonsterName() + " has been spotted "+ randomLocation.getLocationName() +"!```");
				spawned = true;
			}
		}, 750/*6000*/); //3000 THIS NUMBER HANDLES HOW OFTEN THE NEW NPC IS SPAWNED AND THE OLD ONE IS KILLED (IN TICKS)
	}            // IT ALSO DETERMINES HOW MANY TICKS FROM SERVER START UNTIL THE FIRST NPC IS SPAWNED.

	public static void teleGalvek (Player player) {
		if (currentLocation != null) {
			player.getPA().spellTeleport(3164, 3480, 2, false);
		} else if (currentLocation == null) {
			player.sendMessage("You can only use this when Galvek is around..");
		}
	}
	public static GalvekHuntLocation getCurrentLocation() {
		return currentLocation;
	}

	public static void setCurrentLocation(GalvekHuntLocation currentLocation) {
		GalvekHunt.currentLocation = currentLocation;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		GalvekHunt.name = name;
	}
	
	
}