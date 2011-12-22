package tenx.gwt.portfolio.client.rpc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.OK)
public class PortfolioNotFoundException extends Exception {

}
