package ethos.model.content.dailytasks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ethos.model.players.Player;
import ethos.model.players.Right;
import ethos.util.Misc;

/**
 * 
 * @author Nighel
 * Credits to: Tyler for the idea
 *
 */

public class DailyTasks {
	
	public enum PossibleTasks {
		
		SHAYZIEN_ASSAULT(TaskTypes.PVM, 50, "Get 50 Kills at Shayzien Assault"),
		PEST_CONTROL(TaskTypes.PVM, 15, "Do 15 Rounds of Pest Control"),
		SLAYER_TASK(TaskTypes.PVM, 5, "Complete 5 Slayer Tasks"),
		BARROWS_CHESTS(TaskTypes.PVM, 5, "Loot 5 Barrows Chests"),
		SKOTIZO(TaskTypes.PVM, 1, "Kill 1 Skotizo"),
		BLACK_DRAGONS(TaskTypes.PVM, 50, "Kill 50 Black Dragons"),
		BLUE_DRAGONS(TaskTypes.PVM, 50, "Kill 50 Blue Dragons"),
		ABYSSAL_DEMONS(TaskTypes.PVM, 50, "Kill 50 Abyssal Demons"),
		DARK_BESTS(TaskTypes.PVM, 50, "Kill 50 Dark Beasts"),
		GENERAL_GRAARDOR(TaskTypes.PVM, 5, "Kill 5 General Graardor"),
		KREE_ARRA(TaskTypes.PVM, 5, "Kill 5 Kree'Arra"),
		TSUTSAROTH(TaskTypes.PVM, 5, "Kill 5 Tsutsaroth"),
		ZILYANA(TaskTypes.PVM, 5, "Kill 5 Zilyana"),
		//CATACOMBS(TaskTypes.PVM, 150, "Kill 150 NPCs inside of catacombs"),
	//	RAIDS(TaskTypes.PVM, 10, "Defeat 10 Raid Bosses"),
		BLACK_DEMONS(TaskTypes.PVM, 50, "Kill 50 Black Demon"),
		IRON_DRAGONS(TaskTypes.PVM, 50, "Kill 50 Iron Dragons"),
		STEEL_DRAGONS(TaskTypes.PVM, 35, "Kill 35 Steel Dragons"),
		MITHRIL_DRAGONS(TaskTypes.PVM, 25, "Kill 25 Mithril Dragons"),
		
		LAW_RUNES(TaskTypes.SKILLING, 10, "Craft 10 Inventories of Law Runes."),
		RED_CHINCHOMPAS(TaskTypes.SKILLING, 100, "Catch 100 Red Chinchompas"),
		SEERS_COURSE(TaskTypes.SKILLING, 25, "Run 25 laps at the Seers Rooftop Course"),
		PRAYER_POTIONS(TaskTypes.SKILLING, 125, "Make 125 Prayer Potions"),
		HERO(TaskTypes.SKILLING, 75, "Pickpocket a Hero 75 times"),
		MAN(TaskTypes.SKILLING, 150, "Pickpocket a Man 150 times"),
		DRAGONSTONES(TaskTypes.SKILLING, 250, "Cut 250 Dragonstones"),
		YEW_SHORTBOWS(TaskTypes.SKILLING, 125, "Fletch 125 Yew Shortbows"),
		GEM_ROCK(TaskTypes.SKILLING, 200, "Mine 200 Gems from Gem Rocks"),
		IRON_PLATEBODYS(TaskTypes.SKILLING, 50, "Smith 50 Iron Platebodies"),
		SHARKS(TaskTypes.SKILLING, 175, "Fish 175 Shark"),
		LOBSTERS(TaskTypes.SKILLING, 250, "Cook 250 Lobsters"),
		LIGHT_YEW_LOGS(TaskTypes.SKILLING, 125, "Light 125 Yew Logs"),
		YEW_LOGS(TaskTypes.SKILLING, 250, "Cut 250 Yew Logs"),
		SNAPDRAGONS(TaskTypes.SKILLING, 75, "Harvest 75 Snapdragons from a Farming patch"),
		SILVER_SICKLES(TaskTypes.SKILLING, 150, "Thieve 150 Silver Sickles"),
		MAGIC_LOGS(TaskTypes.SKILLING, 125, "Cut 125 Magic Logs"),
		LIGHT_MAGIC_LOGS(TaskTypes.SKILLING, 75, "Light 75 Magic Log"),
		//RUNITE_ORE(TaskTypes.SKILLING, 45, "Mine 45 Runite Ore"),
		NATURE_RUNES(TaskTypes.SKILLING, 10, "Craft 10 Inventories of Nature Runes."),
		;
		
		public TaskTypes type;
		public int amount;
		String message;
		
		private PossibleTasks(TaskTypes type, int amount, String message) {
			
			this.type = type;
			this.amount = amount;
			this.message = message;
			
		}
		
	}
	
	/**
	 * Gets today's date
	 * @return
	 */
	
	public static int getTodayDate() {
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		return (month * 100 + day);
	}
	
	/**
	 * Assigns a task to a player when it has daily's enabled and has no current task.
	 * @param player
	 */
	
	public static void assignTask(Player player) {
		if (player.dailyEnabled) {

			if(player.currentTask != null) {
				player.sendMessage("@red@You already have a daily task. Type '::dailytask' to check your task!");
				return;
			}
			
			if (player.dailyTaskDate == getTodayDate() && player.currentTask == null && player.completedDailyTask == true) {
				player.sendMessage("@red@You already completed your daily task");
				return;
			} else {
				if (player.completedDailyTask == false && player.dailyTaskDate != getTodayDate() && player.currentTask == null) {
					player.currentTask = getRandomTask(player.playerChoice);
					player.sendMessage("@red@New Daily Task: " + player.currentTask.message);
					player.updateQuestTab();
				}
			}
			
		} 
	}
	
	public static void complete(Player player) {
		if(player.currentTask == null)
			return;
		
		if(player.totalDailyDone >= player.currentTask.amount) {
			int pointsGiven = (10 + Misc.random(15));
			player.totalDailyDone = 0;
			player.dailyTaskDate = getTodayDate();
			player.completedDailyTask = true;
			player.sendMessage("@blu@@cr10@ You have completed your daily task: "+ player.currentTask.name().toLowerCase() + ", take these rewards!");
			player.dailyPoints += pointsGiven;
			player.getItems().addItemUnderAnyCircumstance(20791, 1);
				//player.getItems().addItemUnderAnyCircumstance(20703, 1);
			player.sendMessage("@blu@@cr10@You've been rewarded with " + pointsGiven + " Daily points & a Skilling Supply Crate");
			player.currentTask = null;
		}
	}
	
	public static void increase(Player player, PossibleTasks task) {
		if(player.currentTask == null)
			return;
		
		if (player.currentTask.equals(task)) {
			player.totalDailyDone++;
			player.sendMessage(player.totalDailyDone + "/"+player.currentTask.amount+" completed of your task.");
			complete(player);
		}
	}
	
	/**
	 * Gets a random task on the choice of what the player has selected
	 * @param type
	 * @return
	 */
	private static PossibleTasks getRandomTask(TaskTypes type) {
		ArrayList<PossibleTasks> possibleTasks = new ArrayList<PossibleTasks>();
		for(PossibleTasks tasks : PossibleTasks.values())
			if(tasks.type.equals(type))
				possibleTasks.add(tasks);
		return possibleTasks.get(Misc.random(possibleTasks.size()));
	}
	
}