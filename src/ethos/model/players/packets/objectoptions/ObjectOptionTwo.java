package ethos.model.players.packets.objectoptions;

import ethos.Config;
import ethos.Server;
import ethos.clip.ObjectDef;
import ethos.model.players.Player;
import ethos.model.players.Position;
import ethos.model.players.Right;
import ethos.model.players.packets.objectoptions.impl.DarkAltar;
import ethos.model.players.skills.FlaxPicking;
import ethos.model.players.skills.thieving.Thieving.Stall;
import ethos.util.Location3D;
import ethos.util.Misc;

/*
 * @author Matt
 * Handles all 2nd options for objects.
 */

public class ObjectOptionTwo {

	public static void handleOption(final Player c, int objectType, int obX, int obY) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		c.clickObjectType = 0;
		c.getFarming().patchObjectInteraction(objectType, -1, obX, obY);
		if (Server.getHolidayController().clickObject(c, 2, objectType, obX, obY)) {
			return;
		}
		Location3D location = new Location3D(obX, obY, c.heightLevel);
		ObjectDef def = ObjectDef.getObjectDef(objectType);
		if ((def!=null ? def.name : null)!= null && def.name.toLowerCase().contains("bank")) {
			c.getPA().openUpBank();
		}
		if (c.getRights().isOrInherits(Right.OWNER))
			c.sendMessage("Clicked Object Option 2:  "+objectType+"");
		switch (objectType) {
		case 29772://Tree in raids that fill your Inventory with empty vials
			c.getItems().addItem(20800, c.getItems().freeSlots());
			c.sendMessage("@red@You quickly fill your inventory with Empty Gourd vials from the tree.");
			break;
        case 6:
            c.getCannon().pickup(new Position(c.objectX, c.objectY, c.heightLevel));
            break;
		case 29778:
			c.sendMessage("hello");
			break;
		case 28900:
			DarkAltar.handleRechargeInteraction(c);
			break;
		case 4309:
			c.turnPlayerTo(obX, obY);
			c.getDH().sendDialogues(40, -1);
			break;
		case 4004:
		case 6097:
		case 24150:
			if (Config.BONUS_XP_WOGW) {
				c.getDH().sendNpcChat1(
						"Well of Goodwill is currently @red@active@bla@! \\n It is granting 1 hour of @red@Double XP@bla@!",
						306, "Revelation Guide");
			} else if (Config.BONUS_MINIGAME_WOGW) {
				c.getDH().sendNpcChat1(
						"Well of Goodwill is currently @red@active@bla@! \\n It is granting 1 hour of @red@Double Pc Points@bla@!",
						306, "Revelation Guide");
			} else if (Config.DOUBLE_DROPS) {
				c.getDH().sendNpcChat1(
						"Well of Goodwill is currently @red@active@bla@! \\n It is granting 1 hour of @red@Double Drops@bla@!",
						306, "Revelation Guide");
			} else {
				c.getDH().sendNpcChat1("Well of Goodwill is currently @red@inactive@bla@!", 306, "Revelation Guide");
			}
		break;
		
		case 7811:
			if (!c.inClanWarsSafe()) {
				return;
			}
			c.getShops().openShop(115);
			break;
		/**
		 * Iron Winch - peek
		 */
			case 9157:
				c.getPA().showInterface(51000);
				c.getTeleport().selection(c, 0);
				break;
		case 23104:
			c.getDH().sendDialogues(110, 5870);
			break;
			
		case 2118:
			c.getPA().movePlayer(3434, 3537, 0);
			break;

		case 2114:
			c.getPA().movePlayer(3433, 3537, 1);
			break;
		case 25824:
			c.turnPlayerTo(obX, obY);
			c.getDH().sendDialogues(40, -1);
			break;
		case 26260:
			c.getDH().sendDialogues(55874, -1);
			break;
		case 14896:
			c.turnPlayerTo(obX, obY);
			FlaxPicking.getInstance().pick(c, new Location3D(obX, obY, c.heightLevel));
			break;
		
		case 3840: // Compost Bin
		case 7808:
		case 7837:
		case 7836:
			c.getFarming().handleCompostRemoval();
		break;
		case 4874:
		case 11729:
			c.getThieving().steal(Stall.Silk, objectType, location);
			break;
		case 11730:
			c.objectDistance=1;	
			c.getThieving().steal(Stall.Baker, objectType, location);
			break;
		case 4877:
		case 11731:
			c.getThieving().steal(Stall.Gem, objectType, location);
			break;
		case 11732:
			c.getThieving().steal(Stall.Fur, objectType, location);
			break;
		case 11733:
			c.getThieving().steal(Stall.Spice, objectType, location);
			break;
		case 635://tea stall in varrock
			c.getThieving().steal(Stall.Tea, objectType, location);
			break;
			
		case 30279:
			c.getThieving().steal(Stall.Rune, objectType, location);
			break;	
		case 30280:
			c.getThieving().steal(Stall.Infernogems, objectType, location);
			break;	
			
			
		case 11734:
		case 4876:
			c.getThieving().steal(Stall.Silver, objectType, location);
			break;
		case 7236:
			if (Misc.random(2) == 1) {
				c.getThieving().steal(Stall.Rogue, objectType, location);
			} else if (Misc.random(2) == 1) {
				c.getThieving().steal(Stall.Rogue1, objectType, location);
			} else if (Misc.random(2) == 1) {
				c.getThieving().steal(Stall.Rogue2, objectType, location);
			} else if (Misc.random(2) == 1) {
				c.getThieving().steal(Stall.Rogue3, objectType, location);
			} else if (Misc.random(2) == 1) {
				c.getThieving().steal(Stall.Rogue4, objectType, location);
			
				break;
			}
			break;
			
		case 32546:
			c.getPA().sendFrame126("https://revelation.everythingrs.com/services/vote", 12000);
			break;
			
		case 4878:
			c.getThieving().steal(Stall.Scimitar, objectType, location);
			break;
	
		case 4875:
			c.getThieving().steal(Stall.Baker, objectType, location);
			break;
			
		case 23609:
			c.getPA().movePlayer(3507, 9494, 0);
			break;
			
		case 2558:
		case 8356://streequid
			c.getPA().movePlayer(1255, 3568, 0);
			break;
		case 2557:
			if (System.currentTimeMillis() - c.lastLockPick < 1000 || c.freezeTimer > 0) {
				return;
			}
			c.lastLockPick = System.currentTimeMillis();
			if (c.getItems().playerHasItem(1523, 1)) {

				if (Misc.random(10) <= 2) {
					c.sendMessage("You fail to pick the lock.");
					break;
				}
				if (c.objectX == 3044 && c.objectY == 3956) {
					if (c.absX == 3045) {
						c.getPA().walkTo(-1, 0);
					} else if (c.absX == 3044) {
						c.getPA().walkTo(1, 0);
					}

				} else if (c.objectX == 3038 && c.objectY == 3956) {
					if (c.absX == 3037) {
						c.getPA().walkTo(1, 0);
					} else if (c.absX == 3038) {
						c.getPA().walkTo(-1, 0);
					}
				} else if (c.objectX == 3041 && c.objectY == 3959) {
					if (c.absY == 3960) {
						c.getPA().walkTo(0, -1);
					} else if (c.absY == 3959) {
						c.getPA().walkTo(0, 1);
					}
				} else if (c.objectX == 3191 && c.objectY == 3963) {
					if (c.absY == 3963) {
						c.getPA().walkTo(0, -1);
					} else if (c.absY == 3962) {
						c.getPA().walkTo(0, 1);
					}
				} else if (c.objectX == 3190 && c.objectY == 3957) {
					if (c.absY == 3957) {
						c.getPA().walkTo(0, 1);
					} else if (c.absY == 3958) {
						c.getPA().walkTo(0, -1);
					}
				}
			} else {
				c.sendMessage("I need a lockpick to pick this lock.");
			}
			break;
		case 7814:
			if (c.playerMagicBook == 0) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 838);
				c.sendMessage("An ancient wisdomin fills your mind.");
			} else if (c.playerMagicBook == 1) {
				c.sendMessage("You switch to the lunar spellbook.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
			} else if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 938);
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
			}
			break;
		case 17010:
			if (c.playerMagicBook == 0) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 838);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 1) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 938);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			break;
		/*
		 * One stall that will give different amount of money depending on your thieving level, also different amount of xp.
		 */
		case 2781:
		case 26814:
		case 11666:
		case 3044:
		case 16469:
		case 2030:
		case 24009:
		case 26300:
		case 10082:
			c.getSmithing().sendSmelting();
			break;
			
			/**
		 * Opening the bank.
		 */
		case 24101:
		case 14367:
		case 11758:
		case 10517:
		case 26972:
		case 25808:
		case 11744:
		case 11748:
		case 10060:
		case 24347:
		case 16700:
		case 32666:
			c.getPA().openUpBank();
			break;

		}
	}
}
