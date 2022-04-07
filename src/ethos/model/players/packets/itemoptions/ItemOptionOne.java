package ethos.model.players.packets.itemoptions;

import java.util.Objects;
import java.util.Optional;

import ethos.Config;
import ethos.Server;
import ethos.model.content.DiceHandler;
import ethos.model.content.Packs;
import ethos.model.content.RunePouch;
import ethos.model.content.LootingBag.LootingBag;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.content.barrows.Barrows;
import ethos.model.content.teleportation.TeleportTablets;
import ethos.model.content.trails.MasterClue;
import ethos.model.content.trails.RewardLevel;
import ethos.model.items.ItemAssistant;
import ethos.model.multiplayer_session.MultiplayerSessionType;
import ethos.model.multiplayer_session.duel.DuelSession;
import ethos.model.multiplayer_session.duel.DuelSessionRules.Rule;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.*;
import ethos.model.players.combat.Hitmark;
import ethos.model.players.combat.magic.NonCombatSpells;
import ethos.model.players.skills.agility.AgilityHandler;
import ethos.model.players.skills.hunter.Hunter;
import ethos.model.players.skills.hunter.trap.impl.BirdSnare;
import ethos.model.players.skills.hunter.trap.impl.BoxTrap;
import ethos.model.players.skills.prayer.Bone;
import ethos.model.players.skills.prayer.Prayer;
import ethos.model.players.skills.runecrafting.Pouches;
import ethos.model.players.skills.runecrafting.Pouches.Pouch;
import ethos.util.Misc;
import ethos.world.objects.GlobalObject;
import ethos.model.content.achievement_diary.ardougne.*;
import ethos.model.content.achievement_diary.varrock.VarrockDiaryEntry;

import static ethos.model.content.DiceHandler.DICING_AREA;
import ethos.model.content.Dwarfcannon;
import ethos.model.content.GalvekBag;
/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ItemOptionOne implements PacketType {
    /**
     * The last planted flower
     */
    private int lastPlantedFlower;

    /**
     /**
     * If the entity is in the midst of planting mithril seeds
     */
    public static boolean plantingMithrilSeeds = false;

    /**
     * The position the mithril seed was planted
     */
    private static final int[] position = new int[2];

    /**
     * Chances of getting to array flower1
     */
    private int flowerChance = Misc.random(100);


    /**
     * Last plant for the timing of the flower
     */
    private long lastPlant = 0;
    /**
     * Array containing flower object ids without black and white flowers
     */
    private int flower[] = {
            2980, 2981, 2982,
            2983, 2984, 2985,
            2986
    };

    /**
     * Array containing flower object ids along with black and white flowers
     */
    private int flower1[] = {
            2980, 2981, 2982,
            2983, 2984, 2985,
            2986, 2987, 2988
    };

    /**
     * Draws a random flower object from the flower arrays
     * @return
     * 			the flower object chosen
     */
    private int randomFlower() {
        if (flowerChance > 29) {
            return flower[(int) (Math.random() * flower.length)];
        }
        return flower[(int) (Math.random() * flower1.length)];
    }

    public void plantMithrilSeed(Player c) {
        if (System.currentTimeMillis() - c.diceDelay >= 2000) { // time between plants.
            position[0] = c.absX;
            position[1] = c.absY;
            lastPlantedFlower = randomFlower();
            if (!Server.getGlobalObjects().anyExists(position[0], position[1], c.getHeight())) { // Checks if there are NO objects under the player
                GlobalObject object1 = new GlobalObject(lastPlantedFlower, position[0], position[1], c.getHeight(), 3, 10, 120, -1);
                Server.getGlobalObjects().add(object1);
                c.getPA().walkTo(1, 0);
                c.turnPlayerTo(position[0] - 1, position[1]);
                c.sendMessage("You planted a flower!");
                plantingMithrilSeeds = true;
                c.getItems().deleteItem(299, c.getItems().getItemSlot(299), 1);
                c.diceDelay = System.currentTimeMillis();
            } else if (Server.getGlobalObjects().anyExists(position[0], position[1], c.getHeight())) { // Checks if there are objects under the player
                c.sendMessage("You can plant on top of this. Please Move");
            } else {
                c.sendMessage("You must wait 2 seconds to plant another flower."); // Message for when you attempt to plant before the timer is finished
            }
        }
    }
    public static void plantMithrilSeedRigged(Player c,int flowerId) {
        position[0] = c.absX;
        position[1] = c.absY;
        GlobalObject object1 = new GlobalObject(flowerId, position[0], position[1], c.getHeight(), 3, 10, 120, -1);
        Server.getGlobalObjects().add(object1);
        c.getPA().walkTo(1, 0);
        c.turnPlayerTo(position[0] -1, position[1]);
        c.sendMessage("You planted a flower!");
        plantingMithrilSeeds = true;
    }
    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        c.getInStream().readSignedShortLittleEndianA();
        int itemSlot = c.getInStream().readSignedWordA();
        int itemId = c.getInStream().readSignedWordBigEndian();
        if(c.debugMessage) {
        	c.sendMessage("ItemOption1: Item: " + itemId + " Name: " + ItemAssistant.getItemName(itemId));
        }
        if (itemSlot >= c.playerItems.length || itemSlot < 0) {
            return;
        }
        if (itemId != c.playerItems[itemSlot] - 1) {
            return;
        }
        if (c.isDead || c.getHealth().getAmount() <= 0) {
            return;
        }
        if (Server.getHolidayController().clickItem(c, itemId)) {
            return;
        }
        if (c.getInterfaceEvent().isActive()) {
            c.sendMessage("Please finish what you're doing.");
            return;
        }
        if (c.getBankPin().requiresUnlock()) {
            c.getBankPin().open(2);
            return;
        }
        if (c.getTutorial().isActive()) {
            c.getTutorial().refresh();
            return;
        }
        c.lastClickedItem = itemId;
        c.getHerblore().clean(itemId);
        if (c.getFood().isFood(itemId)) {
            c.getFood().eat(itemId, itemSlot);
        } else if (c.getPotions().isPotion(itemId)) {
            c.getPotions().handlePotion(itemId, itemSlot);
        }
        Optional<Bone> bone = Prayer.isOperableBone(itemId);
        if (bone.isPresent()) {
            c.getPrayer().bury(bone.get());
            return;
        }
        TeleportTablets.operate(c, itemId);
        Packs.openPack(c, itemId);
        if (LootingBag.isLootingBag(c, itemId)) {
            c.getLootingBag().openWithdrawalMode();
            return;
        }
        if (RunePouch.isRunePouch(c, itemId)) {
            c.getRunePouch().openRunePouch();
            return;
        }
        switch (itemId) {
		case 3847:
			if (Server.getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
				for (int i = 8144; i < 8195; i++) {
					c.getPA().sendFrame126("", i);
				}
				c.getPA().sendFrame126("         @dre@Starter tips for Regular players!", 8144);
				
				c.getPA().sendFrame126("@dre@We keep this information updated:", 8145);
				
				c.getPA().sendFrame126("<shad=7000>@or2@Thieving is a good way of making some starter cash", 8148);
				c.getPA().sendFrame126("<shad=7000>@or1@Barrows spawn on top of the mounds, all you have to", 8149);
				c.getPA().sendFrame126("<shad=7000>@or1@do is kill the designated npcs for drops", 8150);
				c.getPA().sendFrame126("<shad=7000>@or2@Wildy slayer tasks can drop slayer keys", 8151);
				c.getPA().sendFrame126("<shad=7000>@or2@you can obtain all kinds of rewards from the chest", 8152);
				c.getPA().sendFrame126("<shad=7000>@or1@All skillcapes have perks & special benefits", 8153);
				c.getPA().sendFrame126("<shad=7000>@or2@Max capes give perks from ALL skill capes", 8154);
				c.getPA().sendFrame126("<shad=7000>@or1@Revenants drop weapons that do bonus damage", 8155);
				c.getPA().sendFrame126("<shad=7000>@or1@to any npc inside of the wilderness", 8156);
				c.getPA().sendFrame126("<shad=7000>@or2@Also check out all of our usefull ::commands", 8157);

				c.getPA().showInterface(8134);
			break;
        /**
         * Bonus xp & Double Drop books
         */
	case 13513:
        if (c.getItems().playerHasItem(13513, 1)) {
            c.getDH().sendDialogues(9029, -1);
		}
		break;
	case 13254:
        if (c.getItems().playerHasItem(13254, 1)) {
        	   c.getJarofSouls().open();
		}
		break;
	case 19903:
        if (c.getItems().playerHasItem(19903, 1)) {
        	   c.getCoxBox().open();
		}
		break;
	case 19911:
        if (c.getItems().playerHasItem(19911, 1)) {
        	   c.getTobBox().open();
		}
		break;
	case 12007:
        if (c.getItems().playerHasItem(12007, 1)) {
        	   c.getJarofDirt().open();
		}
		break;
	case 21233:
        if (c.getItems().playerHasItem(21233, 1)) {
        	   c.getMeatTree().open();
		}
		break;
	case 12885:
        if (c.getItems().playerHasItem(12885, 1)) {
        	   c.getJarofSand().open();
		}
		break;
		
	case 13187:
        if (c.getItems().playerHasItem(13187, 1)) {
        	   c.getCarePackage().open();
		}
		break;
		
	case 32101:
        if (c.getItems().playerHasItem(32101, 1)) {
        	   c.getXmasPresentBlue().open();
		}
		break;
	case 32102:
        if (c.getItems().playerHasItem(32102, 1)) {
        	   c.getXmasPresentWhite().open();
		}
		break;
	case 12936:
        if (c.getItems().playerHasItem(12936, 1)) {
        	   c.getJarofSwamp().open();
		}
		break;
	case 14945:
        if (c.getItems().playerHasItem(14945, 1)) {
            c.getDH().sendDialogues(9033, -1);
		}
		break;
	case 14947:
        if (c.getItems().playerHasItem(14947, 1)) {
            c.getDH().sendDialogues(9035, -1);
		}
		break;
	case 14949:
        if (c.getItems().playerHasItem(14949, 1)) {
            c.getDH().sendDialogues(9039, -1);
		}
		break;
	case 14951:
        if (c.getItems().playerHasItem(14951, 1)) {
            c.getDH().sendDialogues(9037, -1);
		}
		break;
	case 13279:
        if (c.getItems().playerHasItem(13279, 1)) {
            c.getDH().sendDialogues(9031, -1);
		}
		break;
	case 14955:
        if (c.getItems().playerHasItem(14955, 1)) {
            c.getDH().sendDialogues(9043, -1);
		}
		break;
	case 14957:
        if (c.getItems().playerHasItem(14957, 1)) {
            c.getDH().sendDialogues(9045, -1);
		}
		break;
	case 14953:
        if (c.getItems().playerHasItem(14953, 1)) {
            c.getDH().sendDialogues(9047, -1);
		}
		break;
	case 14959:
        if (c.getItems().playerHasItem(14959, 1)) {
            c.getDH().sendDialogues(9049, -1);
		}
		break;
		
	case 19668:
		 c.getItems().deleteItem(19668, 1);
		Server.getGlobalObjects().add(new GlobalObject(11338, c.absX, c.absX, c.getHeight(), 2, 10, 1, 11338)); // North - Awakened Altar
		 Server.getGlobalObjects().add(new GlobalObject(11338, c.absX, c.absX, c.getHeight(), 3, 10, 120, 11338));
		 c.sendMessage("@blu@You have placed down a bank booth.");
          break;	
	case 10952:
		  if(Boundary.isIn(c, Boundary.BOSSINGZONE1) && c.underAttackBy2 <= 0 || Boundary.isIn(c, Boundary.BOSSINGZONE2)) {
            c.getDH().sendDialogues(6966, -1);
		  } else {
        		 c.sendMessage("@red@You can only use this in the designated bossing area.");
        		 c.sendMessage("@red@You can only only spawn 1 boss at a time as well.");
        	}
		break;
		
        case 6:
			c.getCannon().setup();
			break; 
        case 2696:
            if (c.getItems().playerHasItem(2696, 1)) {
                c.getItems().deleteItem(2696, 1);
                c.gfx100(263);
                c.amDonated += 1;
                c.sendMessage("Thank you for donating! 1$ has been added to your total credit.");
                c.updateRank();
                c.getPA().closeAllWindows();
    			c.getItems().addItemUnderAnyCircumstance(30230, 1);
                return;
            }
            break;
            
        case 10328:
        	c.getItems().deleteItem(10328, 1);
			c.getPA().movePlayer(2639, 10424, 0);
			Server.npcHandler.spawnNpc(c, 5822, 2650, 10425, 0, 1, 500, 5, 250, 250, true, false);
			c.sendMessage("<shad=7000>@whi@The Christmas Boss Has Spawned. Kill It!");
			break;
			
        case 2395:
        	if (c.playerLevel[5] == c.getPA().getLevelForXP(c.playerXP[5])) {
				c.sendMessage("You already have full prayer points.");
				return;
			}			
        	c.gfx100(1177);
         	c.startAnimation(2289);
			c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
			c.sendMessage("You recharge your prayer points.");
			c.getPA().refreshSkill(5);
			break;
            case 2697:
                if (c.getItems().playerHasItem(2697, 1)) {
                    c.getItems().deleteItem(2697, 1);
                    c.gfx100(263);
                    c.amDonated += 10;
                    c.sendMessage("Thank you for donating! 10$ has been added to your total credit.");
                    c.updateRank();
                    c.getPA().closeAllWindows();
        			c.getItems().addItemUnderAnyCircumstance(30230, 10);
                    return;
                }
                break;
            case 299:
                if(!Boundary.isIn(c, DICING_AREA)) {
                    c.sendMessage("You can not gamble here.");
                    return;
                }
                if(System.currentTimeMillis() - c.lastPlant < 250) {
                    return;
                }
                c.lastPlant = System.currentTimeMillis();
                plantMithrilSeed(c);
            	Achievements.increase(c, AchievementType.GAMBLE, 1);
                break;
            case 2698:
                if (c.getItems().playerHasItem(2698, 1)) {
                    c.getItems().deleteItem(2698, 1);
                    c.gfx100(263);
                    c.amDonated += 25;
                    c.sendMessage("Thank you for donating! 25$ has been added to your total credit.");
                    c.updateRank();
                    c.getPA().closeAllWindows();
            		c.getItems().addItemUnderAnyCircumstance(30230, 25);
                    return;
                }
            case 2699:
                if (c.getItems().playerHasItem(2699, 1)) {
                    c.getItems().deleteItem(2699, 1);
                    c.gfx100(263);
                    c.amDonated += 50;
                    c.sendMessage("Thank you for donating! 50$ has been added to your total credit.");
                    c.updateRank();
                    c.getPA().closeAllWindows();
            		c.getItems().addItemUnderAnyCircumstance(30230, 50);
                    return;
                }
                break;
            case 12929:
				if(c.getItems().playerHasItem(12929)) {
					c.getItems().deleteItem(12929, 1);
					c.getItems().addItemUnderAnyCircumstance(12934, 20000);
					c.sendMessage("You successfully dismantle the Serpentine Helmet for 20,000 zulrah scales.");
				}
			break;
            case 2700:
                if (c.getItems().playerHasItem(2700, 1)) {
                    c.getItems().deleteItem(2700, 1);
                    c.gfx100(263);
                    c.amDonated += 100;
                    c.sendMessage("Thank you for donating! 100$ has been added to your total credit.");
                    c.updateRank();
                    c.getPA().closeAllWindows();
            		c.getItems().addItemUnderAnyCircumstance(30230, 100);
                }
                return;
            case 23071: //Ancient loot bag
                    if (c.getItems().playerHasItem(23071)) {
                        c.getGalvekBag().open();
                        return;
                    }
            case 13346:
                if (c.getItems().playerHasItem(13346)) {
                    c.getUltraMysteryBox().openInterface();
                    return;
                }
                break;
            case 6677:
                if (c.getItems().playerHasItem(6677)) {
                    c.getMegaBox().openInterface();
                    return;
                }
                break;
            case 13656:
                if (c.getItems().playerHasItem(13656)) {
                    c.getXmasPresent().openInterface();
                    return;
                }
                break;
            case 21347:
                c.boltTips = true;
                c.arrowTips = false;
                c.javelinHeads = false;
                c.sendMessage("Your Amethyst method is now Bolt Tips!");
                break;
                /**
                 * prayer scrolls
                 */
    		case 21034:
    		/*	if (c.rigUnlocked == true) {
    				c.sendMessage("You have already unlocked this prayer");
    				return;
    			}
    			else {
    			c.rigUnlocked = true;
    			c.getItems().deleteItem(21034, 1);
    			c.sendMessage("You have unlocked the Rigour Prayer");
    			}*/
                c.getDH().sendDialogues(5151, -1);
    			break;
    		case 21079:
    		/*	if (c.augUnlocked == true) {
    				c.sendMessage("You have already unlocked this prayer");
    				return;
    			}
    			else {
    			c.augUnlocked = true;
    			c.getItems().deleteItem(21079, 1);
    			c.sendMessage("You have unlocked the Augury Prayer");
    			}*/
                c.getDH().sendDialogues(5152, -1);

    		break;
            case 20724:
                DuelSession session = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
                if (Objects.nonNull(session)) {
                    if (session.getRules().contains(Rule.NO_DRINKS)) {
                        c.sendMessage("Using the imbued heart with 'No Drinks' option is forbidden.");
                        return;
                    }
                }
                if (System.currentTimeMillis() - c.lastHeart < 420000) {
                    c.sendMessage("You must wait 7 minutes between each use.");
                } else {
                    c.getPA().imbuedHeart();
                    c.lastHeart = System.currentTimeMillis();
                }
                break;
            case 10006:
                Hunter.lay(c, new BirdSnare(c));
                break;
            case 10008:
            	c.startAnimation(5208);
                Hunter.lay(c, new BoxTrap(c));
                break;
            case 13249:
                if (!c.getSlayer().isCerberusRoute()) {
                    c.sendMessage("You have no clue how to navigate in here, you should find a slayer master to learn.");
                    return;
                }
                c.getPA().startTeleport(1310, 1237, 0, "keymaster", false);
                //AgilityHandler.delayFade(c, "", 1310, 1237, 0, "You teleport into the cave", "and end up at the main room.", 3);
                c.getItems().deleteItem(13249, 1);
                break;

            case 13226:
                c.getHerbSack().fillSack();
                break;

            case 12020:
                c.getGemBag().fillBag();
                break;

            case 5509:
                Pouches.fill(c, Pouch.forId(itemId), itemId, 0);
                break;
            case 5510:
                Pouches.fill(c, Pouch.forId(itemId), itemId, 1);
                break;
            case 6819:
                Pouches.fill(c, Pouch.forId(itemId), itemId, 2);
                break;

            case 952: //Spade
                int x = c.absX;
                int y = c.absY;
                if (Boundary.isIn(c, Barrows.GRAVEYARD)) {
                    c.getBarrows().digDown();
                }
                if (x == 3005 && y == 3376 || x == 2999 && y == 3375 || x == 2996 && y == 3377) {
          
                		c.getPA().movePlayer(1760, 5163, 0);
                        return;
                 
             }
                break;
        }

        if (itemId == 2678) {
            c.getDH().sendDialogues(657, -1);
            return;
        }
        if (itemId == 8015 || itemId == 8014) {
            NonCombatSpells.attemptDate(c, itemId);
        }
        
        if (itemId == 9553) {
            c.getPotions().eatChoc(9553, -1, itemSlot, 1, true);
        }
        if (itemId == 12846) {
            c.getDH().sendDialogues(578, -1);
        }
        if (itemId == 12938) {
            if (c.getZulrahEvent().getInstancedZulrah() != null) {
                c.sendMessage("It seems your currently in the zulrah instance, relog if this is false.");
                return;
            }
            c.getDH().sendDialogues(625, -1);
            return;
        }
        if (itemId == 19564) {
			if (c.wildLevel > 30) {
				c.sendMessage("You can't teleport above level 30 in the wilderness.");
				c.getPA().closeAllWindows();
				return;
			}
			c.getPA().startTeleport(3097, 3510, 0, "seedpod", false);
            return;
        }
        /*if (itemId == 405) {
			if (c.getItems().freeSlots() < 2) {
				c.sendMessage("You need at least 2 free slots to open this.");
				return;
			}
			c.getItems().deleteItem2(itemId, 1);
			c.getItems().addItem(995, 10000 + Misc.random(90000));
			c.getItems().addItem(1624, 1 + Misc.random(9));
			c.sendMessage("You open the casket and find some treasure inside.");
		}*/
        if (itemId == 4155) {
            if (!c.getSlayer().getTask().isPresent()) {
                c.sendMessage("You do not have a task, please talk with a slayer master!");
                return;
            }
            c.sendMessage("I currently have @blu@" + c.getSlayer().getTaskAmount() + " " + c.getSlayer().getTask().get().getPrimaryName() + "@bla@ to kill.");
            c.getPA().closeAllWindows();
        }
        if (itemId == 2839) {
            if (c.getSlayer().isHelmetCreatable() == true) {
                c.sendMessage("You have already learned this recipe. You have no more use for this scroll.");
                return;
            }
            if (c.getItems().playerHasItem(2839)) {
                c.getSlayer().setHelmetCreatable(true);
                c.sendMessage("You have learned the slayer helmet recipe. You can now assemble it");
                c.sendMessage("using a Black Mask, Facemask, Nose peg, Spiny helmet and Earmuffs.");
                c.getItems().deleteItem(2839, 1);
            }
        }
        if (itemId == DiceHandler.DICE_BAG) {
            DiceHandler.selectDice(c, itemId);
        }
        if (itemId > DiceHandler.DICE_BAG && itemId <= 15100) {
            DiceHandler.rollDice(c);
        }
        if (itemId == 2697) {
            c.sendMessage("You can't redeem this scroll on yourself!");
            c.sendMessage("Use this on another player to transfer $10 to their amount donated");
            c.sendMessage("This does NOT give the other player donator points.");
			/*if (c.inWild() || c.inDuelArena() || Server.getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (c.getItems().playerHasItem(2697, 1)) {
				c.getDH().sendDialogues(4000, -1);
			}*/
        }
        if (itemId == 2698) {
            c.sendMessage("You can't redeem this scroll on yourself!");
            c.sendMessage("Use this on another player to transfer $50 to their amount donated");
            c.sendMessage("This does NOT give the other player donator points.");
			/*if (c.inWild() || c.inDuelArena() || Server.getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (c.getItems().playerHasItem(2697, 1)) {
				c.getDH().sendDialogues(4000, -1);
			}*/
        }
        if (itemId == 2699) {
            c.sendMessage("You can't redeem this scroll on yourself!");
            c.sendMessage("Use this on another player to transfer $150 to their amount donated");
            c.sendMessage("This does NOT give the other player donator points.");
			/*if (c.inWild() || c.inDuelArena() || Server.getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (c.getItems().playerHasItem(2697, 1)) {
				c.getDH().sendDialogues(4000, -1);
			}*/
        }
        if (itemId == 2700) {
            c.sendMessage("You can't redeem this scroll on yourself!");
            c.sendMessage("Use this on another player to transfer $300 to their amount donated");
            c.sendMessage("This does NOT give the other player donator points.");
			/*if (c.inWild() || c.inDuelArena() || Server.getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (c.getItems().playerHasItem(2697, 1)) {
				c.getDH().sendDialogues(4000, -1);
			}*/
        }
        if (itemId == 2701) {
            if (c.inWild() || c.inDuelArena() || Server.getMultiplayerSessionListener().inAnySession(c)) {
                return;
            }
            if (c.getItems().playerHasItem(2701, 1)) {
                c.getDH().sendDialogues(4004, -1);
            }
        }
        if (itemId == 7509) {
            if (c.inWild() || c.inDuelArena() || Boundary.isIn(c, Boundary.DUEL_ARENA)) {
                c.sendMessage("You cannot do this here.");
                return;
            }
            if (c.getHealth().getStatus().isPoisoned() || c.getHealth().getStatus().isVenomed()) {
                c.sendMessage("You are effected by venom or poison, you should cure this first.");
                return;
            }
            if (c.getHealth().getAmount() <= 1) {
                c.sendMessage("I better not do that.");
                return;
            }
            c.forcedChat("Ow! I nearly broke a tooth!");
            c.startAnimation(829);
            // c.getHealth().reduce(1);
            c.appendDamage(1, Hitmark.HIT);
            return;
        }
        if (itemId == 10269) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10269, 1)) {
                c.getItems().addItem(995, 30000);
                c.getItems().deleteItem(10269, 1);
            }
        }
        if(itemId == 1464) {
            c.getPA().exchangeItems(PlayerAssistant.PointExchange.VOTE_POINTS, 1464, 1);
        }
        if (itemId == 10271) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10271, 1)) {
                c.getItems().addItem(995, 10000);
                c.getItems().deleteItem(10271, 1);
            }
        }
        if (itemId == 10273) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10273, 1)) {
                c.getItems().addItem(995, 14000);
                c.getItems().deleteItem(10273, 1);
            }
        }
        if (itemId == 10275) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10275, 1)) {
                c.getItems().addItem(995, 18000);
                c.getItems().deleteItem(10275, 1);
            }
        }
        if (itemId == 10277) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10277, 1)) {
                c.getItems().addItem(995, 22000);
                c.getItems().deleteItem(10277, 1);
            }
        }
        if (itemId == 10279) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10279, 1)) {
                c.getItems().addItem(995, 26000);
                c.getItems().deleteItem(10279, 1);
            }
        }
        
		/* Mystery box */
        if (itemId == 6199)
            if (c.getItems().playerHasItem(6199)) {
                c.getMysteryBox().open();
                return;
            }
        
        if (itemId == 13245)
            if (c.getItems().playerHasItem(13245)) {
                c.getJarofSouls().open();
                return;
            }
        
        if (itemId == 5020)
            if (c.getItems().playerHasItem(5020)) {
             	c.startAnimation(712);
                c.getScratchTicket().open();
                return;
                }
        
        if (itemId == 11738)
            if (c.getItems().playerHasItem(11738)) {
                c.getHerbBox().open();
                return;
            }
        if (itemId == 11739)
            if (c.getItems().playerHasItem(11739)) {
                c.getHourlyRewardBox().open();
                return;
            }
        if (itemId == 405) //Pvm Casket
            if (c.getItems().playerHasItem(405)) {
                c.getPvmCasket().open();
                return;
            }
        if (itemId == 21307) //Pursuit Crate
            if (c.getItems().playerHasItem(21307)) {
                c.getWildyCrate().open();
                return;
            }
        if (itemId == 20703) //Daily Gear Box
            if (c.getItems().playerHasItem(20703)) {
                c.getDailyGearBox().open();
                return;
            }
        if (itemId == 20791) //Daily Skilling Box
            if (c.getItems().playerHasItem(20791)) {
                c.getDailySkillBox().open();
                return;
            }
		/*if (itemId == 7310) //Skill Casket
			if (c.getItems().playerHasItem(7310)) {
				c.getSkillCasket().open();
				return;
			}*/
        if (itemId == 6542)
            if (c.getItems().playerHasItem(6542)) {
                c.getChristmasPresent().open();
                return;
            }
        if (itemId == 2714) { // Easy Clue Scroll Casket
            c.getItems().deleteItem(itemId, 1);
            c.getTrails().addRewards(RewardLevel.EASY);
            c.setEasyClueCounter(c.getEasyClueCounter() + 1);
            c.sendMessage("@blu@You have completed " + c.getEasyClueCounter() + " easy Treasure Trails.");
        }
        if (itemId == 2802) { // Medium Clue Scroll Casket
            c.getItems().deleteItem(itemId, 1);
            c.getTrails().addRewards(RewardLevel.MEDIUM);
            c.setMediumClueCounter(c.getMediumClueCounter() + 1);
            c.sendMessage("@blu@You have completed " + c.getMediumClueCounter() + " medium Treasure Trails.");
        }
        if (itemId == 2775) { // Hard Clue Scroll Casket
            c.getItems().deleteItem(itemId, 1);
            c.getTrails().addRewards(RewardLevel.HARD);
            c.setHardClueCounter(c.getHardClueCounter() + 1);
            c.sendMessage("@blu@You have completed " + c.getHardClueCounter() + " hard Treasure Trails.");
        }
        if (itemId == 19841) { // Master Clue Scroll Casket
            if (c.getItems().playerHasItem(19841)) {
                c.getItems().deleteItem(itemId, 1);
                c.getTrails().addRewards(RewardLevel.MASTER);
                c.setMasterClueCounter(c.getMasterClueCounter() + 1);
                c.sendMessage("@blu@You have completed " + c.getMasterClueCounter() + " master Treasure Trails.");
                if (Misc.random(200) == 2 && c.getItems().getItemCount(19730, true) == 0 && c.summonId != 19730) {
                    PlayerHandler.executeGlobalMessage("[<col=CC0000>Clue</col>] @cr20@ <col=255>" + c.playerName
                            + "</col> hit the jackpot and got a <col=CC0000>Bloodhound</col> pet!");
                    c.getItems().addItemUnderAnyCircumstance(19730, 1);
                }
            }
        }
        if (itemId == 2677) {
            Achievements.increase(c, AchievementType.CLUES, 1);
            c.getItems().deleteItem(itemId, 1);
            c.getItems().addItem(2714, 1);
            c.sendMessage("@pur@You've received a easy clue scroll casket.");
        }
        if (itemId == 2801) {
            Achievements.increase(c, AchievementType.CLUES, 1);
            c.getItems().deleteItem(itemId, 1);
            c.getItems().addItem(2802, 1);
            c.sendMessage("@pur@You've received a medium clue scroll casket.");
        }
        if (itemId == 2722) {
            Achievements.increase(c, AchievementType.CLUES, 1);
            c.getItems().deleteItem(itemId, 1);
            c.getItems().addItem(2775, 1);
            c.sendMessage("@pur@You've received a hard clue scroll casket.");
        }
        /**
         * Master clue scroll
         */
        if (itemId == 19835) {
            MasterClue.progressScroll(c);
        }
        if (itemId == 2528) {
            c.usingLamp = true;
            c.normalLamp = false;
            c.antiqueLamp = true;
            c.sendMessage("You rub the lamp...");
            c.getPA().showInterface(2808);
        }
		/*
		 * if (itemId == 4447) {
		 *  c.usingLamp = true;
		 *   c.antiqueLamp = true;
		 *    c.normalLamp = false;
		 *    c.sendMessage("You rub the antique lamp of 13 million experience..." );
		 * c.getPA().showInterface(2808);
		 *  }
		 *   if (itemId == 2528) {
		 *    c.usingLamp = true;
		 *     c.normalLamp = true;
		 *      c.antiqueLamp = false;
		 *       c.sendMessage("You rub the lamp of 1 million experience...");
		 *        c.getPA().showInterface(2808);
		 *         }
		 */

    }

}