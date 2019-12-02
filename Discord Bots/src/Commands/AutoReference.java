package Commands;

import java.util.ArrayList;

import Commands.Commands.Types;
import Main.KellyBot;
import Main.Reference;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class AutoReference extends CommandTemplate
{
	private ArrayList<Reference> references;
	private boolean foundMatch = false;
	
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		if(event.getMember().getUser().equals(event.getJDA().getSelfUser()))
			return;
		
		else if(args[0].contains(KellyBot.getPrefix()))
			return;
		
		String msg = "";
		
		for(String arg : args)
		{
			msg += arg + " ";
		}
		
		try
		{
			CommandTemplate refCmd = KellyBot.getCmds().getCmdClone(Types.FindReference);
			references = ((FindReference) refCmd).getReferences();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		
		for(Reference ref : references)
		{
			if(msg.toLowerCase().contains(ref.getTitle().toLowerCase())) // Message Contained a Reference
			{
				printRef(event, ref);
				break;
			}
			
			else
			{
				for(String tag : ref.getTags())
				{
					if(msg.toLowerCase().contains(tag.toLowerCase()))
					{
						printRef(event, ref);
						break;
					}
				}
				
				if(foundMatch)
					break;
			}
		}
		System.out.println("finished ref check");
		foundMatch = false;
	}

	@Override
	public String getCmdName() 
	{
		return null;
	}

	@Override
	public String getCmd() 
	{
		return null;
	}

	@Override
	public String getDesc() 
	{
		return null;
	}
	
	private void printRef(GuildMessageReceivedEvent event, Reference ref)
	{
		event.getChannel().sendTyping();
		event.getChannel().sendMessage("__" + ref.getTitle() + ":__\n" + ref.getLink()).queue();
		foundMatch = true;
	}
}
