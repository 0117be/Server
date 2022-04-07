package ethos.net.discord;

import java.awt.Color;
import java.net.URI;

import org.json.JSONObject;

import ethos.net.discord.Misc;

import ethos.net.discord.DiscordMessage;
import ethos.net.discord.WebhookClient;
import ethos.net.discord.WebhookClientBuilder;
import ethos.net.discord.DiscordEmbed;


public class DiscordMessager extends JSONObject { 

	public static boolean active = true;
	
	private static String announcementhook = "https://discord.com/api/webhooks/906849896554967053/Lz8OEvuDSpkXKFMmkjeQZcSb9p4lFFl_8dFNDjGp5DFLmR2Rk8cLtKOIw0aimuB3nIbE";
	private static String newplayers = "https://discord.com/api/webhooks/906849896554967053/Lz8OEvuDSpkXKFMmkjeQZcSb9p4lFFl_8dFNDjGp5DFLmR2Rk8cLtKOIw0aimuB3nIbE";
	private static String lootations = "https://discord.com/api/webhooks/906849896554967053/Lz8OEvuDSpkXKFMmkjeQZcSb9p4lFFl_8dFNDjGp5DFLmR2Rk8cLtKOIw0aimuB3nIbE"; 
	private static String staffhook = "https://discord.com/api/webhooks/906849896554967053/Lz8OEvuDSpkXKFMmkjeQZcSb9p4lFFl_8dFNDjGp5DFLmR2Rk8cLtKOIw0aimuB3nIbE"; 
	private static String petshook = "https://discord.com/api/webhooks/906849896554967053/Lz8OEvuDSpkXKFMmkjeQZcSb9p4lFFl_8dFNDjGp5DFLmR2Rk8cLtKOIw0aimuB3nIbE";
	private static String debughook = "https://discord.com/api/webhooks/906849896554967053/Lz8OEvuDSpkXKFMmkjeQZcSb9p4lFFl_8dFNDjGp5DFLmR2Rk8cLtKOIw0aimuB3nIbE";
	private static String prestige = "https://discord.com/api/webhooks/906849896554967053/Lz8OEvuDSpkXKFMmkjeQZcSb9p4lFFl_8dFNDjGp5DFLmR2Rk8cLtKOIw0aimuB3nIbE";
	private static String bronze = "https://discord.com/api/webhooks/933174974087327764/vT05pZPXC4jL4tMGf6Eg-0mJ9ReYJ-LuzGZkmvS1P1w9meyHW9GG35jU4MiI4liVTeJW";
	
	
	public static void sendAnnouncement(String msg) {
		try {
			
			String webhook = announcementhook;
			
			WebhookClient client = new WebhookClientBuilder()
				    .withURI(new URI(webhook))
				    .build(); // Create the webhook client
			
			new DiscordEmbed.Builder()
				    .withTitle("Revelation RSPS") // The title of the embed element
				    .withURL("http://revelation-rsps.com/") // The URL of the embed element
				    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
				    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
				    .build();
			
			DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
				    //.withEmbed(embed) // Add our embed object
				    .withUsername("Announcement Bot") // Override the username of the bot
				    .build(); // Build the message
			
			client.sendPayload(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendPrestige(String msg) {
		try {
			
			String webhook = prestige;
			
			WebhookClient client = new WebhookClientBuilder()
				    .withURI(new URI(webhook))
				    .build(); // Create the webhook client
			
			new DiscordEmbed.Builder()
			 .withTitle("Revelation RSPS") // The title of the embed element
			    .withURL("http://revelation-rsps.com/") // The URL of the embed element
			    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
			    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
				    .build();
			
			DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
				    //.withEmbed(embed) // Add our embed object
				    .withUsername("Leveling Bot") // Override the username of the bot
				    .build(); // Build the message
			
			client.sendPayload(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendLootations(String msg) {
		try {
			
			String webhook = lootations;
			
			WebhookClient client = new WebhookClientBuilder()
				    .withURI(new URI(webhook))
				    .build(); // Create the webhook client
			
			new DiscordEmbed.Builder()
			 .withTitle("Revelation RSPS") // The title of the embed element
			    .withURL("http://revelation-rsps.com/") // The URL of the embed element
			    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
			    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
				    .build();
			
			DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
				    //.withEmbed(embed) // Add our embed object
				    .withUsername("Rare Drop Bot") // Override the username of the bot
				    .build(); // Build the message
			
			client.sendPayload(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendNewPlayers(String msg) {
		try {
			
			String webhook = newplayers;
			
			WebhookClient client = new WebhookClientBuilder()
				    .withURI(new URI(webhook))
				    .build(); // Create the webhook client
			
			new DiscordEmbed.Builder()
			 .withTitle("Revelation RSPS") // The title of the embed element
			    .withURL("http://revelation-rsps.com/") // The URL of the embed element
			    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
			    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
				    .build();
			
			DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
				    //.withEmbed(embed) // Add our embed object
				    .withUsername("New Players Bot") // Override the username of the bot
				    .build(); // Build the message
			
			client.sendPayload(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendStaffMessage(String msg) {
		try {
			
			String webhook = staffhook;
			
			WebhookClient client = new WebhookClientBuilder()
				    .withURI(new URI(webhook))
				    .build(); // Create the webhook client
			
			new DiscordEmbed.Builder()
				    //.withTitle("Necrotic - RSPS") // The title of the embed element
				    //.withURL("http://necrotic.org/") // The URL of the embed element
				    .withColor(Color.ORANGE) // The color of the embed. You can leave this at null for no color
				    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
				    .build();
			
			DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
				    //.withEmbed(embed) // Add our embed object
				    .withUsername("Staff Bot") // Override the username of the bot
				    .build(); // Build the message
			
			client.sendPayload(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendPetsMessage(String msg) {
		try {
			
			
			String webhook = petshook;
			
			WebhookClient client = new WebhookClientBuilder()
				    .withURI(new URI(webhook))
				    .build(); // Create the webhook client
			
			new DiscordEmbed.Builder()
				    //.withTitle("Necrotic - RSPS") // The title of the embed element
				    //.withURL("http://necrotic.org/") // The URL of the embed element
				    .withColor(Color.BLUE) // The color of the embed. You can leave this at null for no color
				    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
				    .build();
			
			String msgToSend = Misc.stripIngameFormat(msg);
			
			DiscordMessage message = new DiscordMessage.Builder(msgToSend) // The content of the message
				    //.withEmbed(embed) // Add our embed object
				    .withUsername("Pets Bot") // Override the username of the bot
				    .build(); // Build the message
			
			if (msgToSend.equalsIgnoreCase(":information_source:!")) {
				sendDebugMessage("Bad message from sendInGameMessage, \n"+msgToSend);
			} else {
				client.sendPayload(message);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendDebugMessage(String msg) {
		try {
			
			String webhook = debughook;
			
			WebhookClient client = new WebhookClientBuilder()
				    .withURI(new URI(webhook))
				    .build(); // Create the webhook client
			
			new DiscordEmbed.Builder()
			 .withTitle("Revelation RSPS") // The title of the embed element
			    .withURL("http://revelation-rsps.com/") // The URL of the embed element
			    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
			    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
				    .build();
			
			//DiscordMessage message = new DiscordMessage.Builder(msg) 
			DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
				    //.withEmbed(embed) // Add our embed object
				    .withUsername("Debug Bot") // Override the username of the bot
				    .build(); // Build the message
			
			client.sendPayload(message);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	public static void bronze(String msg) {
		try {
			
			String webhook = bronze;
			
			WebhookClient client = new WebhookClientBuilder()
				    .withURI(new URI(webhook))
				    .build(); // Create the webhook client
			
			new DiscordEmbed.Builder()
			 .withTitle("Revelation RSPS") // The title of the embed element
			    .withURL("http://revelation-rsps.com/") // The URL of the embed element
			    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
			    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
				    .build();
			
			DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
				    //.withEmbed(embed) // Add our embed object
				    .withUsername("Bronze Man") // Override the username of the bot
				    .build(); // Build the message
			
			client.sendPayload(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
