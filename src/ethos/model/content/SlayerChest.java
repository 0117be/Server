package ethos.model.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ethos.Server;
import ethos.model.content.HourlyRewardBox.Rarity;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.items.GameItem;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.Right;
import ethos.util.Misc;

public class SlayerChest {

	private static final int KEY = 3464;
	private static final int ANIMATION = 881;

	private static final Map<Rarity, List<GameItem>> items = new HashMap<>();

	static {
		items.put(Rarity.COMMON, Arrays.asList(
				new GameItem(3052, 25), 
				new GameItem(216, 25),   
				new GameItem(2486, 25),				
				new GameItem(537, 25),
				new GameItem(12696, 5),
				

				new GameItem(11212, Misc.random(20) + 50),//d
				new GameItem(9341, Misc.random(20) + 50),//d
				new GameItem(11230, Misc.random(20) + 50),//d
				new GameItem(11937, Misc.random(20) + 50),//dark crabs
				new GameItem(22804, Misc.random(20) + 50),//d
				new GameItem(2996, Misc.random(1) + 5),//pkp

				new GameItem(995, Misc.random(500_000) + 1_000_000),//coins
				new GameItem(220, 25)));
		
		items.put(Rarity.UNCOMMON, Arrays.asList(
				new GameItem(11944, 20), 
				new GameItem(995, Misc.random(1_000_000) + 5_000_000),//coins
				 new GameItem(6585, 1),
				 new GameItem(4716, 1),
                 new GameItem(4718, 1),
                 new GameItem(4720, 1),
                 new GameItem(4722, 1),
                 new GameItem(4724, 1),
                 new GameItem(4726, 1),
                 new GameItem(4728, 1),
                 new GameItem(4730, 1),
                 new GameItem(4732, 1),
                 new GameItem(4734, 1),
                 new GameItem(4736, 1),
                 new GameItem(4738, 1),		 
				 new GameItem(19529, 1),    
				 new GameItem(19529, 1)));    
				//new GameItem(11840, 1)));
		items.put(Rarity.RARE, Arrays.asList(
				new GameItem(11286, 1), 
				new GameItem(995, Misc.random(5_000_000) + 20_000_000),//coins
				 new GameItem(12783, 1),
	
				 new GameItem(2577, 1),
				 new GameItem(11235, 1),
				 new GameItem(12006, 1),
				 new GameItem(4151, 1),				 
				 new GameItem(19529, 1),               
				new GameItem(11840, 1)));
	}

	private static GameItem randomChestRewards(int chance) {
		int random = Misc.random(chance);
		List<GameItem> itemList = random > 3 ? items.get(Rarity.COMMON) : random <= 1 && random <= 45 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		GameItem item = Misc.getRandomItem(itemList);
		return Misc.getRandomItem(itemList);
	}



	public static void searchChest(Player c) {
		if (c.getItems().playerHasItem(KEY)) {
			c.getItems().deleteItem(KEY, 1);
			c.startAnimation(ANIMATION);
			
	
			GameItem reward = Boundary.isIn(c, Boundary.DONATOR_ZONE) && c.getRights().isOrInherits(Right.CONTRIBUTOR) ? randomChestRewards(2) : randomChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				Server.itemHandler.createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.heightLevel, reward.getAmount());
			}
		
			Achievements.increase(c, AchievementType.SLAYER_CHEST, 1);
			c.sendMessage("@blu@You stick your hand in the chest and pull an item out of the chest.");
		} else {
			c.sendMessage("@blu@The chest is locked, it won't budge!");
		}
	}

	enum Rarity {
		UNCOMMON, COMMON, RARE
	}

}