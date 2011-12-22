package tenx.gwt.portfolio.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("serial")
public class Portfolio extends PortfolioLite {
	private List<PortfolioEntry> entries = new ArrayList<PortfolioEntry>();
	private Date asOnDate = new Date();
	
	Portfolio(){
	}
	
	public Portfolio(Integer id,String name) {
		super(id,name);
	}

	public Portfolio addEntry(PortfolioEntry entry) {
		entries.add(entry);
		return this;
	}
	
	public List<PortfolioEntry> getEntries() {
		return entries;
	}

	public void removeEntry(Integer entryId) {
		for(Iterator<PortfolioEntry> iterator = entries.iterator();iterator.hasNext();) {
			PortfolioEntry entry = iterator.next();
			if (entry.getId().equals(entryId)) {
				iterator.remove();
			} else {
				iterator.next();
			}
		}
	}

	public Date getAsOnDate() {
		return asOnDate;
	}

	public Portfolio setAsOnDate(Date asOnDate) {
		this.asOnDate = asOnDate;
		return this;
	}
}
