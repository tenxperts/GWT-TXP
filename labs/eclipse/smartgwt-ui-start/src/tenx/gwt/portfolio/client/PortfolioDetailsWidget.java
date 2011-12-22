package tenx.gwt.portfolio.client;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import tenx.gwt.portfolio.client.rpc.PortfolioService;
import tenx.gwt.portfolio.client.rpc.PortfolioServiceAsync;
import tenx.gwt.portfolio.shared.Portfolio;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PortfolioDetailsWidget extends Composite implements ClickHandler {
	
	private static final Logger LOGGER = Logger.getLogger(PortfolioDetailsWidget.class.getName());
	
	PortfolioServiceAsync portofolioService = GWT.create(PortfolioService.class);
	
	PortfolioConstants constants = GWT.create(PortfolioConstants.class);
	PortfolioMessages messages = GWT.create(PortfolioMessages.class);
	NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT);
	
	private Button refreshButton;
	private Button addNewButton;
	private Button deleteButton;
	private Button nextPortfolio;
	private Button prevPortfolio;
	
	private Label statusBar;
	
	private int portFolioId  = 1;

	public PortfolioDetailsWidget() {
		super();
		HorizontalPanel buttons = new HorizontalPanel();
		
		buttons.setSpacing(10);
		
		refreshButton = new Button(constants.buttonRefresh(),this);
		buttons.add(refreshButton);
		
		addNewButton = new Button(constants.buttonAddNew(),this);
		buttons.add(addNewButton);
		
		deleteButton = new Button(constants.buttonDelete(),this);
		deleteButton.setEnabled(false);
		buttons.add(deleteButton);

		nextPortfolio = new Button("Next",this);
		buttons.add(nextPortfolio);
		
		prevPortfolio = new Button("Prev",this);
		buttons.add(prevPortfolio);
		
		VerticalPanel container = new VerticalPanel();
		container.add(buttons);
		
		statusBar = new Label();
		
		updateStatusBar(new Date());
		container.add(statusBar);
		
		container.setWidth("100%");


		initWidget(container);
	}
	
	
	Timer autoRefreshTimer;

	@Override
	protected void onLoad() {
		super.onLoad();
		LOGGER.info("Setting up auto refresh timer");
		autoRefreshTimer = new Timer() {
			@Override
			public void run() {
				LOGGER.info("Refresh timer triggered");
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						refresh();
					}
				});
				LOGGER.info("Timer execution complete");
			}
		};
		//autoRefreshTimer.scheduleRepeating(30000);
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		LOGGER.info("Cancel auto refresh timer");
		autoRefreshTimer.cancel();
	}



	private void updateStatusBar(Date date) {
		statusBar.setText(messages.statusMessage( dateFormat.format(date)));
	}

	private void fetchAndPopulateData() {
		LOGGER.info("Make server call to get portfolio for id " + portFolioId);
		statusBar.setText("Loading...");
		portofolioService.getPortfolioDetail(portFolioId, new AsyncCallback<Portfolio>() {
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.SEVERE,"getPortfolioDetail failed",caught);
				Window.alert("Error fetching portfolio details : " + caught.getMessage());
				statusBar.setText("Failed to load portfolio");
			}

			@Override
			public void onSuccess(Portfolio result) {
				LOGGER.info("Fetch portfolio details successfully");
				updateStatusBar(result.getAsOnDate());
			}
		});
	}


	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == refreshButton) {
			//Window.alert("TODO - implement refresh");
			refresh();
			
		} else if (event.getSource() == addNewButton) {
		 	final DialogBox newEntryDialog =  new DialogBox();
		 	newEntryDialog.setText("Add new portfolio entry");
		 	NewPortfolioEntryForm newPortfolioEntryDialog = new NewPortfolioEntryForm();
		 	newPortfolioEntryDialog.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					newEntryDialog.hide();					
				}
			});
			newEntryDialog.setWidget(newPortfolioEntryDialog);
		 	newEntryDialog.center();
		} else if (event.getSource() == deleteButton) {
			Window.alert("TODO - implement delete");
		} else if (event.getSource() == nextPortfolio) {
			portFolioId++;
			refresh();
		} else if (event.getSource() == prevPortfolio) {
			portFolioId--;
			refresh();
		}
		
	}

	private void refresh() {
		LOGGER.info("Clearing list for refresh");
	}
}
