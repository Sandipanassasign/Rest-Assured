package end2endflow;

public class CreateOrderResponse {

	
	String message;
	String[] orders;
	String[] productOrderId;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getOrders() {
		return orders;
	}
	public void setOrders(String[] orders) {
		this.orders = orders;
	}
	public String[] getProductOrderId() {
		return productOrderId;
	}
	public void setProductOrderId(String[] productOrderId) {
		this.productOrderId = productOrderId;
	}
	
	
	
}
