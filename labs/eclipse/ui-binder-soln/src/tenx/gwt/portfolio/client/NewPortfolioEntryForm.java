package tenx.gwt.portfolio.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NewPortfolioEntryForm extends Composite implements HasClickHandlers  {

	private static NewPortfolioEntryFormUiBinder uiBinder = GWT
			.create(NewPortfolioEntryFormUiBinder.class);
	
	@UiField
	TextBox symbol;
	
	@UiField
	TextBox quantity;

	@UiField
	TextBox price;
	
	@UiField
	Button submit;
	
	@UiField
	Button cancel;
	
	interface NewPortfolioEntryFormUiBinder extends
			UiBinder<Widget, NewPortfolioEntryForm> {
	}

	public NewPortfolioEntryForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	@UiHandler("submit")
	public void onSubmit(ClickEvent clickEvent) {
		Window.alert("add new");
		fireEvent(clickEvent);
	}

	@UiHandler("cancel")
	public void onCancel(ClickEvent clickEvent) {
		Window.alert("cancelled");
		fireEvent(clickEvent);
	}
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	
}
