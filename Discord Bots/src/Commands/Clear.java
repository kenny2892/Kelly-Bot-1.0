package Commands;

import java.awt.Color;
import java.util.List;
import java.util.TimerTask;

import Main.KellyBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;

public class Clear extends CommandTemplate 
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event)
	{
		if(args.length < 2) // Just Clear
		{
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Error: Ya Don GOOFED!");
			help.setDescription("❌\nThe Clear command needs to be written like this: " + KellyBot.getPrefix() + "clear [Number of Messages to Delete]");
			help.setColor(Color.red);
			
			event.getChannel().sendMessage(help.build()).queue();
			
			help.clear();
		}
		
		else
		{
			int numToDelete = Integer.parseInt(args[1]) + 1; // Because you have to take the command itself into account
			
			List<Message> messages = event.getChannel().getHistory().retrievePast(numToDelete).complete();
			try
			{
				// Delete
				event.getChannel().deleteMessages(messages).queue();
				
				done(event, args);
			}
			
			catch(IllegalArgumentException e)
			{
				if(e.toString().contains("limit")) // To Many
				{
					EmbedBuilder toMany = new EmbedBuilder();
					toMany.setTitle("Error: To Many");
					toMany.setDescription("😡 \nCouldn't delete all Messages! There were so many that I couldn't keep up.\nCan you try again but not go over 100");
					toMany.setImage("https://media1.tenor.com/images/bc7a8e060496a6b889c9100c20dce14e/tenor.gif?itemid=7919962");
					toMany.setColor(Color.red);
					
					event.getChannel().sendMessage(toMany.build()).queue();
					toMany.clear();
				}
				
				else if(e.toString().contains("at least 2")) // Only 1
				{
					List<Message> oneMessage = event.getChannel().getHistory().retrievePast(1).complete();
					event.getChannel().deleteMessageById(oneMessage.get(0).getId()).queue();
					done(event, args);
				}
				
				else // To Old
				{
					EmbedBuilder toOld = new EmbedBuilder();
					toOld.setTitle("Error: To Old");
					toOld.setDescription("👴 \nCouldn't delete all Messages, some were to old and Discord won't let me hurt them.");
					toOld.setColor(Color.red);
					
					event.getChannel().sendMessage(toOld.build()).queue();
					toOld.clear();
				}
			}
			
			catch(InsufficientPermissionException e)
			{
				EmbedBuilder noPermission = new EmbedBuilder();
				noPermission.setTitle("Error: Permissions");
				noPermission.setDescription("😥\nI dont't have the right permissions on this channel to do that.\nCan you please set the \"Mangage Messages\" Permission to true for my Role?");
				noPermission.setColor(Color.red);
				
				event.getChannel().sendMessage(noPermission.build()).queue();
				noPermission.clear();
			}
		}
	}
	
	private void done(GuildMessageReceivedEvent event, String[] args)
	{		
		// Message
		EmbedBuilder done = new EmbedBuilder();
		done.setTitle("Success!");
		
		if(Integer.parseInt(args[1]) > 1)
			done.setDescription("✔ " + args[1] + " messages have been deleted!");
		
		else
			done.setDescription("✔ " + args[1] + " message has been deleted!");
			
		done.setColor(Color.green);
		
		event.getChannel().sendMessage(done.build()).queue();
		done.clear();
		
		// Delete Done Message
		new java.util.Timer().schedule
		(
			new TimerTask() 
			{
				@Override
				public void run()
				{
					List<Message> toDelete = event.getChannel().getHistory().retrievePast(1).complete();
					
					if(toDelete.get(0).getMember().getUser().equals(event.getJDA().getSelfUser())) // Message came from the Bot
					{
						event.getChannel().deleteMessageById(toDelete.get(0).getId()).queue();
					}
				}
			},

			5 * 1000
		);
	}

	@Override
	public String getCmd() 
	{
		return "clear [number]";
	}

	@Override
	public String getDesc() 
	{
		return "Clear messages from the Channel";
	}

	@Override
	public String getCmdName() 
	{
		return "Clear";
	}
}
