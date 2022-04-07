package ethos.model.content;

import java.util.ArrayList;
import java.util.List;

import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

/*
 * @author Robbie
 */
public class TriviaBot {
	
	public static final int TIMER = 15; //1800
	public static int botTimer = TIMER;
	
	public static int answerCount;
	public static String firstPlace;
	public static String secondPlace;
	public static String thirdPlace;

	public static List<String> attempts = new ArrayList<>(); 

	public static void sequence() {
		botTimer = TIMER;
		if(botTimer > 0)
			botTimer--;
		
		if(botTimer <= 0) {
			PlayerHandler.executeGlobalMessage("@blu@[Trivia] @red@No one answered on time!");
			currentQuestion = "";
			didSend = false;
			botTimer = TIMER;
			answerCount = 0;
			askQuestion();
		}
	}
	
	public static void attemptAnswer(Player p, String attempt) {
		
		if (!currentQuestion.equals("") && attempt.replaceAll("_", " ").equalsIgnoreCase(currentAnswer)) {
			
			/*if (botTimer >= 600) {
				World.sendMessage("@blu@[Trivia] @red@No one answered on time!");
				didSend = false;
				botTimer = TIMER;
				answerCount = 0;
				return;
			}*/
			if (answerCount == 0) {
				answerCount++;
				p.incrementTriviaPoints(10);
				p.sendMessage("You Recieved 10 trivia points for @red@1st Place.");
				firstPlace = p.getName();
				return;
			}
			if (answerCount == 1) {
				if (p.getName().equalsIgnoreCase(firstPlace)) {
					p.sendMessage("Already answered");
					return;
				}
				answerCount++;
				p.incrementTriviaPoints(6);
				p.sendMessage("You Recieved 6 trivia points for @red@2nd Place.");
				secondPlace = p.getName();
				return;
			
			}
			if (answerCount == 2) {
				if (p.getName().equalsIgnoreCase(firstPlace) || p.getName().equalsIgnoreCase(secondPlace)) {
					p.sendMessage("Already answered");
					return;
				}
				p.incrementTriviaPoints(4);
				p.sendMessage("You Recieved 4 trivia points for @red@3rd Place.");
				thirdPlace = p.getName();
				PlayerHandler.executeGlobalMessage("@blu@[Trivia] @bla@[1st:@blu@" +firstPlace+"@red@ (10 pts)@bla@] @bla@[2nd:@blu@" +secondPlace+"@red@ (6 pts)@bla@] [3rd:@blu@" +thirdPlace+"@red@  (4 pts)@bla@]");
				//String[] s = Arrays.asList(attempts);
				//World.sendMessage("@blu@[Trivia] @red@Failed attempts: "+s);
				currentQuestion = "";
				didSend = false;
				botTimer = TIMER;
				answerCount = 0;
				return;
			}
		} else {
			if(attempt.contains("question") || attempt.contains("repeat")){
				p.sendMessage("<col=800000>"+(currentQuestion));
				return;
			}

			//attempts.add(attempt); // need to add a filter for bad strings (advs, curses)
			p.sendMessage("@blu@[Trivia]@red@ Sorry! Wrong answer! The current question is: +");
			p.sendMessage("@blu@[Trivia]@red@ "+(currentQuestion));
			return;
		}
		
	}
	
	public static boolean acceptingQuestion() {
		return !currentQuestion.equals("");
	}
	
	public static void askQuestion() {
		for (int i = 0; i < Trivia_DATA.length; i++) {
			if (Misc.random(Trivia_DATA.length - 1) == i) {
				if(!didSend) {
					didSend = true;
				currentQuestion = Trivia_DATA[i][0];
				currentAnswer = Trivia_DATA[i][1];
				PlayerHandler.executeGlobalMessage(currentQuestion);
				
				
				}
			}
		}
	}
	
	public static boolean didSend = false;
	
	private static final String[][] Trivia_DATA = {
		{"@blu@[Trivia]@red@ What rank has a silver crown on Runescape?", "Moderator"},
		{"@blu@[Trivia]@red@ What rank has a golden crown on Runescape?", "Administrator"},
		{"@blu@[Trivia]@red@ How much exp. do you need for level 99?", "13M"},
		{"@blu@[Trivia]@red@ How much exp. do you need for Dungeoneering level 120 on Runescape?", "104M"},
		{"@blu@[Trivia]@red@ What is the largest state in the U.S.A?", "Alaska"},
		{"@blu@[Trivia]@red@ What city is the most populated city on earth?", "Shanghai"},
		{"@blu@[Trivia]@red@ What is the biggest manmade structure on earth?", "The Great Wall"},
		{"@blu@[Trivia]@red@ What is the strongest prayer on Runescape?", "Turmoil"},
		{"@blu@[Trivia]@red@ What Herblore level is required to make overloads on Runescape?", "96"},
		{"@blu@[Trivia]@red@ What attack level is required to wear Chaotic Melee weapons?", "80"},
		{"@blu@[Trivia]@red@ How many bones are there in an adult human body?", "206"},
		{"@blu@[Trivia]@red@ What is the deadliest insect on the planet?", "Mosquito"},
		{"@blu@[Trivia]@red@ What is the square root of 12 to the power of 2?", "12"},
		{"@blu@[Trivia]@red@ What is the color of a 10M money stack?", "Green"},
		{"@blu@[Trivia]@red@ What combat level is the almighty Jad?", "702"},
		{"@blu@[Trivia]@red@ What is the best Dungeoneering armour?", "Primal"},
		{"@blu@[Trivia]@red@ How many brothers are there originally in Runescape?", "6"},
		{"@blu@[Trivia]@red@ Varrock is the capital of which kingdom?", "Misthalin"},
		{"@blu@[Trivia]@red@ Which NPC is wearing a 2H-Sword and a Dragon SQ Shield?", "Vannaka"},
		{"@blu@[Trivia]@red@ What is the baby spider called?", "Spiderling"},
		{"@blu@[Trivia]@red@ In what year did it snow in the Sahara Desert?", "1979"},
		{"@blu@[Trivia]@red@ The more you take, the more you leave, who am I?", "Footsteps"},
		{"@blu@[Trivia]@red@ What is 9 + 10?", "19"},
		{"@blu@[Trivia]@red@ What defence level is required to wear Vanguard?", "85"},
		{"@blu@[Trivia]@red@ What magic level is required to cast fire bolt?", "35"},
		{"@blu@[Trivia]@red@ What is 9 + 10?", "19"},
		{"@blu@[Trivia]@red@ Pressing F1 will open what?", "Inventory"},
		{"@blu@[Trivia]@red@ In which quest must you slay Elvarg the Dragon?", "Dragon Slayer"},
		{"@blu@[Trivia]@red@ What is the maximum exp. you can gain in a skill?", "1b"},
		{"@blu@[Trivia]@red@ What is a barracuda?", "Fish"},
		{"@blu@[Trivia]@red@ Which countrys flag features a maple leaf?", "Canada"},
		{"@blu@[Trivia]@red@ How many cards are in a deck of playing cards?", "52"},
		{"@blu@[Trivia]@red@ How do you get to the gambling area?", "::gamble"},
		{"@blu@[Trivia]@red@ Which minigame has teams of Zamorak and Saradomin?", "Castle wars"},
		{"@blu@[Trivia]@red@ What monster drops dragon claws?", "Tormented demon"},
		{"@blu@[Trivia]@red@ Who created Trayas clue guide?", "Nutmeg"},
		{"@blu@[Trivia]@red@ What is the name of our discord channel?", "Traya"},
		{"@blu@[Trivia]@red@ How do you claim vote rewards in-game?", "::reward 1"},
		{"@blu@[Trivia]@red@ Who are the co-founders of Traya?", "Mike and Robbie"},
		{"@blu@[Trivia]@red@ Who is the community manager of Traya?", "Gucci"},
		{"@blu@[Trivia]@red@ Who is the staff manager of Traya?", "Dotson"},
		{"@blu@[Trivia]@red@ How many coins are required to fill the Well?", "100m"},
		{"@blu@[Trivia]@red@ What is the highest donator rank?", "Legendary"},
		{"@blu@[Trivia]@red@ How many donator zones are in Traya?", "2"},
		{"@blu@[Trivia]@red@ Which monster drops the toxic blowpipe?", "Zulrah"},
		{"@blu@[Trivia]@red@ Which combat type is least effective in the Zombies minigame?", "Magic"},
		{"@blu@[Trivia]@red@ What cc can I join to ask for assistance?", "help"},
		{"@blu@[Trivia]@red@ If i'm stuck on a clue, what command can I use to get hints?", "::clue"},
		{"@blu@[Trivia]@red@ What slayer level is required to kill Nezikchened?", "95"},
		{"@blu@[Trivia]@red@ Where can you report a bug/glitch?", "Discord"},
		{"@blu@[Trivia]@red@ Where can i make suggestions to improve traya?", "Forums"},
		{"@blu@[Trivia]@red@ How many bones are in an adult human body?", "206"},
		{"@blu@[Trivia]@red@ What is the deadliest insect on the planet?", "Mosquito"},
		{"@blu@[Trivia]@red@ Which member of the Beatles was assassinated in 1980?", "John Lennon"},
		{"@blu@[Trivia]@red@ How do lizards communicate?", "Push ups"},
		{"@blu@[Trivia]@red@ What year was the movie e.t. released?", "1982"},
		{"@blu@[Trivia]@red@ How often does the wildywyrm spawn?", "Every hour"},
		{"@blu@[Trivia]@red@ On what mountain does apollo reside?", "Mount Olympus"},
		{"@blu@[Trivia]@red@ How many earths would fit into the sun?", "1.3 million"},
		{"@blu@[Trivia]@red@ What was the last letter added to the english alphabet?", "J"},
		{"@blu@[Anagram]@red@ Smerta", "Master"},
		{"@blu@[Anagram]@red@ Apre", "Pear"},
		{"@blu@[Anagram]@red@ Ekomyn", "Monkey"},
		{"@blu@[Anagram]@red@ Tevo", "Vote"},
		{"@blu@[Anagram]@red@ Grando", "Dragon"},
		{"@blu@[Anagram]@red@ Krcosalti", "Rocktails"},
		{"@blu@[Anagram]@red@ Hicacot", "Chaotic"},
		{"@blu@[Anagram]@red@ Rihucrnea", "Hurricane"},
		{"@blu@[Anagram]@red@ Damanta", "Adamant"},
		{"@blu@[Anagram]@red@ Gemldaono", "Megalodon"},
		{"@blu@[Anagram]@red@ Geertips", "Prestige"},
		{"@blu@[Anagram]@red@ Edrotmoar", "Moderator"},
		{"@blu@[Anagram]@red@ Guerdoningene", "Dungeoneering"},
	};
	
	public static String currentQuestion;
	private static String currentAnswer;
}