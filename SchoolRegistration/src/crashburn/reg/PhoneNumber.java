package crashburn.reg;

public class PhoneNumber {
	private String areaCode;

    private String exchange;
    
	private String subscriberNumber;

	public PhoneNumber() {

	}

	public PhoneNumber(String anAreaCode, String anExchange, String aSubscriberNumber) {
		this.areaCode = anAreaCode;
		this.exchange = anExchange;
		this.subscriberNumber = aSubscriberNumber;
	}

	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getSubscriberNumber() {
		return subscriberNumber;
	}
	public void setSubscriberNumber(String subscriberNumber) {
		this.subscriberNumber = subscriberNumber;
	}

	public String toString() {
		return areaCode + "-" + exchange + "-" + subscriberNumber;
	}
}
