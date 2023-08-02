package end2endflow;

public class ProductRequestBase {
	
	
	String _id;
	ProductRequestDetails product;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public ProductRequestDetails getProduct() {
		return product;
	}
	public void setProduct(ProductRequestDetails product) {
		this.product = product;
	}

}
