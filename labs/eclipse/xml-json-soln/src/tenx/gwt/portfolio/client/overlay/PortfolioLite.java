package tenx.gwt.portfolio.client.overlay;

import com.google.gwt.core.client.JavaScriptObject;

public class PortfolioLite extends JavaScriptObject {

	protected PortfolioLite(){
	}
	
	public final native Integer getId() /*-{
		return this.id;
	}-*/;
	
	public final native String getName() /*/*-{
		return this.name;
	}-*/;


}
