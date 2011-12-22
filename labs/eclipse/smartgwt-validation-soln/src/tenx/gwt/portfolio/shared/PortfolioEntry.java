package tenx.gwt.portfolio.shared;

@SuppressWarnings("serial")
public class PortfolioEntry extends BaseVO {
	private Integer id;
	private String symbol;
	private Integer quantity;
	private float purchasePrice;
	private float marketPrice;
	private float profitOrLoss;

	PortfolioEntry(){
	}
	
	public PortfolioEntry( String symbol, Integer quantity,
			float purchasePrice) {
		this.symbol = symbol;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.marketPrice = purchasePrice;
		this.profitOrLoss = 0;
	}
	public PortfolioEntry(Integer id, String symbol, Integer quantity,
			float purchasePrice, float marketPrice, float profitOrLoss) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.marketPrice = marketPrice;
		this.profitOrLoss = profitOrLoss;
	}

	public Integer getId() {
		return id;
	}

	public String getSymbol() {
		return symbol;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public float getPurchasePrice() {
		return purchasePrice;
	}

	public float getMarketPrice() {
		return marketPrice;
	}

	public float getProfitOrLoss() {
		return profitOrLoss;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortfolioEntry other = (PortfolioEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
