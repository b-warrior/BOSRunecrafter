package methods;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;
import org.powerbot.script.rt4.Widget;

public class StoreMethods extends Methods{

	public StoreMethods(ClientContext clientContext) {
		super(clientContext);
	}
	
	public boolean isShopOpen(){
		Widget shopWidget = clientContext.widgets.select().id(300).poll();
		Component closeButton = shopWidget.component(91);
		
		return closeButton.visible();
	}

}
