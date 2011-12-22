package tenx.gwt.helloworld.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class HelloWorld implements EntryPoint {

	@Override
	public void onModuleLoad() {
		
		Label helloWorld = new Label("Hello World");
		helloWorld.addStyleName("hello-world");
		RootPanel.get("content")
			.add(helloWorld);

	}

}
