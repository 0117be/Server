package ethos.model.content.achievement;

import java.util.EnumSet;
import java.util.Set;

import ethos.model.items.GameItem;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

/**
 * @author Jason http://www.rune-server.org/members/jason
 * @date Mar 26, 2014
 */
public class Achievements {

    public enum Achievement {
        /**
         * Tier 1 Achievement Start
         */
       Crab_Killer(0, AchievementTier.TIER_1, 25, AchievementType.SLAY_ROCK_CRABS, null, "Kill 100 Rock Crabs", 100, 1, new GameItem(995, 500000), new GameItem(4587), new GameItem(405)),
       Slaughter_House(1, AchievementTier.TIER_1, 25, AchievementType.SLAY_CHICKENS, null, "Kill 1000 Chickens", 1000, 1, new GameItem(995, 500000), new GameItem(405)),
       DEAL_10000_DAMAGE(2, AchievementTier.TIER_1, 25, AchievementType.DEAL_DAMAGE_PVM , null, "Deal 10,000 Damage on NPCs", 10000, 1, new GameItem(995, 2000000), new GameItem(405), new GameItem(13513)),
       HEAL_5000_HEALTH(3, AchievementTier.TIER_1, 25, AchievementType.HEAL_WITH_FOOD, null, "Heal 5,000 Heatlh from food", 5000, 1, new GameItem(995, 2000000),new GameItem(11937, 200), new GameItem(13513)),
       Bby_Dragon_Hunter_I(4, AchievementTier.TIER_1, 25, AchievementType.SLAY_BABY_DRAGONS, null, "Kill 50 Baby Dragons", 50, 1, new GameItem(995, 250000), new GameItem(535, 100), new GameItem(405)),
       DRAGON_SLAYER_I(5, AchievementTier.TIER_1, 25, AchievementType.SLAY_DRAGONS, null, "Kill 25 Dragons", 25, 1, new GameItem(995, 500000), new GameItem(537, 50), new GameItem(405)),
       PvMer_I(6, AchievementTier.TIER_1, 25, AchievementType.SLAY_ANY_NPCS, null, "Kill 1000 Npcs", 1000, 1, new GameItem(995, 50000000), new GameItem(405)),
       Boss_Hunter_I(7, AchievementTier.TIER_1, 25, AchievementType.SLAY_BOSSES, null, "Kill 100 Bosses", 100, 1, new GameItem(995, 5000000), new GameItem(11937, 50), new GameItem(405)),
       Fishing_Task_I(8, AchievementTier.TIER_1, 25, AchievementType.FISH, null, "Catch 1000 Fish", 1000, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(384, 50)),
        Cooking_Task_I(9, AchievementTier.TIER_1, 25, AchievementType.COOK, null, "Cook 1000 Fish", 1000, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(3145, 50)),
        Mining_Task_I(10, AchievementTier.TIER_1, 25, AchievementType.MINE, null, "Mine 1000 Rocks", 1000, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(12013), new GameItem(12016)),
        Smithing_Task_I(11, AchievementTier.TIER_1, 25, AchievementType.SMITH, null, "Smith 1000 Bars", 1000, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(2360, 300)),
        Farming_Task_I(12, AchievementTier.TIER_1, 25, AchievementType.FARM, null, "Pick Herbs 100 Times", 100, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(208, 150), new GameItem(13646), new GameItem(13644)),
        Herblore_Task_I(13, AchievementTier.TIER_1, 25, AchievementType.HERB, null, "Mix 500 Potions", 500, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(100, 50)),
        Woodcutting_Task_I(14, AchievementTier.TIER_1, 25, AchievementType.WOODCUT, null, "Cut 1000 Trees", 1000, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(1518, 300)),
        Fletching_Task_I(15, AchievementTier.TIER_1, 25, AchievementType.FLETCH, null, "Fletch 1000 Logs", 1000, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(63, 300)),
        Firemaking_Task_I(16, AchievementTier.TIER_1, 25, AchievementType.FIRE, null, "Light 500 Logs", 500, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(20710)),
        Thieving_Task_I(17, AchievementTier.TIER_1, 25, AchievementType.THIEV, null, "Steal 500 Times", 500, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(5557), new GameItem(5556)),
        Agility_Task_I(18, AchievementTier.TIER_1, 25, AchievementType.AGIL, null, "Complete 100 Course Laps", 100, 1, new GameItem(995, 2000000), new GameItem(20791, 2), new GameItem(11849, 15)),
        Slayer_Task_I(19, AchievementTier.TIER_1, 25, AchievementType.SLAY, null, "Complete 50 Slayer Tasks", 50, 1, new GameItem(995, 2000000), new GameItem(2677, 2), new GameItem(405)),
        CKey_Task(20, AchievementTier.TIER_1, 25, AchievementType.LOOT_CRYSTAL_CHEST, null, "Loot Crystal Chest 50 Times", 50, 1, new GameItem(995, 5000000), new GameItem(990, 5)),
        Pc_Task(21, AchievementTier.TIER_1, 25, AchievementType.PEST_CONTROL_ROUNDS, null, "Complete Pest Control 100 Times", 100, 1, new GameItem(1812, 1)),
        Jad_Task(22, AchievementTier.TIER_1, 25, AchievementType.FIGHT_CAVES_ROUNDS, null, "Complete Fightcaves 3 Times", 3, 1, new GameItem(995, 50000000), new GameItem(405, 3), new GameItem(6570)),
        Slayer_Chest_I(23, AchievementTier.TIER_1, 25, AchievementType.SLAYER_CHEST, null, "Loot 20 Slayer Chests", 150, 1, new GameItem(3464, 5)),
        ClueScroll_Task(24, AchievementTier.TIER_1, 25, AchievementType.CLUES, null, "Loot 50 Clue Caskets", 50, 1, new GameItem(995, 5000000)),
        Dungeon_Crawler(25, AchievementTier.TIER_1, 25, AchievementType.RAIDS, null, "Complete 25 Chamber of Xeric raids", 25, 1, new GameItem(22380, 1)),
        Rune_Supplier_I(26, AchievementTier.TIER_1, 25, AchievementType.RUNECRAFTING, null, "Craft 5000 runes of any kind", 5000, 1, new GameItem(995, 1000000), new GameItem(20791, 3), new GameItem(7937,2500)),
        Zulrah_Slayer(27, AchievementTier.TIER_1, 25, AchievementType.ZULRAH, null, "Kill 25 Zulrah", 25, 1, new GameItem(995, 5000000), new GameItem(6199, 1)),
        Mr_Resourceful(28, AchievementTier.TIER_1, 25, AchievementType.ARMOUR, null, "Pack a set of armour into a box set using a hammer", 1, 1, new GameItem(995, 250000), new GameItem(6199, 1)),
        Duel_Winner(29, AchievementTier.TIER_1, 25, AchievementType.DUEL_WINNER, null, "Win 25 Duel Matches", 25, 1, new GameItem(995, 250000), new GameItem(6199, 1)),
        Mbox_Opener(30, AchievementTier.TIER_1, 25, AchievementType.MBOX, null, "Open 5 Mboxes", 5, 1, new GameItem(995, 250000), new GameItem(6199, 1)),
        Ultra_Opener(31, AchievementTier.TIER_1, 25, AchievementType.ULTRA, null, "Open 5 Ultras", 5, 1, new GameItem(995, 2500000), new GameItem(13346, 1)),
        Vote_Box_Opener(32, AchievementTier.TIER_1, 25, AchievementType.VOTEBOX, null, "Open 5 Vote Boxes", 5, 1, new GameItem(995, 250000), new GameItem(11739, 1)),
        Jar_Opener(33, AchievementTier.TIER_1, 25, AchievementType.JAR, null, "Open 5 Jars", 5, 1, new GameItem(995, 2500000), new GameItem(12007, 1)),
        Skilling_Crate_Opener(34, AchievementTier.TIER_1, 25, AchievementType.SKILLINGCRATE, null, "Open 10 Skilling Crates", 10, 1, new GameItem(995, 250000), new GameItem(20791, 1)),
        Voter(35, AchievementTier.TIER_1, 25, AchievementType.VOTE, null, "Vote 25 Times", 25, 1, new GameItem(995, 25000000), new GameItem(1464, 25)),
        Scratchy(36, AchievementTier.TIER_1, 25, AchievementType.SCRATCH, null, "Use 10 Scratch Tickets", 10, 1, new GameItem(995, 10000000), new GameItem(5020, 3)),
        Blood_Money_Machine(37, AchievementTier.TIER_1, 25, AchievementType.BLOODMONEY, null, "Exchange 10k Bloodmoney", 10000, 1, new GameItem(995, 10000000), new GameItem(13307, 2500)),
        Bank_Pin(38, AchievementTier.TIER_1, 25, AchievementType.BANKPIN, null, "Set a Bank Pin", 1, 1, new GameItem(995, 10000000), new GameItem(13307, 2500)),
        Event_Boss_Killer(39, AchievementTier.TIER_1, 25, AchievementType.EVENTBOSS, null, "Kill the Event Boss 25 Times", 25, 1, new GameItem(13307, 3000), new GameItem(23071, 2)),
        Money_Gainer(40, AchievementTier.TIER_1, 25, AchievementType.GAINS, null, "Sell 25 Items to Money Gains", 25, 1, new GameItem(13307, 3000), new GameItem(5020, 10)),
        Gambler(41, AchievementTier.TIER_1, 25, AchievementType.GAMBLE, null, "Plant 100 Mithril Seeds", 100, 1, new GameItem(299, 75), new GameItem(995, 10000000)),
        Mini_Assault(41, AchievementTier.TIER_1, 25, AchievementType.ASSAULT, null, "kill 200 Shayzien Npcs", 100, 1, new GameItem(995, 30000000), new GameItem(405, 2)),



        /**
         * Tier 2 Achievement Start
         */
        HEAVY_GRINDER(0, AchievementTier.TIER_2, 150, AchievementType.WEAR_99, null, "Get 99 in any skill and wear the Skill Cape", 1, 1,  new GameItem(995, 500000)),//here
        Dragon_Hunter_II(1, AchievementTier.TIER_2, 50, AchievementType.SLAY_DRAGONS, null, "Kill 350 Dragons", 350, 2, new GameItem(995, 50000000), new GameItem(537, 200), new GameItem(405)),
        DEAL_100000_DAMAGE(2, AchievementTier.TIER_2, 25, AchievementType.DEAL_DAMAGE_PVM, null, "Deal 100,000 Damage on NPCs", 100000, 1, new GameItem(995, 5000000)),
        HEAL_250000_HEALTH(3, AchievementTier.TIER_2, 25, AchievementType.HEAL_WITH_FOOD, null, "Heal 25,000 Heatlh from food", 25000, 1, new GameItem(995, 5000000), new GameItem(11937, 300)),
        PvMer_II(4, AchievementTier.TIER_2, 50, AchievementType.SLAY_ANY_NPCS, null, "Kill 3000 Npcs", 3000, 2, new GameItem(995, 75000000), new GameItem(13307, 30000), new GameItem(405, 2)),
        Boss_Hunter_II(5, AchievementTier.TIER_2, 50, AchievementType.SLAY_BOSSES, null, "Kill 700 Bosses", 700, 2, new GameItem(995, 200000000), new GameItem(13307, 30000), new GameItem(405, 4)),
        INTERMEDIATE_FISHER(6, AchievementTier.TIER_2, 50, AchievementType.FISH, null, "Catch 2500 Fish", 2500, 2, new GameItem(995, 5000000), new GameItem(20791, 2), new GameItem(384, 250)),
        INTERMEDIATE_CHEF(7, AchievementTier.TIER_2, 50, AchievementType.COOK, null, "Cook 2500 Fish", 2500, 2, new GameItem(995, 5000000), new GameItem(20791, 2), new GameItem(11937, 200)),
        INTERMEDIATE_MINER(8, AchievementTier.TIER_2, 50, AchievementType.MINE, null, "Mine 2500 Rocks", 2500, 2, new GameItem(995, 50000000), new GameItem(20791, 5), new GameItem(2364, 200)),
        INTERMEDIATE_SMITH(9, AchievementTier.TIER_2, 50, AchievementType.SMITH, null, "Smelt/Smith 2500 Bars", 2500, 2, new GameItem(995, 5000000), new GameItem(20791, 2), new GameItem(2362, 300)),
        INTERMEDIATE_FARMER(10, AchievementTier.TIER_2, 50, AchievementType.FARM, null, "Pick Herbs 300 Times", 300, 2, new GameItem(995, 5000000), new GameItem(20791, 2), new GameItem(2486, 300), new GameItem(13640)),
        INTERMEDIATE_MIXER(11, AchievementTier.TIER_2, 50, AchievementType.HERB, null, "Mix 1000 Potions", 1000, 2, new GameItem(995, 5000000), new GameItem(20791, 2), new GameItem(3005, 50)),
        INTERMEDIATE_CHOPPER(12, AchievementTier.TIER_2, 50, AchievementType.WOODCUT, null, "Cut 2500 Trees", 2500, 2, new GameItem(995, 5000000), new GameItem(20791, 2), new GameItem(1516, 200)),
        INTERMEDIATE_FLETCHER(13, AchievementTier.TIER_2, 50, AchievementType.FLETCH, null, "Fletch 2500 Logs", 2500, 2, new GameItem(995, 5000000), new GameItem(20791, 2), new GameItem(67, 300)),
        INTERMEDIATE_PYRO(14, AchievementTier.TIER_2, 50, AchievementType.FIRE, null, "Light 1000 Logs", 1000, 2, new GameItem(995, 5000000), new GameItem(20791, 2), new GameItem(20706), new GameItem(20704)),
        INTERMEDIATE_THIEF(15, AchievementTier.TIER_2, 50, AchievementType.THIEV, null, "Steal 1000 Times", 1000, 2, new GameItem(995, 5000000), new GameItem(20791, 2), new GameItem(5555), new GameItem(5553)),
        INTERMEDIATE_RUNNER(16, AchievementTier.TIER_2, 50, AchievementType.AGIL, null, "Complete 250 Course Laps", 250, 2, new GameItem(995, 5000000), new GameItem(20791, 2), new GameItem(11849, 50)),
        SLAYER_DESTROYER(17, AchievementTier.TIER_2, 50, AchievementType.SLAY, null, "Complete 80 Slayer Tasks", 80, 2, new GameItem(995, 5000000), new GameItem(3464, 5), new GameItem(405)),
        CHEST_LOOTER(18, AchievementTier.TIER_2, 50, AchievementType.LOOT_CRYSTAL_CHEST, null, "Loot Crystal Chest 100 Times", 100, 2, new GameItem(995, 5000000), new GameItem(990, 15)),
        MR_PORTAL(19, AchievementTier.TIER_2, 50, AchievementType.PEST_CONTROL_ROUNDS, null, "Complete Pest Control 250 Times", 250, 2, new GameItem(995, 150000000), new GameItem(11673, 1)),
        RED_OF_FURY(20, AchievementTier.TIER_2, 25, AchievementType.FIGHT_CAVES_ROUNDS, null, "Complete Fightcaves 5 Times", 5, 2, new GameItem(995, 50000000), new GameItem(405, 3), new GameItem(6570)),
        Slayer_Chest_II(21, AchievementTier.TIER_2, 100, AchievementType.SLAYER_CHEST, null, "Loot 300 Slayer Chests", 300, 2, new GameItem(3464, 1)),
        CLUE_SCROLLER(22, AchievementTier.TIER_2, 75, AchievementType.CLUES, null, "Loot 120 Clue Caskets", 120, 2, new GameItem(995, 5000000)),
        RAID_EXPERT(23, AchievementTier.TIER_2, 50, AchievementType.RAIDS, null, "Complete 50 Chamber of Xeric raids", 50, 1, new GameItem(22384, 1)),
        Rune_Supplier_II(24, AchievementTier.TIER_2, 50, AchievementType.RUNECRAFTING, null, "Craft 20000 runes of any kind", 20000, 1, new GameItem(995, 5000000),new GameItem(20791, 2), new GameItem(6819, 1)),
        Vorkath_Hunter(25, AchievementTier.TIER_2, 50, AchievementType.VORKATH, null, "Kill 25 Vorkath", 25, 1, new GameItem(995, 25000000), new GameItem(22125, 100)),
        WONDROUS_MAGICIAN(25, AchievementTier.TIER_2, 50, AchievementType.MAGE_ARENA, null, "Complete the Mage Arena Miniquest", 1, 1, new GameItem(995, 25000000), new GameItem(6889, 1)),
        MINE_1000_ADAMANT_ORE(26, AchievementTier.TIER_2, 50, AchievementType.MINE, null, "Mine 1000 Adamant Ore", 1000, 2, new GameItem(995, 5000000),new GameItem(20791, 2), new GameItem(12015)),
        FISH_1000_SWORDFISH(27, AchievementTier.TIER_2, 50, AchievementType.FISH_SWORDFISH, null, "Fish 1000 Swordfish", 1000, 2, new GameItem(995, 250000000),new GameItem(20791, 2), new GameItem(384, 250)),
        WILDERNESS_SACRIFICE(28, AchievementTier.TIER_2, 50, AchievementType.PRAYER, null, "Offer 500 bones at  the Wilderness Altar @red@38 Wilderness", 500, 2, new GameItem(995, 75000000), new GameItem(3464, 4)),
        Duel_Slayer(29, AchievementTier.TIER_2, 25, AchievementType.DUEL_WINNER, null, "Win 50 Duel Matches", 50, 1, new GameItem(995, 20000000), new GameItem(6199, 2)),
        Mbox_Lover(30, AchievementTier.TIER_2, 25, AchievementType.MBOX, null, "Open 25 Mboxes", 25, 1, new GameItem(995, 20000000), new GameItem(6199, 2)),
        Ultra_Lover(31, AchievementTier.TIER_2, 25, AchievementType.ULTRA, null, "Open 25 Ultras", 25, 1, new GameItem(995, 50000000), new GameItem(13346, 2)),
        Vote_Box_Lover(32, AchievementTier.TIER_2, 25, AchievementType.VOTEBOX, null, "Open 25 Vote Boxes", 25, 1, new GameItem(995, 2500000), new GameItem(11739, 4)),
        Jar_Opener_Lover(33, AchievementTier.TIER_2, 25, AchievementType.JAR, null, "Open 25 Jars", 25, 1, new GameItem(995, 10000000), new GameItem(13245, 1)),
        Skilling_Crate_Lover(34, AchievementTier.TIER_2, 25, AchievementType.SKILLINGCRATE, null, "Open 50 Skilling Crates", 50, 1, new GameItem(995, 25000000), new GameItem(20791, 5)),
        Voter_King(35, AchievementTier.TIER_2, 25, AchievementType.VOTE, null, "Vote 100 Times", 100, 1, new GameItem(995, 50000000), new GameItem(1464, 100)),
        Scratcher(36, AchievementTier.TIER_2, 25, AchievementType.SCRATCH, null, "Use 50 Scratch Tickets", 50, 1, new GameItem(995, 50000000), new GameItem(5020, 6)),
        Blood_Money_Machine_2(37, AchievementTier.TIER_2, 25, AchievementType.BLOODMONEY, null, "Exchange 100k Bloodmoney", 100000, 1, new GameItem(995, 30000000), new GameItem(13307, 5000)),
        Event_Boss_Slayer(38, AchievementTier.TIER_2, 25, AchievementType.EVENTBOSS, null, "Kill the Event Boss 100 Times", 100, 1, new GameItem(13307, 5000), new GameItem(23071, 4)),
        Money_Gainer_2(39, AchievementTier.TIER_2, 25, AchievementType.GAINS, null, "Sell 100 Items to Money Gains", 100, 1, new GameItem(13307, 5000), new GameItem(5020, 20)),
        Gambler_Addict(40, AchievementTier.TIER_2, 25, AchievementType.GAMBLE, null, "Plant 1000 Mithril Seeds", 1000, 1, new GameItem(299, 300), new GameItem(995, 20000000)),
        Harsh_Assault(41, AchievementTier.TIER_2, 25, AchievementType.ASSAULT, null, "kill 500 Shayzien Npcs", 500, 1, new GameItem(995, 35000000), new GameItem(405, 3)),
        RFD_Minigame(42, AchievementTier.TIER_2, 25, AchievementType.RFD, null, "Complete RFD Minigame", 1, 1, new GameItem(995, 3000000), new GameItem(7462, 1)),
        Advanced_Shopper(43, AchievementTier.TIER_2, 25, AchievementType.SHOPPER, null, "Access Advanced Wilderness Shop", 1, 1, new GameItem(995, 1000000), new GameItem(13307, 200)),
        Reversal(44, AchievementTier.TIER_2, 25, AchievementType.HASTA, null, "Turn Zamorak Spear to Hasta", 1, 1, new GameItem(995, 1000000), new GameItem(13307, 200)),
        Dusk_Dawn_Killer(45, AchievementTier.TIER_2, 25, AchievementType.DAWN, null, "Use 50 Wilderness Keys", 50, 1, new GameItem(995, 40000000), new GameItem(3460, 5)),
        Raid_Crate_Opener(46, AchievementTier.TIER_2, 25, AchievementType.RAIDBOX, null, "Open 25 Raid Crates", 25, 1, new GameItem(19911, 1), new GameItem(19903, 1)),

        /**
         * Tier 3 Achievement Start
         */
        MAXIMUS(0, AchievementTier.TIER_3, 150, AchievementType.WEAR_MAX, null, "Wear a Max Cape", 1, 1, new GameItem(13346, 2), new GameItem(995, 50000000)),
        EXPERT_DRAGON_SLAYER(1, AchievementTier.TIER_3, 250, AchievementType.SLAY_DRAGONS, null, "Kill 950 Dragons", 950, 3, new GameItem(995, 10000000), new GameItem(405), new GameItem(537, 1000)),
        DEAL_500000_DAMAGE(2, AchievementTier.TIER_3, 250, AchievementType.DEAL_DAMAGE_PVM, null, "Deal 500,000 Damage on NPCs", 500000, 1, new GameItem(995, 10000000), new GameItem(10556)),
        HEAL_100000_HEALTH(3, AchievementTier.TIER_3, 250, AchievementType.HEAL_WITH_FOOD, null, "Heal 100,000 Heatlh from food", 100000, 1, new GameItem(995, 10000000), new GameItem(10559)),
        SLAUGHTERER(4, AchievementTier.TIER_3, 500, AchievementType.SLAY_ANY_NPCS, null, "Kill 10,000 Npcs", 10000, 3, new GameItem(995, 250000000), new GameItem(13307, 50000), new GameItem(405, 4)),
        BOSS_SLAUGHTERER(5, AchievementTier.TIER_3, 500, AchievementType.SLAY_BOSSES, null, "Kill 1500 Bosses", 1500, 3, new GameItem(995, 250000000), new GameItem(13307, 50000), new GameItem(405, 4)),
        EXPERT_FISHER(6, AchievementTier.TIER_3, 100, AchievementType.FISH, null, "Catch 5000 Fish", 5000, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(11935, 200)),
        EXPERT_CHEF(7, AchievementTier.TIER_3, 100, AchievementType.COOK, null, "Cook 5,000 Fish", 5000, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(11937, 500)),
        EXPERT_MINER(8, AchievementTier.TIER_3, 100, AchievementType.MINE, null, "Mine 5,000 Rocks", 5000, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(12014)),
        EXPERT_SMITH(9, AchievementTier.TIER_3, 100, AchievementType.SMITH, null, "Smelt/Smith 5,000 Bars", 5000, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(2364, 300)),
        EXPERT_FARMER(10, AchievementTier.TIER_3, 100, AchievementType.FARM, null, "Pick Herbs 600 Times", 600, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(220, 500), new GameItem(13642)),
        EXPERT_MIXER(11, AchievementTier.TIER_3, 100, AchievementType.HERB, null, "Mix 2,500 Potions", 2500, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(112, 100)),
        EXPERT_CHOPPER(12, AchievementTier.TIER_3, 100, AchievementType.WOODCUT, null, "Cut 5,000 Trees", 5000, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(1514, 300)),
        EXPERT_FLETCHER(13, AchievementTier.TIER_3, 100, AchievementType.FLETCH, null, "Fletch 5,000 Logs", 5000, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(71, 300)),
        EXPERT_PYRO(14, AchievementTier.TIER_3, 100, AchievementType.FIRE, null, "Light 2,500 Logs", 2500, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(20708), new GameItem(20712)),
        EXPERT_THIEF(15, AchievementTier.TIER_3, 100, AchievementType.THIEV, null, "Steal 3,000 Times", 3000, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(5554)),
        EXPERT_RUNNER(16, AchievementTier.TIER_3, 200, AchievementType.ROOFTOP, null, "Complete 300 Rooftop Course Laps", 300, 3, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(11849, 100)),
        SLAYER_EXPERT(17, AchievementTier.TIER_3, 100, AchievementType.SLAY, null, "Complete 150 Slayer Tasks", 150, 3, new GameItem(995, 100000000), new GameItem(3464, 5), new GameItem(405, 3)),
        DIG_FOR_GOLD(18, AchievementTier.TIER_3, 100, AchievementType.LOOT_CRYSTAL_CHEST, null, "Loot Crystal Chest 200 Times", 200, 3, new GameItem(995, 10000000), new GameItem(990, 30)),
        KNIGHT(19, AchievementTier.TIER_3, 200, AchievementType.PEST_CONTROL_ROUNDS, null, "Complete Pest Control 500 Times", 500, 3, new GameItem(995, 150000000), new GameItem(11673, 2)),
        TZHAAR(20, AchievementTier.TIER_3, 25, AchievementType.FIGHT_CAVES_ROUNDS, null, "Complete Fightcaves 10 Times", 10, 3, new GameItem(995, 50000000), new GameItem(405, 3), new GameItem(6570)),
        Slayer_chest_III(21, AchievementTier.TIER_3, 250, AchievementType.SLAYER_CHEST, null, "Loot 750 Slayer Chests", 750, 3, new GameItem(3464, 15)),
        CLUE_CHAMP(22, AchievementTier.TIER_3, 150, AchievementType.CLUES, null, "Loot 180 Clue Caskets", 180, 3, new GameItem(995, 10000000), new GameItem(20164)),
        OLM_KILLING_MACHINE(23, AchievementTier.TIER_3, 50, AchievementType.RAIDS, null, "Complete 100 Chamber of Xeric raids", 100, 1, new GameItem(22376, 1)),
        Rune_Overload(24, AchievementTier.TIER_3, 150, AchievementType.RUNECRAFTING, null, "Craft 200000 runes of any kind", 200000, 1, new GameItem(995, 10000000), new GameItem(20791, 5), new GameItem(20251, 1)),
        FREAK_ON_A_LEASH(25, AchievementTier.TIER_3, 50, AchievementType.CERB, null, "Kill 25 Cerberus", 25, 1, new GameItem(995, 10000000), new GameItem(13346, 1)),
        MINE_2500_RUNE_ORE(26, AchievementTier.TIER_3, 50, AchievementType.MINE, null, "Mine 2500 Rune Ore", 2500, 2, new GameItem(995, 75000000), new GameItem(20791, 5), new GameItem(12800)),
        FISH_5000_SHARKS(27, AchievementTier.TIER_3, 50, AchievementType.FISH_SHARK, null, "Fish 5000 Raw Shark", 5000, 2, new GameItem(995, 75000000), new GameItem(20791, 5), new GameItem(21028)),
        Vorkath_Hunter_Master(28, AchievementTier.TIER_3, 50, AchievementType.VORKATH, null, "Kill 50 Vorkath", 50, 1, new GameItem(995, 50000000), new GameItem(2425, 1)),
        TOB_RUNNER(29, AchievementTier.TIER_3, 50, AchievementType.TOB, null, "Complete 100 Theatre of Blood raids", 100, 1, new GameItem(30908, 1), new GameItem(30913, 1)),
        TOB_ELITEST(30, AchievementTier.TIER_3, 50, AchievementType.TOB, null, "Complete 200 Theatre of Blood raids", 100, 1, new GameItem(30909, 1), new GameItem(30914, 1)),
        TOB_ADDICT(31, AchievementTier.TIER_3, 50, AchievementType.TOB, null, "Complete 250 Theatre of Blood raids", 100, 1, new GameItem(30908, 1), new GameItem(30915, 1)),
        TOB_MASTER(32, AchievementTier.TIER_3, 50, AchievementType.TOB, null, "Complete 300 Theatre of Blood raids", 300, 1, new GameItem(22473, 1), new GameItem(30908, 1)),
        Duel_Master(33, AchievementTier.TIER_3, 25, AchievementType.DUEL_WINNER, null, "Win 100 Duel Matches", 100, 1, new GameItem(995, 20000000), new GameItem(6199, 2)),
        Mbox_Master(34, AchievementTier.TIER_3, 25, AchievementType.MBOX, null, "Open 50 Mboxes", 50, 1, new GameItem(995, 20000000), new GameItem(6199, 2)),
        Ultra_Master(35, AchievementTier.TIER_3, 25, AchievementType.ULTRA, null, "Open 50 Ultras", 50, 1, new GameItem(995, 50000000), new GameItem(13346, 2)),
        Vote_Box_Master(36, AchievementTier.TIER_3, 25, AchievementType.VOTEBOX, null, "Open 50 Vote Boxes", 50, 1, new GameItem(995, 2500000), new GameItem(11739, 4)),
        Jar_Opener_Master(37, AchievementTier.TIER_3, 25, AchievementType.JAR, null, "Open 50 Jars", 50, 1, new GameItem(995, 10000000), new GameItem(13245, 1)),
        Skilling_Crate_Master(38, AchievementTier.TIER_3, 25, AchievementType.SKILLINGCRATE, null, "Open 100 Skilling Crates", 100, 1, new GameItem(995, 25000000), new GameItem(20791, 5)),
        Voter_God(39, AchievementTier.TIER_3, 25, AchievementType.VOTE, null, "Vote 500 Times", 500, 1, new GameItem(995, 50000000), new GameItem(1464, 100)),
        Scratchist(40, AchievementTier.TIER_3, 25, AchievementType.SCRATCH, null, "Use 250 Scratch Tickets", 250, 1, new GameItem(995, 50000000), new GameItem(5020, 6)),
        Blood_Money_Machine_3(41, AchievementTier.TIER_3, 25, AchievementType.BLOODMONEY, null, "Exchange 500k Bloodmoney", 500000, 1, new GameItem(995, 30000000), new GameItem(13307, 5000)),
        Event_Boss_Master(42, AchievementTier.TIER_3, 25, AchievementType.EVENTBOSS, null, "Kill the Event Boss 500 Times", 500, 1, new GameItem(13307, 5000), new GameItem(23071, 4)),
        Money_Gainer_3(43, AchievementTier.TIER_3, 25, AchievementType.GAINS, null, "Sell 500 Items to Money Gains", 500, 1, new GameItem(13307, 5000), new GameItem(5020, 20)),
        Gambler_Problems(44, AchievementTier.TIER_3, 25, AchievementType.GAMBLE, null, "Plant 10000 Mithril Seeds", 10000, 1, new GameItem(299, 300), new GameItem(995, 20000000)),
        Master_Assault(45, AchievementTier.TIER_3, 25, AchievementType.ASSAULT, null, "kill 1000 Shayzien Npcs", 1000, 1, new GameItem(995, 35000000), new GameItem(405, 3)),
        Dusk_Dawn_Slayer(46, AchievementTier.TIER_3, 25, AchievementType.DAWN, null, "Use 100 Wilderness Keys", 100, 1, new GameItem(995, 40000000), new GameItem(3460, 5)),
        Karils_Killer(47, AchievementTier.TIER_3, 25, AchievementType.KARILS, null, "kill 300 Karils", 300, 1, new GameItem(12883, 1)),
        Ahrims_Killer(48, AchievementTier.TIER_3, 25, AchievementType.AHRIMS, null, "kill 300 Ahrims", 300, 1, new GameItem(12881, 1)),
        Dharoks_Killer(49, AchievementTier.TIER_3, 25, AchievementType.DHAROKS, null, "kill 300 Dharoks", 300, 1, new GameItem(12877, 1)),
        Guthans_Killer(50, AchievementTier.TIER_3, 25, AchievementType.GUTHANS, null, "kill 300 Guthans", 300, 1, new GameItem(12873, 1)),
        Veracs_Killer(51, AchievementTier.TIER_3, 25, AchievementType.VERACS, null, "kill 300 Veracs", 300, 1, new GameItem(12875, 1)),
        Torags_Killer(52, AchievementTier.TIER_3, 25, AchievementType.TORAGS, null, "kill 300 Torags", 300, 1, new GameItem(12879, 1)),
        Buy_a_Perk(53, AchievementTier.TIER_3, 25, AchievementType.PERK, null, "Buy a Perk", 1, 2, new GameItem(13346, 1)),
        Blood_Altar(54, AchievementTier.TIER_3, 25, AchievementType.BLOODALTAR, null, "Use Blood Altar 100 Times", 100, 3, new GameItem(3339, 100)),
        Godsword_Builder(55, AchievementTier.TIER_3, 25, AchievementType.GODSWORD, null, "Build 4 Godswords", 4, 4, new GameItem(20068, 1), new GameItem(20071, 1), new GameItem(20074, 1), new GameItem(20077, 1)),
        Shield_Builder(56, AchievementTier.TIER_3, 25, AchievementType.SPIRITSHIELD, null, "Build 3 Spirit Shields", 3, 5, new GameItem(12831, 1), new GameItem(995, 50000000)),
        Imbue_Fountain(57, AchievementTier.TIER_3, 25, AchievementType.IMBUERING, null, "Imbue 6 Rings on Fountain", 6, 3, new GameItem(11773, 1), new GameItem(995, 50000000)),
        Raid_Crate_Master(58, AchievementTier.TIER_3, 25, AchievementType.RAIDBOX, null, "Open 50 Raid Crates", 50, 5, new GameItem(19911, 1), new GameItem(19903, 1));


        private AchievementTier tier;
        private AchievementRequirement requirement;
        private AchievementType type;
        private String description;

        public int getachiveFameReward() {
            return achiveFameReward;
        }

        public void setachiveFameReward(int achiveFameReward) {
            this.achiveFameReward = achiveFameReward;
        }

        private int achiveFameReward;
        private int amount, identification, points;
        private final GameItem[] rewards;

        Achievement(int identification, AchievementTier tier, int achiveFameReward, AchievementType type, AchievementRequirement requirement, String description, int amount, int points, GameItem... rewards) {
            this.identification = identification;
            this.tier = tier;
            this.type = type;
            this.requirement = requirement;
            this.description = description;
            this.achiveFameReward = achiveFameReward;
            this.amount = amount;
            this.points = points;
            this.rewards = rewards;

            //format the items
            for (GameItem b : rewards) if (b.getAmount() == 0) b.setAmount(1);

        }

        public int getId() {
            return identification;
        }

        public AchievementTier getTier() {
            return tier;
        }

        public AchievementType getType() {
            return type;
        }

        public AchievementRequirement getRequirement() {
            return requirement;
        }

        public String getDescription() {
            return description;
        }

        public int getAmount() {
            return amount;
        }

        public int getPoints() {
            return points;
        }

        public GameItem[] getRewards() {
            return rewards;
        }

        public static final Set<Achievement> ACHIEVEMENTS = EnumSet.allOf(Achievement.class);

        public static Achievement getAchievement(AchievementTier tier, int ordinal) {
            for (Achievement achievement : ACHIEVEMENTS)
                if (achievement.getTier() == tier && achievement.ordinal() == ordinal)
                    return achievement;
            return null;
        }

        public static boolean hasRequirement(Player player, AchievementTier tier, int ordinal) {
            for (Achievement achievement : ACHIEVEMENTS) {
                if (achievement.getTier() == tier && achievement.ordinal() == ordinal) {
                    if (achievement.getRequirement() == null)
                        return true;
                    if (achievement.getRequirement().isAble(player))
                        return true;
                }
            }
            return false;
        }
    }

    public static void increase(Player player, AchievementType type, int amount) {
        for (Achievement achievement : Achievement.ACHIEVEMENTS) {
            if (achievement.getType() == type) {
                if (achievement.getRequirement() == null || achievement.getRequirement().isAble(player)) {
                    int currentAmount = player.getAchievements().getAmountRemaining(achievement.getTier().ordinal(), achievement.getId());
                    int tier = achievement.getTier().ordinal();
                    if (currentAmount < achievement.getAmount() && !player.getAchievements().isComplete(achievement.getTier().ordinal(), achievement.getId())) {
                        player.getAchievements().setAmountRemaining(tier, achievement.getId(), currentAmount + amount);
                        if ((currentAmount + amount) >= achievement.getAmount()) {
                            String name = achievement.name().replaceAll("_", " ");
                            player.getAchievements().setComplete(tier, achievement.getId(), true);
                            player.getAchievements().setPoints(achievement.getPoints() + player.getAchievements().getPoints());
                            player.setFame(player.getFame() + player.getAchievements().getPoints());
                            player.sendMessage("@red@Achievement completed on tier " + (tier + 1) + ": '" + achievement.name().toLowerCase().replaceAll("_", " ") + "' and receive " + achievement.getPoints() + " point(s).", 255);
                            if (achievement.getTier().ordinal() > 0) {
                                for (Player p : PlayerHandler.players) {
                                    if (p == null)
                                        continue;
                                    Player c = p;
                                    //c.sendMessage("@red@" + Misc.capitalizeJustFirst(player.playerName) + " has completed the achievement: " + name + " on tier " + (tier + 1) + ".");
                                }
                            }
                            //add reward inventory if spots free
                            if (achievement.getRewards() != null) {
                                player.sendMessage("Your achievement reward(s) has been added to your account.");
								//player.sendMessage("Your achievement point(s) have been added to your account.");
                                for (GameItem b : achievement.getRewards())
                                	player.getItems().addItemToBank(b.getId(), b.getAmount());
									//player.getItems().addItemUnderAnyCircumstance(b.getId(), b.getAmount());
                            }
                        }
                    }
                }
            }
        }
    }

    public static void reset(Player player, AchievementType type) {
        for (Achievement achievement : Achievement.ACHIEVEMENTS) {
            if (achievement.getType() == type) {
                if (achievement.getRequirement() == null || achievement.getRequirement().isAble(player)) {
                    if (!player.getAchievements().isComplete(achievement.getTier().ordinal(), achievement.getId())) {
                        player.getAchievements().setAmountRemaining(achievement.getTier().ordinal(), achievement.getId(),
                                0);
                    }
                }
            }
        }
    }

    public static void complete(Player player, AchievementType type) {
        for (Achievement achievement : Achievement.ACHIEVEMENTS) {
            if (achievement.getType() == type) {
                if (achievement.getRequirement() != null && achievement.getRequirement().isAble(player)
                        && !player.getAchievements().isComplete(achievement.getTier().ordinal(), achievement.getId())) {
                    int tier = achievement.getTier().ordinal();
                    //String name = achievement.name().replaceAll("_", " ");
                    player.getAchievements().setAmountRemaining(tier, achievement.getId(), achievement.getAmount());
                    player.getAchievements().setComplete(tier, achievement.getId(), true);
                    player.getAchievements().setPoints(achievement.getPoints() + player.getAchievements().getPoints());
                    player.sendMessage("Achievement completed on tier " + (tier + 1) + ": '" + achievement.name().toLowerCase().replaceAll("_", " ") + "' and receive " + achievement.getPoints() + " point(s).", 255);
                }
            }
        }
    }

    public static void checkIfFinished(Player player) {
        //complete(player, AchievementType.LEARNING_THE_ROPES);
    }

    public static int getMaximumAchievements() {
        return Achievement.ACHIEVEMENTS.size();
    }
}
