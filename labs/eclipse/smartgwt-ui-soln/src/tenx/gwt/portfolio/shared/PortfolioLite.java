package tenx.gwt.portfolio.shared;


@SuppressWarnings("serial")
public class PortfolioLite extends BaseVO {
	private Integer id;
	private String name;
	
	PortfolioLite(){
	}
	
	public PortfolioLite(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	
}
