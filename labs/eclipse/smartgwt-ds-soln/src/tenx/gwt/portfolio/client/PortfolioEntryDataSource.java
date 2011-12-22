package tenx.gwt.portfolio.client;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class PortfolioEntryDataSource extends DataSource {
	
	public static PortfolioEntryDataSource getInstance() {
		return new PortfolioEntryDataSource();
	}

	static public final String GRID_FIELD_ID = "ID";
	static public final String GRID_FIELD_PROFIT_LOSS = "profitLoss";
	static public final String GRID_FIELD_MARKET_PRICE = "marketPrice";
	static public final String GRID_FIELD_PURCHASE_PRICE = "purchasePrice";
	static public final String GRID_FIELD_QUANTITY = "quantity";
	static public final String GRID_FIELD_SYMBOL = "symbol";
	
	public PortfolioEntryDataSource() {
		
		DataSourceField idField = new DataSourceIntegerField(GRID_FIELD_ID);
		idField.setHidden(true);
		idField.setPrimaryKey(true);
		
		DataSourceFloatField marketPriceField = new DataSourceFloatField(GRID_FIELD_MARKET_PRICE, "Market Price");
		marketPriceField.setCanEdit(false);
		
		DataSourceFloatField profitLossField = new DataSourceFloatField(GRID_FIELD_PROFIT_LOSS, "Profit / Loss");
		profitLossField.setCanEdit(false);
		
		setFields(idField,
				new DataSourceTextField(GRID_FIELD_SYMBOL,"Symbol"),
				new DataSourceIntegerField(GRID_FIELD_QUANTITY, "Quantity"),
				new DataSourceFloatField(GRID_FIELD_PURCHASE_PRICE, "Purchase Price"),
				marketPriceField,
				profitLossField);
	}

}
