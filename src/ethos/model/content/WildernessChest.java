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

public class WildernessChest {

	private static final int KEY = 3460;
	private static final int ANIMATION = 1914;

	private static final Map<Rarity, List<GameItem>> items = new HashMap<>();

	static {
		items.put(Rarity.COMMON, Arrays.asList(   
				new GameItem(11337, 10), 
				new GameItem(11212, Misc.random(20) + 50),//d
				new GameItem(9341, Misc.random(20) + 50),//d
				new GameItem(11230, Misc.random(20) + 50),//d
				new GameItem(11937, Misc.random(20) + 50),//dark crabs
				new GameItem(22804, Misc.random(20) + 50),//d
				new GameItem(2996, Misc.random(1) + 15),//pkp
				new GameItem(12749),//mysterious emblem tier 3
				new GameItem(12750),//mysterious emblem tier 4
				new GameItem(12696, 20), //noted super combat potion
				new GameItem(12746),//mysterious emblem
				new GameItem(11937, 300),//note dark crabs
				new GameItem(9242, 200), //ruby bolts (e)
				new GameItem(9244, 200),//dragonstone bolts (e)

				new GameItem(995, Misc.random(500_000) + 1_000_000)));
		
		items.put(Rarity.UNCOMMON, Arrays.asList(
				new GameItem(11337, 20), 
				new GameItem(995, Misc.random(1_000_000) + 5_000_000),//coins
				 new GameItem(6585, 1),	 
				 new GameItem(19529, 1),    
				 new GameItem(4716, 1),
                 new GameItem(4718, 1),
 				new GameItem(12749),//mysterious emblem tier 3
				new GameItem(12750),//mysterious emblem tier 4
				new GameItem(12751),//mysterious emblem tier 5
				new GameItem(12752),//mysterious emblem tier 6
				new GameItem(12753),//mysterious emblem tier 7
				new GameItem(12754),//mysterious emblem tier 8
                 new GameItem(4720, 1),
                 new GameItem(4722, 1),
                 new GameItem(4724, 1),
                 new GameItem(4726, 1),
                 new GameItem(4728, 1),
     			new GameItem(20838),//corrupted helm
				new GameItem(20840),//corrupted platebody
				new GameItem(20842),//corrupted platelegs
				new GameItem(20844), //corrupted plateskirt		
				new GameItem(20846),//corrupted kiteshield
				new GameItem(12769),//whip mix
				new GameItem(12771),//whip mix
				new GameItem(12757),//dbow paint
				new GameItem(12759),//dbow paint
				new GameItem(12761),//dbow paint
				new GameItem(12763),//dbow paint
                 new GameItem(4730, 1),
                 new GameItem(4732, 1),
                 new GameItem(4734, 1),
                 new GameItem(4736, 1),
                 new GameItem(4738, 1),		 
				 new GameItem(19529, 1),    
				new GameItem(11840, 1)));
		items.put(Rarity.RARE, Arrays.asList(
				new GameItem(11337, 20), 
				new GameItem(995, Misc.random(1_000_000) + 5_000_000),//coins
				 new GameItem(6585, 1),	 
				 new GameItem(19529, 1),    
				 new GameItem(4716, 1),
                 new GameItem(4718, 1),
 				new GameItem(12749),//mysterious emblem tier 3
				new GameItem(12750),//mysterious emblem tier 4
				new GameItem(12751),//mysterious emblem tier 5
				new GameItem(12752),//mysterious emblem tier 6
				new GameItem(12753),//mysterious emblem tier 7
				new GameItem(12754),//mysterious emblem tier 8
                 new GameItem(4720, 1),
                 new GameItem(4722, 1),
                 new GameItem(4724, 1),
                 new GameItem(4726, 1),
                 new GameItem(4728, 1),
     			new GameItem(20838),//corrupted helm
				new GameItem(20840),//corrupted platebody
				new GameItem(20842),//corrupted platelegs
				new GameItem(20844), //corrupted plateskirt		
				new GameItem(20846),//corrupted kiteshield
				new GameItem(12769),//whip mix
				new GameItem(12771),//whip mix
				new GameItem(12757),//dbow paint
				new GameItem(12759),//dbow paint
				new GameItem(12761),//dbow paint
				new GameItem(12763),//dbow paint
                 new GameItem(4730, 1),
                 new GameItem(4732, 1),
                 new GameItem(4734, 1),
                 new GameItem(4736, 1),
                 new GameItem(4738, 1),		 
				 new GameItem(19529, 1),    
				new GameItem(22622, 1), 
				new GameItem(995, Misc.random(5_000_000) + 30_000_000),//coins
				 new GameItem(22625, 1),
				 new GameItem(22628, 1),
				 new GameItem(22631, 1),
				 new GameItem(22610, 1),
				 new GameItem(22613, 1),
				 new GameItem(22619, 1),
				 new GameItem(22638, 1),
					new GameItem(12875), //veracs armour set
					new GameItem(12877), //dharoks armour set
					new GameItem(12879), //torag's armour set
					new GameItem(12881), //ahrim's armour set
					new GameItem(12883), //karil's armour set
				 new GameItem(20784, 1),
				 new GameItem(22641, 1),
				 new GameItem(22644, 1),
				 new GameItem(22647, 1),
				 new GameItem(22650, 1),
				 new GameItem(22653, 1),
				 new GameItem(565, 350),				
					new GameItem(560, 350),
					new GameItem(11337, 10), 
					new GameItem(11730, 1),
					new GameItem(11212, Misc.random(20) + 50),//d
					new GameItem(9341, Misc.random(20) + 50),//d
					new GameItem(11230, Misc.random(20) + 50),//d
					new GameItem(11937, Misc.random(20) + 50),//dark crabs
					new GameItem(22804, Misc.random(20) + 50),//d
					new GameItem(2996, Misc.random(1) + 15),//pkp
					new GameItem(12749),//mysterious emblem tier 3
					new GameItem(12750),//mysterious emblem tier 4
					new GameItem(12696, 20), //noted super combat potion
					new GameItem(12746),//mysterious emblem
					new GameItem(11937, 300),//note dark crabs
					new GameItem(9242, 200), //ruby bolts (e)
					new GameItem(9244, 200),//dragonstone bolts (e)
				 new GameItem(22656, 1),
				 new GameItem(4151, 1),      
				 new GameItem(21748, 1),  
				 new GameItem(21750, 1), 
				 new GameItem(24271, 1), 
				new GameItem(11840, 1)));
	}

	private static GameItem randomChestRewards(int chance) {
		int random = Misc.random(chance);
		List<GameItem> itemList = random > 1 ? items.get(Rarity.COMMON) : random >= 10 && random < 30 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		GameItem item = Misc.getRandomItem(itemList);
		return Misc.getRandomItem(itemList);
	}



	public static void searchChest(Player c) {
		if (c.getItems().playerHasItem(KEY)) {
			c.getItems().deleteItem(KEY, 1);
			c.startAnimation(ANIMATION);
			Achievements.increase(c, AchievementType.DAWN, 1);
			GameItem reward = Boundary.isIn(c, Boundary.DONATOR_ZONE) && c.getRights().isOrInherits(Right.CONTRIBUTOR) ? randomChestRewards(2) : randomChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				Server.itemHandler.createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.heightLevel, reward.getAmount());
			}
		//	Achievements.increase(c, AchievementType.LOOT_CRYSTAL_CHEST, 1);
			c.sendMessage("@blu@You stick your hand in the chest and pull an item out of the chest.");
		} else {
			c.sendMessage("@blu@The chest is locked, it won't budge!");
		}
	}

	enum Rarity {
		UNCOMMON, COMMON, RARE
	}

}