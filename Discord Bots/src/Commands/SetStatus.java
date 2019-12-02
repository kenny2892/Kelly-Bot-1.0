package Commands;

import java.awt.Color;

import Main.KellyBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SetStatus extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		if(args.length < 3 || !(args[1].equalsIgnoreCase("playing") || args[1].equalsIgnoreCase("listening") | args[1].equalsIgnoreCase("watching")))
		{
			EmbedBuilder notEnoughArgs = new EmbedBuilder();
			notEnoughArgs.setTitle("Error: Improper Input");
			notEnoughArgs.setDescription("😕 You didn't tell me what to play! Try typing: " + KellyBot.getPrefix() + "status [playing Or listening Or watching] [what you want me to play]");
			notEnoughArgs.setColor(Color.red);

			event.getChannel().sendMessage(notEnoughArgs.build()).queue();
			notEnoughArgs.clear();
		}
		
		else
		{
			String type = args[1].toLowerCase();
			String game = "";
			for(int i = 2; i < args.length; i++)
				game += args[i] + " ";
			
			game = game.substring(0, game.length() - 1);
			
			switch(type)
			{
			case "playing":
				event.getJDA().getPresence().setGame(Game.playing(game));
				break;
				
			case "listening":
				event.getJDA().getPresence().setGame(Game.listening(game));
				break;
				
			case "watching":
				event.getJDA().getPresence().setGame(Game.watching(game));
				break;
				
			default:
				event.getJDA().getPresence().setGame(Game.playing(game));
				break;
			}
			
			EmbedBuilder setPlay = new EmbedBuilder();
			setPlay.setTitle("Current Status has Changed");
			setPlay.setDescription("😃 Ok, now I'm " + type + ": " + game);
			setPlay.setColor(Color.orange);

			event.getChannel().sendMessage(setPlay.build()).queue();
			setPlay.clear();
		}
	}

	@Override
	public String getCmdName() 
	{
		return "Playing";
	}

	@Override
	public String getCmd() 
	{
		return "playing";
	}

	@Override
	public String getDesc() 
	{
		return "😊 Set what I am currently playing!";
	}
}
