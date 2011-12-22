package tenx.gwt.portfolio.client;

import com.google.gwt.i18n.client.NumberFormat;

public class JavaScriptHelper {
	// A Java method using JSNI
	static native void sayHello(String name) /*-{
		$wnd.sayHello(name); // $wnd is a JSNI synonym for 'window'
	}-*/;
	
	// Expose the following method into JavaScript.
	  private static String formatAsCurrency(double x) {
	    return NumberFormat.getCurrencyFormat().format(x);
	  }

	  // Set up the JS-callable signature as a global JS function.
	  static native void publish() /*-{
	    $wnd.formatAsCurrency = 
	      @tenx.gwt.portfolio.client.JavaScriptHelper::formatAsCurrency(D);
	  }-*/;
}
