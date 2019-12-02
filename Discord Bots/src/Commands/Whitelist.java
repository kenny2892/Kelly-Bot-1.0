package Commands;

import java.awt.Color;
import java.util.ArrayList;

import Commands.Commands.Types;
import Main.KellyBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Whitelist extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		if(args.length >= 3)
		{
			if(!args[1].equalsIgnoreCase("show") && !args[1].equalsIgnoreCase("remove") && !args[1].equalsIgnoreCase("add"))
			{
				error(event);
				return;
			}
			
			if(args[1].equalsIgnoreCase("show"))
			{
				show(args, event);
				return;
			}
			
			ArrayList<String> roles = new ArrayList<String>();
			
			String allRoles = "";
			for(int i = 3; i < args.length; i++)
				allRoles += args[i] + " ";
			
			allRoles = allRoles.substring(0, allRoles.length() - 1);
			
			if(allRoles.contains(","))
			{
				String[] splitRoles = allRoles.split(", ");
				
				for(String role : splitRoles)
					roles.add(role);
			}
			
			else
				roles.add(allRoles);
			
			addOrRemove(args, event, roles);
		}
		
		else
			error(event);
	}
	
	private void error(GuildMessageReceivedEvent event)
	{
		EmbedBuilder error = new EmbedBuilder();
		error.setTitle("Error: That's not how this Command works.");
		error.setDescription("😟 Please try again with " + KellyBot.getPrefix() + getCmd());
		error.setColor(Color.red);

		event.getChannel().sendMessage(error.build()).queue();
		error.clear();
	}
	
	private void show(String[] args, GuildMessageReceivedEvent event)
	{
		if(args.length == 2)
		{
			EmbedBuilder missingArg = new EmbedBuilder();
			missingArg.setTitle("Error: No Command to View");
			missingArg.setDescription("😕 You never told me what command to show the whitelist of! Try this: \"" + KellyBot.getPrefix() + "whitelist [cmd to show]");
			missingArg.setColor(Color.red);

			event.getChannel().sendMessage(missingArg.build()).queue();
			missingArg.clear();
			return;
		}
		
		else if(args.length == 3)
		{
			Types cmdType = KellyBot.getCmds().getCmdType(args[2]);
			
			if(cmdType == null)
			{
				EmbedBuilder cmdNoExist = new EmbedBuilder();
				cmdNoExist.setTitle("Error: No Such Command");
				cmdNoExist.setDescription("😟 I'm sorry, but the command you wanted to show the whitelist of doesn't exist. Please check \"help\" for the proper command name and try again.");
				cmdNoExist.setColor(Color.red);

				event.getChannel().sendMessage(cmdNoExist.build()).queue();
				cmdNoExist.clear();
				return;
			}
			
			else
			{
				if(KellyBot.getCmds().getWhitelistAsString(cmdType).length() != 0)
				{
					EmbedBuilder printWhitelist = new EmbedBuilder();
					printWhitelist.setTitle("Here's The Whitelist for " + KellyBot.getCmds().getName(cmdType));
					printWhitelist.setDescription(KellyBot.getCmds().getWhitelistAsString(cmdType));
					printWhitelist.setColor(Color.white);

					event.getChannel().sendMessage(printWhitelist.build()).queue();
					printWhitelist.clear();
				}

				else
				{
					EmbedBuilder noList = new EmbedBuilder();
					noList.setTitle("That command has no Whitelisted Roles!");
					noList.setDescription("🤠 It's the wild west!");
					noList.setColor(Color.white);

					event.getChannel().sendMessage(noList.build()).queue();
					noList.clear();
				}
			}
		}
		
		else
		{
			EmbedBuilder toManyArgs = new EmbedBuilder();
			toManyArgs.setTitle("Error: To many Commands");
			toManyArgs.setDescription("😟 I'm sorry, but for the sake of formating, I can only show one command at a time. Please try again.");
			toManyArgs.setColor(Color.red);

			event.getChannel().sendMessage(toManyArgs.build()).queue();
			toManyArgs.clear();
			return;
		}
	}
	
	private void addOrRemove(String[] args, GuildMessageReceivedEvent event, ArrayList<String> roles)
	{
		Types cmdType = KellyBot.getCmds().getCmdType(args[2]);
		
		if(cmdType == null)
		{
			EmbedBuilder cmdNoExist = new EmbedBuilder();
			cmdNoExist.setTitle("Error: No Such Command");
			cmdNoExist.setDescription("😟 I'm sorry, but the command you wanted to whitelist doesn't exist. Please check \"help\" for the proper command name and try again.");
			cmdNoExist.setColor(Color.red);

			event.getChannel().sendMessage(cmdNoExist.build()).queue();
			cmdNoExist.clear();
			return;
		}
		
		for(String role : roles)
		{
			if(args[1].equalsIgnoreCase("remove"))
				KellyBot.getCmds().removeFromWhitelist(cmdType, event, role);

			else
				KellyBot.getCmds().addToWhitelist(cmdType, event, role);
		}
	}

	@Override
	public String getCmdName() 
	{
		return "Whitelist";
	}

	@Override
	public String getCmd() 
	{
		return "whitelist [add / remove / show] [roles with ',' between roles]";
	}

	@Override
	public String getDesc() 
	{
		return "This command shows the whitelist of other commands, add to the whitelist, and removes a role from the whitelist.";
	}
}
