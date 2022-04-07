package ethos.model.players.combat.monsterhunt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import ethos.Server;
import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.content.ShootingStar.CrashedStar;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.net.discord.DiscordMessager;
import ethos.util.Misc;
import ethos.world.objects.GlobalObject;

/**
 * MonsterHunt.java
 * 
 * @author Jashy
 *
 * Re-written to make it cleaner - @Emre
 * 
 * A class to spawn NPC's at different locations in the wilderness.
 *
 */

public class MonsterHunt {

	public enum Npcs {
               //NAME(NPC ID, "WHAT YOU WANT NPC TO BE CALLED" ,HP ,MAXHIT, ATTACK, DEFENCE);
		TEKTON(7542, "Tekton", 2500, 47, 350, 350),
		ICE_QUEEN(4922, "Ice Queen", 1200, 55, 350, 150),
		VESPULA(7531, "Vespula", 2000, 42, 350, 400);
		//GLOD(5129, "Glod", 3000, 20, 350, 100);

		private final int npcId;

		private final String monsterName;

		private final int hp;

		private final int maxHit;

		private final int attack;

		private final int defence;

		private Npcs(final int npcId, final String monsterName, final int hp, final int maxHit, final int attack, final int defence) {
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
	
	private static MonsterHuntLocation[] locations =
			new MonsterHuntLocation[]{new MonsterHuntLocation(3503, 3284, "west of the Barrows mounds"), 
									 new MonsterHuntLocation(2656, 3617, "south of Rellekka"),
									 new MonsterHuntLocation(3237, 3646, "north of the Chaos Altar @red@(16)"),
									 new MonsterHuntLocation(3306, 3934, "east side of the Rogue's Castle @red@(52)"),
									 new MonsterHuntLocation(3255, 3875, "west of the Demonic Ruins @red@(45)"),
									 new MonsterHuntLocation(3305, 3667, "west of the East Dragons teleport @red@(19)")};

	private static MonsterHuntLocation currentLocation;
			
	private static String name;
	
	public static void spawnNPC() {
		CycleEventHandler.getSingleton().addEvent(spawned, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if(spawned) {
					NPCHandler.kill(npcType, 0);
					spawned = false;
					currentLocation = null;
			//		DiscordMessager.sendAnnouncement("[PVM] Galvek has despawned! Better luck next time!");
					return;
				}
				List<MonsterHuntLocation> locationsList = Arrays.asList(locations);
				MonsterHuntLocation randomLocation = Misc.randomTypeOfList(locationsList);
				currentLocation = randomLocation;
				List<Npcs> npcs = new ArrayList<>(EnumSet.allOf(Npcs.class));
				Npcs randomNpc = Misc.randomTypeOfList(npcs);
				name = randomNpc.getMonsterName();
				npcType = randomNpc.getNpcId();

				NPCHandler.spawnNpc(randomNpc.getNpcId(), randomLocation.getX(), randomLocation.getY(), 0, 1, randomNpc.getHp(), randomNpc.getMaxHit(), randomNpc.getAttack(), randomNpc.getDefence()/*, false*/);
				//PlayerHandler.executeGlobalMessage("@red@The " + randomNpc.getMonsterName() + " has been spotted near " + randomLocation.getLocationName() + ".");
				PlayerHandler.executeGlobalMessage("<shad=7000>@red@[PVM Event]@cr33@<col=E70202>" + randomNpc.getMonsterName() + " has been spotted "+ randomLocation.getLocationName() +"!");
				//DiscordMessager.sendAnnouncement("[PVM] " + randomNpc.getMonsterName() + " has been spotted "+ randomLocation.getLocationName() +"!");
				spawned = true;
				if (Misc.random(3) == 1) {	
					Server.getGlobalObjects().add(new GlobalObject(30013, 3111, 3504, 0, 0, 10, -1, -1));
					//Server.getGlobalObjects().add(new GlobalObject(30013, 3111, 3504, 0, 0, 10, 14, 30013));
					PlayerHandler.executeGlobalMessage("<shad=7000>@gre@[Skilling Event]@cr20@@gre@" + " A meat tree has been spotted north-east at home!");
				}
		}
	}, 3000); //3000//was5000 THIS NUMBER HANDLES HOW OFTEN THE NEW NPC IS SPAWNED AND THE OLD ONE IS KILLED (IN TICKS)
}                   // IT ALSO DETERMINES HOW MANY TICKS FROM SERVER START UNTIL THE FIRST NPC IS SPAWNED.

	public static MonsterHuntLocation getCurrentLocation() {
		return currentLocation;
	}
	public static void teleGalvek (Player player) {
		if (currentLocation != null) {
			player.getPA().spellTeleport(3164, 3480, 2, false);
		} else if (currentLocation == null) {
			player.sendMessage("You can only use this when Galvek is around..");
		}
	}
	public static void setCurrentLocation(MonsterHuntLocation currentLocation) {
		MonsterHunt.currentLocation = currentLocation;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		MonsterHunt.name = name;
	}
	
	
}