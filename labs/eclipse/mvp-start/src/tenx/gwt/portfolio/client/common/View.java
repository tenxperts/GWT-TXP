package tenx.gwt.portfolio.client.common;

import com.google.gwt.user.client.ui.IsWidget;

public interface View<T> extends IsWidget {
	public void setPresenter(T presenter);

}
