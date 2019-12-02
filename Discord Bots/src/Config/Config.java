package Config;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

public class Config extends JSONObject
{
	private static Config instance;
	
	public Config(File file) throws IOException
	{
		super(new ConfigLoader().load(file));
		
		instance = this;
	}
	
	public static Config getInstance()
	{
		return instance;
	}
}
