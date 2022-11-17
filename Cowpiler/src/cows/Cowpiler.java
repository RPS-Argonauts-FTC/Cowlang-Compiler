package cows;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Action {
	
	Runnable fn = null;
	
	Action (Runnable fn) {
		this.fn = fn;
	}
	
	public void execute() {
		if (fn == null)
		{
			return;
		}
		
		fn.run();
	}
	
	public String toString() {
		return "";
	}
};

public class Cowpiler {
	
	public String configs = "";
	
	public Cowpiler()
	{
	}
	
	public Cowpiler(String configs)
	{
		this.configs = configs;
	}
	
	private Action handleCommand(String cmd) {
		String fnName = cmd.substring(0, cmd.indexOf('('));
		List<String> params = Arrays.asList(cmd.substring(cmd.indexOf('(') + 1, cmd.indexOf(')')).split(", "));
		
		Runnable runner = null;
		
		switch (fnName)
		{
			case "Drive.Forward":
				runner = new Runnable() {
					public void run() {
						System.out.println("Forward " + params);
					}
				};
				break;
			case "Drive.Backward":
				runner = new Runnable() {
					public void run() {
						System.out.println("Backward " + params);
					}
				};
				break;
			case "Drive.Left":
				runner = new Runnable() {
					public void run() {
						System.out.println("Left " + params);
					}
				};
				break;
			case "Drive.Right":
				runner = new Runnable() {
					public void run() {
						System.out.println("Right " + params);
					}
				};
				break;
			
			//turning (by degrees)
			case "Drive.TurnLeft":
				runner = new Runnable() {
					public void run() {
						System.out.println("TurnLeft " + params);
					}
				};
				break;
			case "Drive.TurnRight":
				runner = new Runnable() {
					public void run() {
						System.out.println("TurnRight " + params);
					}
				};
				break;
			
			//Park
			case "Drive.SunnyPark":
				runner = new Runnable() {
					public void run() {
						System.out.println("Park from current pos on field " + params);
					}
				};
				break;
			
			//delay (by seconds)
			case "Delay":
				runner = new Runnable() {
					public void run() {
						System.out.println("Delay " + params);
					}
				};
				break;
			
			//Claw
			case "Claw.Open":
				runner = new Runnable() {
					public void run() {
						System.out.println("Open " + params);
					}
				};
				break;
			case "Claw.Close":
				runner = new Runnable() {
					public void run() {
						System.out.println("Close " + params);
					}
				};
				break;
			
			//Viper
			case "Viper.GoTo":
				runner = new Runnable() {
					public void run() {
						System.out.println("GoTo " + params);
					}
				};
				break;
			default:
				System.out.println("Error: " + fnName + "is not defined. Removed instead.");
		}
		
		return new Action(runner);
	}
	
	private String process(String cowlang)
	{
		cowlang = cowlang.replaceAll("\\/\\*.*?\\*\\/", "");
		cowlang = cowlang.replaceAll(" ", "");
		return cowlang;
	}
	
	private String transpile(String cowlang)
	{
		if (configs.contains("transpile.flipLeftRight"))
		{
			cowlang = cowlang.replace("Left", "Temp");
			cowlang = cowlang.replace("Right", "Left");
			cowlang = cowlang.replace("Temp", "Right");
		}
		
		return cowlang;
	}
	
	public ArrayList<Action> cowpile(String cowlang)
	{
		cowlang = process(cowlang);
		cowlang = transpile(cowlang);
		
		System.out.println(cowlang);
		
		String[] cmds = cowlang.split(";");
		
		ArrayList<Action> commandsInActions = new ArrayList<Action>();
		
		for (String cmd : cmds)
		{
			commandsInActions.add(handleCommand(cmd));
		}
		
		return commandsInActions;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ReadFile r = new ReadFile("Moo.cow");
		try {
			String code = (String.join("", r.getList()));
			Cowpiler c = new Cowpiler();
			ArrayList<Action> cows = c.cowpile(code);
		
			for (Action cow : cows)
			{
				cow.execute();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
