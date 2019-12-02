package Commands;

import java.awt.Color;
import java.util.ArrayList;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public abstract class CommandTemplate extends ListenerAdapter implements Cloneable
{
	private ArrayList<String> whitelist = new ArrayList<String>();
	
	public final void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		hook(args, event);
	}
	
	public abstract void hook(String[] args, GuildMessageReceivedEvent event);
	
	public abstract String getCmdName();
	
	public abstract String getCmd();
	
	public abstract String getDesc();
	
	public final void setWhite(ArrayList<String> whitelist)
	{
		if(whitelist != null)
			this.whitelist = whitelist;
	}
	
	public final void addWhite(String roleName, GuildMessageReceivedEvent event)
	{
		if(roleName != null && roleName.length() !=  0)
			whitelist.add(roleName.toLowerCase());

		EmbedBuilder addToWhiteList = new EmbedBuilder();
		addToWhiteList.setTitle("Added to Whitelist");
		addToWhiteList.setDescription("✔ The Role \"" + roleName + "\" has been added to the white list for the " + getCmdName() + " commamnd!");
		addToWhiteList.setColor(Color.white);
		
		event.getChannel().sendMessage(addToWhiteList.build()).queue();
		addToWhiteList.clear();
	}
	
	public final void removeWhite(String roleName, GuildMessageReceivedEvent event)
	{
		if(roleName != null && roleName.length() !=  0 && this.inWhiteList(roleName))
		{
			whitelist.remove(roleName.toLowerCase());

			EmbedBuilder removeFromWhitelist = new EmbedBuilder();
			removeFromWhitelist.setTitle("Removed from Whitelist");
			removeFromWhitelist.setDescription("✔ The Role \"" + roleName + "\" has been removed from the whitelist for the " + getCmdName() + " commamnd!");
			removeFromWhitelist.setColor(Color.white);
			
			event.getChannel().sendMessage(removeFromWhitelist.build()).queue();
			removeFromWhitelist.clear();
		}
	}
	
	public final boolean inWhiteList(String roleName)
	{
		return whitelist.contains(roleName.toLowerCase());
	}
	
	public final boolean hasWhitelist()
	{
		if(whitelist.size() == 0)
			return false;
		
		return true;
	}
	
	public void notInWhitelist(GuildMessageReceivedEvent event)
	{
		EmbedBuilder notListed = new EmbedBuilder();
		notListed.setTitle("Error: Don't have permission");
		notListed.setDescription("😕 Sorry, you don't have permission to do that.");
		notListed.setColor(Color.red);

		event.getChannel().sendMessage(notListed.build()).queue();
		notListed.clear();
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	
	public final ArrayList<String> getWhiteList()
	{
		return whitelist;
	}
	
	public final String getWhitelistAsStr()
	{
		if(whitelist.size() == 0)
			return "";
		
		String temp = "";
		
		for(int i = 0; i < whitelist.size() - 1; i++)
		{
			temp += whitelist.get(i) + ", ";
		}
		
		temp += whitelist.get(whitelist.size() - 1);
		
		return temp;
	}
}
