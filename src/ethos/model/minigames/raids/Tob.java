package ethos.model.minigames.raids;

import ethos.Server;
import ethos.clip.doors.Location;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.content.bonus.DoubleRaidSaturday;
import ethos.model.items.Item;
import ethos.model.items.ItemDefinition;

import ethos.model.npcs.NPCDefinitions;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.net.discord.DiscordMessager;
import ethos.util.Misc;
import ethos.world.objects.GlobalObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Author @ Goon_
 * www.rune-server.com
 */

public class Tob {
	/**
	 * The player
	 */
	Player player;

	/**
	 * The Tob leader
	 */
	public Player tobLeader;


	/**
	 * Tob points
	 */
	private int tobPoints;

	/**
	 * Group points
	 */
	public int groupPoints;

	/**
	 * Tob Group
	 */
	Player[] tobGroup;

	/**
	 * The current path
	 */
	private int path;

	/**
	 * The current way
	 */
	private int way;

	/**
	 * Current room
	 */
	public int currentRoom;


	/**
	 * Reached room
	 */
	public int reachedRoom;
	/**
	 * Monster spawns (No Double Spawning)
	 */
	public boolean maiden = false;
	public boolean bloat = false;
	public boolean nylo = false;
	public boolean sotet = false;
	public boolean xarpus = false;
	public boolean vitur = false;
	public boolean viturdead = false;



	
	/**
	 * The door location of the current paths
	 */
	private ArrayList<Location> roomPaths= new ArrayList<Location>();

	/**
	 * The names of the current rooms in path
	 */
	private  ArrayList<String> roomNames = new ArrayList<String>();

	/**
	 * Current monsters needed to kill
	 */
	private int mobAmount = 0;

	/**
	 * Constructs the tob class for the player
	 * @param player The player
	 */
	public Tob(Player player) {
		this.player=player;
	}

	/**
	 * Gets the height for the Tob
	 * @return the height
	 */
	public int getHeight(Player player) {
		return tobLeader.getIndex()*4;
	}

	/**
	 * Get points
	 */
	public int getPoints() {
		return tobPoints;
	}
	/**
	 * Add points
	 */
	public void addPoints(int points) {
		tobPoints += points;
	}

	/**
	 * Gets the current path
	 * @return the path
	 */
	public int getPath() {
		return path;
	}

	/**
	 * Gets the current way
	 * @return the way
	 */
	public int getWay() {
		return way;
	}

	/**
	 * Sets the current path
	 * @param path
	 */
	public void setPath(int path) {
		this.path=path;
	}

	/**
	 * Gets the start location for the path
	 * @return path
	 */
	public Location getStartLocation() {
		switch(path) {
			case 0:
				return TobRooms.STARTING_ROOM.doorLocation;
		}
		return TobRooms.STARTING_ROOM2.doorLocation;
	}
	/**
	 * Handles TOB rooms
	 * @author Bandit
	 *
	 */
	public enum TobRooms{
		STARTING_ROOM("start_room",1,0,new Location(3219, 4460)),
		MAIDEN("maiden",1,0,new Location(3184,4447,4)),
		BLOAT("bloat",1,0,new Location(3322,4448,0)),
		NYLO("nylo",1,0,new Location(3303,4276,0)),
		SOTET("sotet",1,0,new Location(3280,4293,0)),
		XARPUS("xarpus",1,0,new Location(3170,4375,1)),		
		VITUR("vitur",1,0,new Location(3168,4303,0)),
		
		
		STARTING_ROOM2("start_room",1,1,new Location(3219, 4460)),
		MAIDEN2("maiden",1,1,new Location(3184,4447,4)),
		BLOAT2("bloat",1,1,new Location(3322,4448,0)),
		NYLO2("nylo",1,1,new Location(3303,4276,0)),
		SOTET2("sotet",1,1,new Location(3280,4293,0)),
		XARPUS2("xarpus",1,1,new Location(3170,4375,1)),
		VITUR2("vitur",1,1,new Location(3168,4303,0));
		

		

		private Location doorLocation;
		private int path;
		private int way;
		private String roomName;

		private TobRooms(String name,int path1,int way1,Location door) {
			doorLocation=door;
			roomName=name;
			path=path1;
			way=way1;

		}

		public Location getDoor() {
			return doorLocation;
		}

		public int getPath() {
			return path;
		}
		public int getWay() {
			return way;
		}
		public String getRoomName() {
			return roomName;
		}


	}

	public void updateTobPoints() {
		player.getPA().sendFrame126("Total: @whi@"+groupPoints,17502);
		player.getPA().sendFrame126(player.playerName+": @whi@"+tobPoints,17503);
	}
	

	/**
	 * Kill all spawns for the Tob leader if left
	 * @param player
	 */
	public void killAllSpawns(Player player) {
		NPCHandler.kill(394, player.getTob().getHeight(player.getTob().tobLeader)); 
		NPCHandler.kill(8360, player.getTob().getHeight(player.getTob().tobLeader)); 
		NPCHandler.kill(8366, player.getTob().getHeight(player.getTob().tobLeader)); 
		NPCHandler.kill(8366, player.getTob().getHeight(player.getTob().tobLeader));
		NPCHandler.kill(8366, player.getTob().getHeight(player.getTob().tobLeader));
		NPCHandler.kill(8366, player.getTob().getHeight(player.getTob().tobLeader));
		NPCHandler.kill(8359, player.getTob().getHeight(player.getTob().tobLeader));
		NPCHandler.kill(8355, player.getTob().getHeight(player.getTob().tobLeader));
		NPCHandler.kill(8357, player.getTob().getHeight(player.getTob().tobLeader));
		NPCHandler.kill(8388, player.getTob().getHeight(player.getTob().tobLeader));
		NPCHandler.kill(8340, player.getTob().getHeight(player.getTob().tobLeader));
		NPCHandler.kill(8372, player.getTob().getHeight(player.getTob().tobLeader));
		NPCHandler.kill(8374, player.getTob().getHeight(player.getTob().tobLeader));
		
	}

	/**
	 * Leaves the Tob.
	 * @param player
	 */
	public void leaveGame(Player player) {
		if (System.currentTimeMillis() - player.infernoLeaveTimer < 15000) {
			player.sendMessage("You cannot leave yet, wait a couple of seconds and try again.");
			return;
		}
		player.sendMessage("@red@You have left the Theatre of Blood.");
		player.getPA().movePlayer(3676, 3219, 0);
		killAllSpawns(player);
		roomNames = new ArrayList<String>();
		roomPaths= new ArrayList<Location>();
		currentRoom = 0;
		mobAmount=0;
		reachedRoom = 0;
		tobLeader=null;	
		sotet = false;
		xarpus = false;
		maiden = false;
		bloat = false;
		nylo = false;
		vitur = false;
		viturdead = false;


	}
	/**
	 * Starts the Tob.
	 */
	public void startTob() {
		if (player.clan == null || !player.clan.isFounder(player.playerName)) {
			player.sendMessage("You're not in a clan that you own, and can not pass the door.");
			return;
		}

		int memberCount = player.clan.activeMembers.size();

		if (memberCount < 1) {
			player.sendMessage("You don't have enough people in your clan to start Tob.");
			return;
		}

		if (memberCount > 22) {
			player.sendMessage("Your clan exceeds the max limit of 22 players in Tob.");
			return;
		}
		

		tobLeader=player;
		int path1 = 1;
		int way1=Misc.random(1);
		path = path1;
		way=way1;
	//	tobPoints = 0;
		for (String username : player.clan.activeMembers) {
			Player p = PlayerHandler.getPlayer(username);
			p.getTob().tobLeader = player;
			p.getTob().path = path1;
			p.getTob().way= way1;
			//p.getTob().tobPoints=0;
			for(TobRooms room : TobRooms.values()) {
				if(room.getWay() == way) {
					p.getTob().roomNames.add(room.getRoomName());
					p.getTob().roomPaths.add(room.getDoor());
				}
			}
			p.getTob().updateTobPoints();
			p.getPA().movePlayer(getStartLocation().getX(),getStartLocation().getY(),getHeight(player));
			sotet = false;
			xarpus = false;
			maiden = false;
			bloat = false;
			nylo = false;
			vitur = false;
			viturdead = false;
			p.sendMessage("@red@Welcome to the Threatre of Blood!");
		}
	}

	int[] rarerewards = {  22326, 22327, 22328, 22324, 22322, 22323, 22325  };
	int[] commonrewards = {21009, 21028, 560,565,566,892,11212,3050,208,210,212,214,3052,216,2486,218,220,443, 454, 445, 448, 450, 452, 1624, 1622, 1620, 1618, 13391, 7937, 2722}; //{item, maxAmount}

	/**
	 * Handles giving the TOB reward
	 */
	public void giveReward() {
		int rewardChance = Misc.random(300);
		if(rewardChance >= 296) {
			giveRareReward();
		}else {
			giveCommonReward();
		}
		if (player.tobCount == 24) {
		//	player.getItems().addItemUnderAnyCircumstance(22388, 1);
		//	player.getItems().addItemUnderAnyCircumstance(22380, 1);
		}
		if (player.tobCount == 49) {
			//player.getItems().addItemUnderAnyCircumstance(22390, 1);
		}
		if (player.tobCount == 74) {
			//player.getItems().addItemUnderAnyCircumstance(22392, 1);
			//player.getItems().addItemUnderAnyCircumstance(22384, 1);
		}
		if (player.tobCount == 99) {
			//player.getItems().addItemUnderAnyCircumstance(22394, 1);
		}
		if (player.tobCount == 149) {
			//player.getItems().addItemUnderAnyCircumstance(22396, 1);
			//player.getItems().addItemUnderAnyCircumstance(22376, 1);
		}
		if (player.tobCount == 199) {
			//player.getItems().addItemUnderAnyCircumstance(22382, 1);
		}
		if (player.tobCount == 249) {
			//player.getItems().addItemUnderAnyCircumstance(22378, 1);
		}
	}

	/**
	 * Handles giving a rare tob reward.
	 */

	public void giveRareReward() {
		player.gfx0(1368);
		int rareitem = 0;
		rareitem = Misc.random(rarerewards.length-1);
		if(rareitem < 0) {
			rareitem = Misc.random(rarerewards.length);
		}
		player.tobReward[0][0] = rarerewards[rareitem];
		PlayerHandler.executeGlobalMessage("<shad=7000>[TOB]@red@ @cr36@ " + player.playerName + " has received a rare item @red@"+ ItemDefinition.forId(player.tobReward[0][0]).getName() + " from TOB!");
		player.talkingNpc = 8324;
		player.getDH().sendNpcChat("You are extremely lucky! Here is a " + ItemDefinition.forId(player.tobReward[0][0]).getName());
		if(player.tobReward[0][0] == 20849) {
			player.tobReward[0][1] = 500;
		}else {
			player.tobReward[0][1] = 1;
		}

		
	}
	/**
	 * Handles giving a common reward
	 */
	public void giveCommonReward() {
		player.gfx0(277);
		int commonitem = 0;
		commonitem = Misc.random(commonrewards.length-1);
		player.tobReward[0][0] = commonrewards[commonitem];

		switch(player.tobReward[0][0]) {
			case 560://death rune
				player.tobReward[0][1] = Misc.random(1000);
				break;
			case 565://blood rune
				player.tobReward[0][1] = Misc.random(1000);
				break;
			case 566://soul rune
				player.tobReward[0][1] = Misc.random(1000);
				break;
			case 892://rune arrow
				player.tobReward[0][1] = Misc.random(1000);
				break;
			case 11212://dragon arrow
				player.tobReward[0][1] = Misc.random(1000);
				break;
			case 3050://grimy toadflax
				player.tobReward[0][1] = Misc.random(750);
				break;
			case 208://grimy rannar
				player.tobReward[0][1] = Misc.random(350);
				break;
			case 210://grimy irit
				player.tobReward[0][1] = Misc.random(300);
				break;
			case 212://grimy avantoe
				player.tobReward[0][1] = Misc.random(300);
				break;
			case 214://grimy kwuarm
				player.tobReward[0][1] = Misc.random(300);
				break;
			case 3052://grimy snapdragon
				player.tobReward[0][1] = Misc.random(300);
				break;
			case 216://grimy cadatine
				player.tobReward[0][1] = Misc.random(300);
				break;
			case 2486://grimy lantadyme
				player.tobReward[0][1] = Misc.random(300);
				break;
			case 218://grimy dwarf weed
				player.tobReward[0][1] = Misc.random(400);
				break;
			case 220://grimy torsol
				player.tobReward[0][1] = Misc.random(500);
				break;
			case 443://silver ore
				player.tobReward[0][1] = Misc.random(1500);
				break;
			case 454://coal
				player.tobReward[0][1] = Misc.random(1200);
				break;
			case 445://gold ore
				player.tobReward[0][1] = Misc.random(100);
				break;
			case 448://mithril ore
				player.tobReward[0][1] = Misc.random(750);
				break;
			case 450://adamant ore
				player.tobReward[0][1] = Misc.random(500);
				break;
			case 452://runite ore
				player.tobReward[0][1] = Misc.random(300);
				break;
			case 1624://uncut sapphire
				player.tobReward[0][1] = Misc.random(350);
				break;
			case 1622://uncut emerald
				player.tobReward[0][1] = Misc.random(350);
				break;
			case 1620://uncut ruby
				player.tobReward[0][1] = Misc.random(350);
				break;
			case 1618: //uncut diamond
				player.tobReward[0][1] = Misc.random(400);
				break;
			case 13391: //lizard fang
				player.tobReward[0][1] = Misc.random(3000);
				break;
			case 7937://pure ess
				player.tobReward[0][1] = Misc.random(3000);
				break;
			case 2722://hard casket
				player.tobReward[0][1] = 1;
				break;
			default:
				player.tobReward[0][1]=1;
				break;

		}
	}

	
	public void handleMobDeath(int npcType) {
		player.getTob().tobLeader.getTob().mobAmount-=1;
		switch(npcType) {

			case 8374:
				

				for (String username : player.getTob().tobLeader.clan.activeMembers) {
					Player p = PlayerHandler.getPlayer(username);
					if (!p.inTob()) {
						continue;
					}
	
					
					p.tobCount+=1;
				//	Achievements.increase(player, AchievementType.TOB, 1);
					p.getPA().movePlayer(3237,4307,0);
					p.sendMessage("@red@Congratulations you have defeated The Verzik Vitur go loot your chest!");
					
					p.sendMessage("@red@You have completed "+p.tobCount+" TOB's." );
				//	p.getItems().addItemUnderAnyCircumstance(p.tobReward[0][0], p.tobReward[0][1]);
					p.getTob().roomNames = new ArrayList<String>();
					p.getTob().roomPaths= new ArrayList<Location>();
					p.getTob().currentRoom = 0;
					p.getTob().mobAmount=0;
					p.getTob().reachedRoom = 0;
					p.getTob().tobLeader=null;
					p.getTob().maiden = false;
					p.getTob().sotet = false;
					p.getTob().xarpus = false;
					p.getTob().bloat = false;
					p.getTob().nylo = false;
					p.getTob().vitur = false;
					p.getTob().viturdead = false;
					p.hasRaidOverloadBoost = false;
		}
				return;
			
		}

		if(player.getTob().tobLeader.getTob().mobAmount <= 0) {
			player.sendMessage("@red@The room has been cleared and you are free to pass.");
		}else {
			player.sendMessage("@red@There are "+player.getTob().tobLeader.getTob().mobAmount+" enemies remaining.");
		}

		player.getTob().updateTobPoints();
	}

	/**
	 * Spawns npc for the current room
	 * @param currentRoom The room
	 */
	public void spawnNpcs(int currentRoom) {

		int height = player.getTob().getHeight(player);

		switch(player.getTob().roomNames.get(currentRoom)) {
			case "maiden":
				if(maiden) {
					return;
				}
				if(path == 0 || path == 1) {
			
			        if (player.clan.activeMembers.size() >= 1)  {
						NPCHandler.spawn(8360, 3162, 4444, height, -1, 1000, 40, 300, 300, true);
					
					NPCHandler.spawn(8366, 3163, 4441, height, 4, 100, 25, 100, 100,true);
					NPCHandler.spawn(8366, 3165, 4441, height, 4, 100, 25, 100, 100,true);
					NPCHandler.spawn(8366, 3163, 4452, height, 4, 100, 25, 100, 100,true);
					NPCHandler.spawn(8366, 3165, 4452, height, 4, 100, 25, 100, 100,true);
		      
			        }
				
			        
			    maiden = true;
				mobAmount+=5;
		
				break;
				}
			case "bloat":
				if(bloat) {
					return;
				}
				if(path == 0 || path == 1) {
		        if (player.clan.activeMembers.size() >= 1)  {
					NPCHandler.spawn(8359, 3288, 4452, height, 1, 1250, 33, 300, 300,true); // bloat bloat

		        }
				bloat = true;
				mobAmount+=1;
				break;
	
				}
			case "nylo":
				if(nylo) {
					return;
				}
				if(path == 0 || path == 1) {
		        if (player.clan.activeMembers.size() >= 1)  {
					NPCHandler.spawn(8355, 3295, 4248, height, 1, 1500, 33, 300, 300,true); // Nylo

				 }
		

			
				nylo = true;
				mobAmount+=1;
				break;
	
				}
			case "sotet":
				if(sotet) {
					return;
				}
				if(path == 0 || path == 1) {
		        if (player.clan.activeMembers.size() >= 1)  {
					NPCHandler.spawn(8388, 3278, 4327, height, 1, 1750, 40, 300, 300,true); // Sotet

				 }
		

			
		        sotet = true;
				mobAmount+=1;
				break;
	
				}


			case "xarpus":
				if(xarpus) {
					return;
				}
				if(path == 0 || path == 1) {
		        if (player.clan.activeMembers.size() >= 1)  {
					NPCHandler.spawn(8340, 3170, 4386, 1, height/3, 2000, 40, 300, 300,true); // xar

				 }
		

			
		        xarpus = true;
				mobAmount+=1;
				break;
	
				}


			case "vitur":
				if(vitur) {
					return;
				}
		        if (player.clan.activeMembers.size() == 1)  {
					NPCHandler.spawn(8372, 3167, 4323, height, -1, 2500, 40, 325, 325,false); // vitur boss
		
	
				 }
		        if (player.clan.activeMembers.size() == 2)  {
					NPCHandler.spawn(8372, 3167, 4323, height, -1, 3000, 50, 325, 325,false); // vitur boss
		

				 }
		        if (player.clan.activeMembers.size() == 3)  {
					NPCHandler.spawn(8372, 3167, 4323, height, -1, 4000, 60, 325, 325,false); // vitur boss
		
	
				 }	
		        if (player.clan.activeMembers.size() >= 4)  {
					NPCHandler.spawn(8372, 3167, 4323, height, -1, 5000, 60, 325, 325,false); // vitur boss

	
				 }
		

			
				vitur = true;
				break;
		}
		reachedRoom+=1;
	}
	
	/**
	 * Handles object clicking for Tob objects
	 * @param player The player
	 * @param objectId The object id
	 * @return
	 */
	public boolean handleObjectClick(Player player, int objectId) {
		switch(objectId) {	
			case 32755:	
				if (player.absX == 3305) {
					player.getPA().movePlayer(3303, 4448, getHeight(player));
				} else if (player.absY == 4256) {
					player.getPA().movePlayer(3295, 4254,  getHeight(player));
				} else if (player.absY == 4306) {
					player.getPA().movePlayer(3279, 4308,  getHeight(player));
				} else if (player.absY == 4378) {
					player.getPA().movePlayer(3170, 4380, 1);
				} else  player.getTob().nextRoom();
				
			
				return true;
				
			case 22683:
				if (player.getItems().isWearingItem(9104) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY && player.getItems().getItemCount(30011, true) != 1) {
					player.getTob().roomNames = new ArrayList<String>();
					player.getTob().roomPaths= new ArrayList<Location>();
					player.getTob().currentRoom = 0;
					player.getTob().mobAmount=0;
					player.getTob().reachedRoom = 0;
					player.getTob().tobLeader=null;
					player.getPA().movePlayer(3651, 3212, 0);			
					player.getTob().giveReward();
					player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
					Achievements.increase(player, AchievementType.TOB, 1);
					player.sendMessage("@red@You were awared twice for wearing the Raids Ring.");
					player.getTob().giveReward(); 
					player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);		
					PlayerHandler.executeGlobalMessage("<shad=7000>@red@[TOB] Someone just completed the Theatre of Blood!");
					player.raidPoints+=6;
					player.sendMessage("@red@You receive 6 points from finishing this raid");
					player.sendMessage("@red@You now have "+player.raidPoints+" points.");
					  if (Misc.random(75) == 1) {
				            PlayerHandler.executeGlobalMessage("@red@[Tob] " + player.playerName
				                    + " has recieved a Tob Crate!");
				            player.getItems().addItemUnderAnyCircumstance(19911, 1);
				        }

					break;
				}
					else
						if (!player.getItems().isWearingItem(9104) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY && player.getItems().getItemCount(30011, true) != 1) {
				player.getTob().roomNames = new ArrayList<String>();
				player.getTob().roomPaths= new ArrayList<Location>();
				player.getTob().currentRoom = 0;
				player.getTob().mobAmount=0;
				player.getTob().reachedRoom = 0;
				player.getTob().tobLeader=null;
				Achievements.increase(player, AchievementType.TOB, 1);
				player.getPA().movePlayer(3651, 3212, 0);
				player.getTob().giveReward();
				player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
				
				PlayerHandler.executeGlobalMessage("<shad=7000>@red@[TOB] Someone just completed the Theatre of Blood!");
				player.raidPoints+=4;
				player.sendMessage("@red@You receive 4 points from finishing this raid");
				player.sendMessage("@red@You now have "+player.raidPoints+" points.");
				player.sendMessage("@red@You now have "+player.raidPoints+" points.");
				  if (Misc.random(75) == 1) {
			            PlayerHandler.executeGlobalMessage("@red@[Tob] " + player.playerName
			                    + " has recieved a Tob Crate!");
			            player.getItems().addItemUnderAnyCircumstance(19911, 1);
			        }
				break;
						}
						else
							if (player.getItems().getItemCount(30011, true) == 1 && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
					player.getTob().roomNames = new ArrayList<String>();
					player.getTob().roomPaths= new ArrayList<Location>();
					player.getTob().currentRoom = 0;
					player.getTob().mobAmount=0;
					player.getTob().reachedRoom = 0;
					player.getTob().tobLeader=null;
					Achievements.increase(player, AchievementType.TOB, 1);
					player.getPA().movePlayer(3651, 3212, 0);
					player.getTob().giveReward();
					player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
					
					PlayerHandler.executeGlobalMessage("<shad=7000>@red@[TOB] Someone just completed the Theatre of Blood!");
					player.raidPoints+=9;
					player.sendMessage("@red@You receive 9 points from finishing this raid with your perk!");
					player.sendMessage("@red@You now have "+player.raidPoints+" points.");
					player.sendMessage("@red@You now have "+player.raidPoints+" points.");
					  if (Misc.random(75) == 1) {
				            PlayerHandler.executeGlobalMessage("@red@[Tob] " + player.playerName
				                    + " has recieved a Tob Crate!");
				            player.getItems().addItemUnderAnyCircumstance(19911, 1);
				        }
					break;
							}
							else
								if (player.getItems().getItemCount(30011, true) == 1 && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
						player.getTob().roomNames = new ArrayList<String>();
						player.getTob().roomPaths= new ArrayList<Location>();
						player.getTob().giveReward();
						player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
						player.getTob().currentRoom = 0;
						player.getTob().mobAmount=0;
						player.getTob().reachedRoom = 0;
						player.getTob().tobLeader=null;
						Achievements.increase(player, AchievementType.TOB, 1);
						player.getPA().movePlayer(3651, 3212, 0);
						player.getTob().giveReward();
						player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
						player.sendMessage("@red@You were awarded two times because it's friday!");
						PlayerHandler.executeGlobalMessage("<shad=7000>@red@[TOB] Someone just completed the Theatre of Blood!");
						player.raidPoints+=10;
						player.sendMessage("@red@You receive 10 points from finishing this raid with your perk on a friday!");
						player.sendMessage("@red@You now have "+player.raidPoints+" points.");
						player.sendMessage("@red@You now have "+player.raidPoints+" points.");
						  if (Misc.random(75) == 1) {
					            PlayerHandler.executeGlobalMessage("@red@[Tob] " + player.playerName
					                    + " has recieved a Tob Crate!");
					            player.getItems().addItemUnderAnyCircumstance(19911, 1);
					        }
						break;
								}
								else
									if (player.getItems().getItemCount(30011, true) == 1 && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && player.getItems().isWearingItem(9104)) {
							player.getTob().roomNames = new ArrayList<String>();
							player.getTob().roomPaths= new ArrayList<Location>();
							player.getTob().giveReward();
							player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
							player.getTob().currentRoom = 0;
							player.getTob().mobAmount=0;
							player.getTob().reachedRoom = 0;
							player.getTob().tobLeader=null;
							player.getTob().giveReward();
							player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
							Achievements.increase(player, AchievementType.TOB, 1);
							player.getPA().movePlayer(3651, 3212, 0);
							player.getTob().giveReward();
							player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
							player.sendMessage("@red@You were awarded three times because it's friday and you're wearing the raids ring!");
							PlayerHandler.executeGlobalMessage("<shad=7000>@red@[TOB] Someone just completed the Theatre of Blood!");
							player.raidPoints+=15;
							player.sendMessage("@red@You receive 15 points from finishing this raid with your perk on a friday with your ring!");
							player.sendMessage("@red@You now have "+player.raidPoints+" points.");
							player.sendMessage("@red@You now have "+player.raidPoints+" points.");
							  if (Misc.random(75) == 1) {
						            PlayerHandler.executeGlobalMessage("@red@[Tob] " + player.playerName
						                    + " has recieved a Tob Crate!");
						            player.getItems().addItemUnderAnyCircumstance(19911, 1);
						        }
							break;
									}
				else
					if (player.getItems().isWearingItem(9104) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && player.getItems().getItemCount(30011, true) != 1) {
			player.getTob().roomNames = new ArrayList<String>();
			player.getTob().roomPaths= new ArrayList<Location>();
			player.getTob().giveReward();
			player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
			player.getTob().currentRoom = 0;
			player.getTob().mobAmount=0;
			player.getTob().reachedRoom = 0;
			player.getTob().tobLeader=null;
			Achievements.increase(player, AchievementType.TOB, 1);
			player.getPA().movePlayer(3651, 3212, 0);
	
			player.getTob().giveReward();
		
			player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
		
			PlayerHandler.executeGlobalMessage("<shad=7000>@red@[TOB] Someone just completed the Theatre of Blood!");
			player.raidPoints+=6;
			player.getTob().giveReward();
			player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
			player.sendMessage("@red@You receive 6 points from finishing this raid");
			player.sendMessage("@red@You were awarded three times for wearing the Raids Ring.");
			player.sendMessage("@red@You now have "+player.raidPoints+" points.");
			break;
						} else if (!player.getItems().isWearingItem(9104) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && player.getItems().getItemCount(30011, true) != 1)
							player.getTob().roomNames = new ArrayList<String>();
				player.getTob().roomPaths= new ArrayList<Location>();
				player.getTob().currentRoom = 0;
				player.getTob().mobAmount=0;
				player.getTob().reachedRoom = 0;
			
				player.getTob().tobLeader=null;
				player.getPA().movePlayer(3651, 3212, 0);
				player.getTob().giveReward();
				player.getTob().giveReward();
				player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
				player.getItems().addItemUnderAnyCircumstance(player.tobReward[0][0], player.tobReward[0][1]);
				Achievements.increase(player, AchievementType.TOB, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>@red@[TOB] Someone just completed the Theatre of Blood!");
				player.raidPoints+=5;
				player.sendMessage("@red@You were awared twice because it's Friday!");
				player.sendMessage("@red@You receive 5 points from finishing this raid");
				player.sendMessage("@red@You now have "+player.raidPoints+" points.");
				player.sendMessage("@red@You now have "+player.raidPoints+" points.");
				  if (Misc.random(75) == 1) {
			            PlayerHandler.executeGlobalMessage("<shad=7000>@red@[Tob] " + player.playerName
			                    + " has recieved a Tob Crate!");
			            player.getItems().addItemUnderAnyCircumstance(19911, 1);
			        }
				break;
						
				
	
			case 32653:
				player.getTob().startTob();
				break;
				
			
		
			case 30066:
				if (player.specRestore > 0) {
					int seconds = ((int)Math.floor(player.specRestore * 0.6));
					player.sendMessage("@red@You have to wait another " + seconds + " seconds to use the energy well.");
					return true;
				}
				
				player.specRestore = 120;
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.playerLevel[5] = player.getPA().getLevelForXP(player.playerXP[5]);
				player.getHealth().removeAllStatuses();
				player.getHealth().reset();
				player.getPA().refreshSkill(5);
				player.sendMessage("@red@You touch the energy well");
				return true;

			case 29778:
				if (!player.clan.isFounder(player.playerName)) {
					player.getTob().roomNames = new ArrayList<String>();
					player.getTob().roomPaths= new ArrayList<Location>();
					player.getTob().currentRoom = 0;
					player.getTob().mobAmount=0;
					player.getTob().reachedRoom = 0;
					player.getTob().tobLeader=null;
					player.getPA().movePlayer(1245, 3561, 0);
					player.sendMessage("@red@You abandon your team and leave the TOB.");
					return true;
				}
				if (player.clan.isFounder(player.playerName)) {
					for (String username : player.clan.activeMembers) {
						Player p = PlayerHandler.getPlayer(username);
						if (p == null) {
							continue;
						}
						 if (Boundary.isIn(p, Boundary.TOB)) {
							p.getPA().movePlayer(3668,3219,0);
						p.getTob().roomNames = new ArrayList<String>();
						p.getTob().roomPaths= new ArrayList<Location>();
						p.getTob().currentRoom = 0;
						p.getTob().mobAmount=0;
						p.getTob().reachedRoom = 0;
						p.getTob().tobLeader=null;
						p.getTob().sotet = false;
						p.getTob().xarpus = false;
						p.getTob().maiden = false;
						p.getTob().bloat = false;
						p.getTob().nylo = false;
						p.getTob().vitur = false;
						p.getTob().viturdead = false;
					//	p.getTob().updateTobPoints();
						p.sendMessage("@red@The leader of the clan has ended the TOB!");
					    }
					}
			
				}
				return true;

		}
		return false;
	}
	/**
	 * Goes to the next room, Handles spawning etc.
	 */
	public void nextRoom() {

		if(player.getTob().tobLeader.playerName != player.playerName) {
				if (player.getTob().currentRoom + 1 > player.getTob().tobLeader.getTob().reachedRoom && currentRoom != 0) {
					if (player.getTob().tobLeader.getTob().mobAmount > 0 && player.getTob().currentRoom == player.getTob().tobLeader.getTob().currentRoom) {
						player.sendMessage("@red@Please defeat all the monsters before going to the next room.");
						return;
					}
				}

		}else{
			if (player.getTob().currentRoom == player.getTob().tobLeader.getTob().reachedRoom) {
				if (player.getTob().tobLeader.getTob().mobAmount > 0) {
					player.sendMessage("@red@Please defeat all the monsters before going to the next room.");
					return;
				}
			}
		}



		player.getPA().movePlayer(roomPaths.get(currentRoom+1).getX(),roomPaths.get(currentRoom+1).getY(),roomPaths.get(currentRoom+1).getZ() == 1 ? getHeight(player) + 1 :getHeight(player));

		//player.getTob().updateTobPoints();
		currentRoom+=1;

		if(player.getTob().tobLeader.playerName != player.playerName) {
			return;
		}
		spawnNpcs(currentRoom);
	}
}

