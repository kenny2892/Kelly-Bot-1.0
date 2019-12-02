package Commands;

import java.awt.Color;

import Main.Pics;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Greeting extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event)
	{
		String name = event.getMember().getUser().getName();
		
		EmbedBuilder hello = new EmbedBuilder();
		hello.setTitle("Hola!");
		hello.setDescription("Hey " + name + "!\nHope your having a good day!");
		hello.setImage(Pics.idle);
		hello.setColor(new Color(54, 57, 63));
		
		event.getChannel().sendMessage(hello.build()).queue();
		hello.clear();
	}

	@Override
	public String getCmd() 
	{
		return "hi OR hello OR hola";
	}

	@Override
	public String getDesc() 
	{
		return "I say Hi!";
	}

	@Override
	public String getCmdName() 
	{
		return "Greeting";
	}
}
