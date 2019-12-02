package Main;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.login.LoginException;

import APIs.YouTubeAPI;
import Commands.AutoReference;
import Commands.CommandTemplate;
import Commands.Commands;
import Commands.Commands.Types;
import Config.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class KellyBot extends Application
{
	private static String prefix = "~";
	private static File settingsFile;
	private static JDABuilder kellyBot;
	private static JDA jda;
	private static final Commands COMMANDS = new Commands();
	private static Config config = null;
	private static boolean isOn = false;
	private static Map<String, String> channels;
	private static Map<String, ArrayList<String>> servers;

	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		if(YouTubeAPI.KEY.compareTo("Your YouTube API Key Here!") == 0)
		{
			System.out.println("You have yet to enter your YouTubeAPI Key!");
			return;
		}
		
		try 
		{
			Parent root = FXMLLoader.load(getClass().getResource("/Main/Main.fxml"));
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("/Main/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
			primaryStage.show();
			
			double height = scene.getHeight();
			double width = scene.getWidth();
			
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			double newX = ((screenBounds.getWidth() - width) / 2);
			double newY = ((screenBounds.getHeight() - height) / 2);
			
			primaryStage.setX(newX);
			primaryStage.setY(newY);
			
			createBot();
		} 
		
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void createBot()
	{		
		Path dir = Paths.get(""); // Current Directory
		settingsFile = new File(dir.toAbsolutePath().toString() + "\\Settings.json");
		
		if(settingsFile.exists())
		{
			try
			{
				config = new Config(settingsFile);
				
				String clearString = config.getString("Clear");
				String[] clearWhitelist = clearString.split(", ");
				loadWhitelist("Clear", clearWhitelist);
				
				String setPrefixString = config.getString("SetPrefix");
				String[] setPrefixWhitelist = setPrefixString.split(", ");
				loadWhitelist("SetPrefix", setPrefixWhitelist);
				
				String prefixString = config.getString("Prefix");
				loadSettings("Prefix", prefixString);
				
				String playingString = config.getString("Playing Type");
				loadSettings("Playing Type", playingString);
			}

			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("No Settings Found.\nShutting Down.");
				return;
			}
		}
		
		else
		{
			System.out.println("No Settings Found.\nShutting Down.");
			return;
		}
		
		kellyBot = new JDABuilder();
		
		kellyBot.setToken(config.getString("token"));
		kellyBot.setStatus(OnlineStatus.ONLINE);
		
		kellyBot.addEventListener(COMMANDS);
		kellyBot.addEventListener(new AutoReference()); // This is kept separate because it isn't meant for users
	}
	
	public static ArrayList<ArrayList<String>> getCmdInfo()
	{
		ArrayList<CommandTemplate> cmds = COMMANDS.cmdClones();
		ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < cmds.size(); i++)
		{
			CommandTemplate cmd = cmds.get(i);
			
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(cmd.getCmdName());
			temp.add(cmd.getCmd());
			temp.add(cmd.getDesc());
			
			info.add(temp);
		}
		
		return info;
	}
	
	private static void loadWhitelist(String currCmd, String[] whitelist)
	{
		ArrayList<String> whiteAra = new ArrayList<String>();
		for(String role : whitelist)
			whiteAra.add(role);
		
		switch(currCmd.toLowerCase())
		{
		case "clear":
			COMMANDS.setWhitelist(Types.Clear, whiteAra);
			break;
			
		case "prefix":
			COMMANDS.setWhitelist(Types.SetPrefix, whiteAra);
			break;
			
		case "playing":
			COMMANDS.setWhitelist(Types.SetStatus, whiteAra);
			break;
		}
	}
	
	private static void loadSettings(String currCmd, String setting)
	{
		switch(currCmd)
		{
			case "Prefix":
				prefix = setting;
				break;
				
			case "Playing Type":
				setPlaying(setting);
				break;
		}
	}
	
	private static void setPlaying(String type)
	{
		String game = config.getString("Playing");
		
		type = type.toLowerCase();
		if(game != null && game.length() != 0)
		{
			switch(type)
			{
			case "playing":
				kellyBot.setGame(Game.playing(game));
				break;
				
			case "streaming":
//				kellyBot.setGame(Game.streaming(arg0, arg1));
				break;
				
			case "listening":
				kellyBot.setGame(Game.listening(game));
				break;
				
			case "watching":
				kellyBot.setGame(Game.watching(game));
				break;
			
			case "":
				break;
				
			default:
				kellyBot.setGame(Game.playing(game));
				break;
			}
		}
	}
	
	public static Commands getCmds()
	{
		return COMMANDS;
	}
	
	public static void turnOn() throws LoginException
	{
		if(!isOn)
		{
			jda = kellyBot.build();
			isOn = true;
			
			channels = new HashMap<String, String>();
			servers = new HashMap<String, ArrayList<String>>();
			
			try
			{
				Thread.sleep(2000);
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			for(Channel channel : jda.getTextChannels())
			{
				channels.put(channel.getName(), channel.getId());
				
				String serverName = channel.getGuild().getName();
				if(!servers.containsKey(serverName))
					servers.put(serverName, new ArrayList<String>());
				
				servers.get(serverName).add(channel.getName());
			}
		}
	}
	
	public static boolean isOn()
	{
		return isOn;
	}
	
	public static GuildMessageReceivedEvent createDefaultEvent()
	{
		if(!isOn)
			return null;
		
		List<TextChannel> channels = jda.getTextChannels();
		List<Message> messages = null;
		Message msg = null;
		
		if(channels.size() == 0)
			return null;
		
		for(TextChannel channel : channels)
		{
			if(channel.canTalk())
			{
				messages = channels.get(0).getHistory().retrievePast(1).complete();
				
				if(messages.size() != 0)
				{
					msg = messages.get(0);
					break;
				}
			}
		}
		
		if(msg != null)
			return new GuildMessageReceivedEvent(jda, 4, msg);
		
		return null;
	}
	
	public static void createDefaultEvent(String channelName)
	{
		if(!isOn)
			return;
		
		TextChannel channel = jda.getTextChannelById(channels.get(channelName));
		List<Message> messages = null;
		Message msg = null;
		
		if(channel == null || !channel.canTalk())
			return;
		
		else if(channel.getName().equals(channelName))
		{
			messages = channel.getHistory().retrievePast(1).complete();
			if(messages.size() != 0)
				msg = messages.get(0);
		}
		
		if(msg != null)
			COMMANDS.setDefaultChannel(new GuildMessageReceivedEvent(jda, 4, msg));
	}
	
	public static ArrayList<String> getTextChannels(String serverName)
	{
		return servers.get(serverName);
	}
	
	public static ArrayList<String> getServers()
	{
		ArrayList<String> names = new ArrayList<String>();
		Set<String> keys = servers.keySet();
		
		for(String key : keys)
			names.add(key);
		
		return names;
	}
	
	public static String getPrefix()
	{
		return prefix;
	}
	
	public static void setPrefix(String newPrefix)
	{
		if(newPrefix == null || newPrefix.length() == 0)
			return;
		
		prefix = newPrefix;
	}
}
