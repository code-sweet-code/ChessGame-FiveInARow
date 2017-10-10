package graphic;

import java.util.HashMap;
import java.util.Map;

public class ControllerCollector {
	
	private Map<String, Controller> controllers = null;
	
	public ControllerCollector(){
		controllers = new HashMap<String, Controller>();
	}
	
	public void addController(String name, Controller controller){
		controllers.put(name, controller);
	}

	public Controller getController(String name){
		return controllers.get(name);
	}
	
}
