package com.tenxperts.trainings.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class HelloWorld implements EntryPoint {
  public void onModuleLoad() {
	  Label label = new Label("HelloWorld");
	  label.setStyleName("title");
	  RootPanel.get().add(label);
  }
}
