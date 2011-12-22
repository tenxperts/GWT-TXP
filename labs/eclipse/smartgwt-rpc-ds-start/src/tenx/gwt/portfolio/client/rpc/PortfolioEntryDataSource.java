package tenx.gwt.portfolio.client.rpc;

import tenx.gwt.portfolio.shared.PortfolioEntry;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGridRecord;

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

	private PortfolioEntry convertFromRecord(ListGridRecord updatedRecord) {
		return new PortfolioEntry(updatedRecord.getAttributeAsInt(GRID_FIELD_ID),
				updatedRecord.getAttribute(GRID_FIELD_SYMBOL),
				updatedRecord.getAttributeAsInt(GRID_FIELD_QUANTITY),
				updatedRecord.getAttributeAsFloat(GRID_FIELD_PURCHASE_PRICE),
				updatedRecord.getAttributeAsFloat(GRID_FIELD_MARKET_PRICE),
				updatedRecord.getAttributeAsFloat(GRID_FIELD_PROFIT_LOSS));
	}
	
	public static   ListGridRecord convertToRecord(PortfolioEntry portfolioEntry) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(GRID_FIELD_ID, portfolioEntry.getId());
		record.setAttribute(GRID_FIELD_SYMBOL,portfolioEntry.getSymbol());
		record.setAttribute(GRID_FIELD_QUANTITY,portfolioEntry.getQuantity());
		record.setAttribute(GRID_FIELD_PURCHASE_PRICE, portfolioEntry.getPurchasePrice());
		record.setAttribute(GRID_FIELD_MARKET_PRICE,portfolioEntry.getMarketPrice());
		record.setAttribute(GRID_FIELD_PROFIT_LOSS,portfolioEntry.getProfitOrLoss());
		return record;
	}
	
	private ListGridRecord getEditedRecord (DSRequest request) {
        // Retrieving values before edit
        JavaScriptObject oldValues = request.getAttributeAsJavaScriptObject ("oldValues");
        // Creating new record for combining old values with changes
        ListGridRecord newRecord = new ListGridRecord ();
        // Copying properties from old record
        JSOHelper.apply (oldValues, newRecord.getJsObj ());
        // Retrieving changed values
        JavaScriptObject data = request.getData ();
        // Apply changes
        JSOHelper.apply (data, newRecord.getJsObj ());
        return newRecord;
    }


}
