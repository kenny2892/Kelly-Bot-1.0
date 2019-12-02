package Commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Main.KellyBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Help extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		if(args.length == 1) // Just Help
		{
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("❔ Help");
			help.setDescription("Here are all the commands I know:\nHover over the command to see what it does!");
			help.setThumbnail("https://i.imgur.com/8MqVA0K.jpg");
			help.setColor(Color.white);
			
			ArrayList<CommandTemplate> listOfCmds = KellyBot.getCmds().cmdClones();
			for(CommandTemplate cmd : listOfCmds)
			{
				String start = "(https://www.youtube.com/watch?v=dQw4w9WgXcQ \"";
				String end = "\")";
				
				if(!cmd.hasWhitelist())
					help.addField(cmd.getCmdName() + ": ", "[" + KellyBot.getPrefix() + cmd.getCmd() + "]" + start + cmd.getDesc() + end, true);
				
				else
				{
					List<Role> roles = event.getMember().getRoles();
					
					boolean whitelistCheck = false;
					for(Role role : roles)
					{
						if(cmd.inWhiteList(role.getName()))
							whitelistCheck = true;
					}
					
					if(whitelistCheck)
						help.addField(cmd.getCmdName() + ": ", "[" + KellyBot.getPrefix() + cmd.getCmd() + "]" + start + cmd.getDesc() + end, true);
				}
			}	
			
			event.getChannel().sendMessage(help.build()).queue();
			help.clear();
		}
		
		else if(args.length == 2) // Help Specific Cmd
		{
			if(args[1].equalsIgnoreCase("hello") || args[1].equalsIgnoreCase("hola"))
				args[1] = "hi";
			
			int index = -1;
			
			ArrayList<ArrayList<String>> info = KellyBot.getCmdInfo();
			for(int i = 0; i < info.size(); i++)
			{
				if(info.get(i).get(0).equalsIgnoreCase(args[1]))
				{
					index = i;
					break;
				}
			}
			
			if(index > -1)
			{
				EmbedBuilder help = new EmbedBuilder();
				help.setTitle("❔ " + info.get(index).get(0) + ": ");
				help.addField(KellyBot.getPrefix() + info.get(index).get(1), info.get(index).get(2), true);
				help.setThumbnail("https://i.imgur.com/8MqVA0K.jpg");
				help.setColor(Color.white);

				event.getChannel().sendMessage(help.build()).queue();
				help.clear();
			}
			
			else // Improper cmd
			{
				EmbedBuilder errorCmd = new EmbedBuilder();
				errorCmd.setTitle("Error: Not a Command");
				errorCmd.setDescription("🤔 That's not even a command.\nTry again with something like this: \"" + KellyBot.getPrefix() + "help [Command that actually exists]\"");
				errorCmd.setColor(Color.red);
				
				event.getChannel().sendMessage(errorCmd.build()).queue();
				errorCmd.clear();
			}
		}
		
		else // To many arguments
		{
			EmbedBuilder errorToMany = new EmbedBuilder();
			errorToMany.setTitle("Error: To Much Stuff, Not Enough Space");
			errorToMany.setDescription("😓 I can only do so much stuff at once!\nTry again, but with only one command being searched!");
			errorToMany.setColor(Color.red);
			errorToMany.setImage("https://i.kym-cdn.com/entries/icons/original/000/023/623/Too_Much_Stuff_Not_Enough_Space.jpg");
			
			event.getChannel().sendMessage(errorToMany.build()).queue();
			errorToMany.clear();
		}
	}

	@Override
	public String getCmd() 
	{
		return "help [optional command]";
	}

	@Override
	public String getDesc() 
	{
		return "Look up commands and figure out what they do.";
	}

	@Override
	public String getCmdName() 
	{
		return "Help";
	}
	
}
