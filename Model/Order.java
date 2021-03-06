package Model;

public class Order {
	private int id;
	private String clientName;
	private String productName;
	private int quantity;
	
	public Order(int id,String clientName,String productName, int quantity) {
		this.id=id;
		this.clientName=clientName;
		this.productName=productName;
		this.quantity=quantity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
