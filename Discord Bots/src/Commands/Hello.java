package Commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Hello extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event)
	{
		event.getChannel().sendTyping(); // Am Typing
		event.getChannel().sendMessage("Hey there!").queue(); // Send Message
	}

	@Override
	public String getCmd() 
	{
		return "hello";
	}

	@Override
	public String getDesc() 
	{
		return "I say Hello to my creator!";
	}

	@Override
	public String getCmdName() 
	{
		return "Hello";
	}
}
