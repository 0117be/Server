package ethos.model.players.packets;

import java.util.Objects;

import ethos.Config;
import ethos.Server;
import ethos.model.content.DiceHandler;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.multiplayer_session.MultiplayerSessionFinalizeType;
import ethos.model.multiplayer_session.MultiplayerSessionStage;
import ethos.model.multiplayer_session.MultiplayerSessionType;
import ethos.model.multiplayer_session.duel.DuelSession;
import ethos.model.players.PacketType;
import ethos.model.players.Player;
import ethos.model.players.skills.runecrafting.Pouches;
import ethos.model.players.skills.runecrafting.Pouches.Pouch;

/**
 * Wear Item
 **/
public class WearItem implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int wearId = c.wearId;
		wearId = c.getInStream().readUnsignedWord();
		c.wearSlot = c.getInStream().readUnsignedWordA();
		c.interfaceId = c.getInStream().readUnsignedWordA();
		c.alchDelay = System.currentTimeMillis();
		c.nextChat = 0;
		c.dialogueOptions = 0;
		
		if (!c.getItems().playerHasItem(wearId, 1)) {
			return;
		}
		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
			return;
		}
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.isStuck) {
			c.isStuck = false;
			c.sendMessage("@red@You've disrupted stuck command, you will no longer be moved home.");
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if (Server.getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
			Server.getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			c.sendMessage("You cannot remove items from your equipment whilst trading, trade declined.");
			return;
		}
		DuelSession duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}
		if ((c.playerIndex > 0 || c.npcIndex > 0) && wearId != 4153 && wearId != 12848 && !c.usingMagic && !c.usingBow && !c.usingOtherRangeWeapons && !c.usingCross && !c.usingBallista)
			c.getCombat().resetPlayerAttack();
		if (c.canChangeAppearance) {
			c.sendMessage("You can't wear an item while changing appearence.");
			return;
		}
		if (wearId == 4155) {
			if (!c.getSlayer().getTask().isPresent()) {
				c.sendMessage("You do not have a task!");
				return;
			}
			c.sendMessage("I currently have @blu@" + c.getSlayer().getTaskAmount() + " " + c.getSlayer().getTask().get().getPrimaryName() + "@bla@ to kill.");
			c.getPA().closeAllWindows();
			return;
		}
		switch (wearId) {
		case 21347:
			c.boltTips = true;
			c.arrowTips = false;
			c.javelinHeads = false;
			c.sendMessage("Your Amethyst method is now Bolt Tips!");
			break;
		case 5509:
			Pouches.empty(c, Pouch.forId(wearId), wearId, 0);
			break;
		case 5510:
			Pouches.empty(c, Pouch.forId(wearId), wearId, 1);
			break;
		case 6819:
			Pouches.empty(c, Pouch.forId(wearId), wearId, 2);
			break;
		case 22557:
			c.headIconPk = 0;
			c.isSkulled = true;
			c.skullTimer = Config.SKULL_TIMER;
			break;
		case 13280:
		case 13329:
		case 13337:
		case 13331:
		case 13333:
		case 13335:
		case 20760:
		case 21285:
		case 21776:
		case 21780:
		case 21784:
		case 21898:
			Achievements.increase(c, AchievementType.WEAR_MAX, 1);
	break;
		case 9747:
		case 9748:
		case 9750:
		case 9751:
		case 9753:
		case 9754:
		case 9768:
		case 9769:
		case 9756:
		case 9757:
		case 9759:
		case 9760:
		case 9762:
		case 9763:
		case 9801:
		case 9802:
		case 9807:
		case 9808:
		case 9783:
		case 9784:
		case 9798:
		case 9799:
		case 9804:
		case 9805:
		case 9780:
		case 9781:
		case 9795:
		case 9796:
		case 9792:
		case 9793:
		case 9771:
		case 9772:
		case 9774:
		case 9775:
		case 9777:
		case 9778:
		case 9786:
		case 9787:
		case 9810:
		case 9811:
		case 9765:
		case 9766:	
		case 9948:
		case 9949:
		case 9789:
		case 9790:
		case 9813:
			Achievements.increase(c, AchievementType.WEAR_99, 1);
			break;
		}
		if (wearId == DiceHandler.DICE_BAG) {
			DiceHandler.selectDice(c, wearId);
		}
		if (wearId > DiceHandler.DICE_BAG && wearId <= 15100) {
			DiceHandler.rollDice(c);
		}
		
		if (!Server.getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
			c.getItems().wearItem(wearId, c.wearSlot);
		}
	}

}
