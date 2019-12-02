package Commands;

import java.awt.Color;

import Main.KellyBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SetPrefix extends CommandTemplate
{	
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event)
	{
		if(args.length < 2) // No 2nd Arg
		{
			EmbedBuilder notEnoughArgs = new EmbedBuilder();
			notEnoughArgs.setTitle("Error: Improper Input");
			notEnoughArgs.setDescription("😕 You didn't tell me what to change it to! Try typing: " + KellyBot.getPrefix() + "prefix [your new prefix key]");
			notEnoughArgs.setColor(Color.red);

			event.getChannel().sendMessage(notEnoughArgs.build()).queue();
			notEnoughArgs.clear();
		}

		else // Has 2 Args
		{
			KellyBot.setPrefix(args[1]);

			EmbedBuilder setPrefix = new EmbedBuilder();
			setPrefix.setTitle("Changing the Prefix");
			setPrefix.setDescription("😊 Ok, the new prefix for all my commands is: " + KellyBot.getPrefix());
			setPrefix.setColor(Color.orange);

			event.getChannel().sendMessage(setPrefix.build()).queue();
			setPrefix.clear();
		}
	}

	@Override
	public String getCmd() 
	{
		return "prefix [new prefix]";
	}

	@Override
	public String getDesc() 
	{
		return "Change the prfix that is used before all of my commands!";
	}

	@Override
	public String getCmdName() 
	{
		return "Prefix";
	}
}
