package DataAcces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Order;
import Model.Product;


public class OrderDAO {
	private Connection connect;

	public OrderDAO(Connection conect) {
		this.connect = conect;
	}
	/* se introduce comanda is baza de date */
	public void createOrder( Order order) {
		
		try {
			Statement statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO `bazadedate`.`order`(`idorder`,`clientName`,`productName`,`quantity`) VALUES(" + order.getId() + ",'"+order.getClientName()+"',"+"'" +order.getProductName() + "',"
					+ order.getQuantity() + ");");
		} catch (SQLException e) {
			System.out.println("Error!");
		}

	}
	
	public int numberOfRows( Connection connect) {
		Statement statement;
		try {
			statement = connect.createStatement();
			String sql = "SELECT count(*) FROM `bazadedate`.`order`;";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	//la fel ca le client
	public ArrayList<Order> ViewAll(Connection connect){
		ArrayList<Order> results = new ArrayList<Order>();
		String sel="SELECT * FROM `bazadedate`.`order` ORDER BY `order`.`idorder`;";
		try {
			Statement state = connect.createStatement();
			ResultSet s = state.executeQuery(sel);
			while(s.next())
			{
				Order c = new Order(s.getInt("idorder"),s.getString("clientName"), s.getString("productName"),s.getInt("quantity"));
				results.add(c);
			}
		} catch (SQLException e) {
			System.out.println("Error!");
		}
		return results;
	}
	

	
}
