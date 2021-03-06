package DataAcces;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;

import Model.Product;

public class ProductDAO {
	private Connection connect;

	public ProductDAO(Connection connect) {
		this.setConnect(connect);
	}

	public Connection getConnect() {
		return connect;
	}

	public void setConnect(Connection connect) {
		this.connect = connect;
	}
	/* se gaseste produsul dupa nume */
	public Product findByName(String name) {
		Product product = null;
		String sel = "SELECT * FROM `bazadedate`.`product` WHERE `product`.`name` =" + "'" + name + "';";
		try {
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery(sel);
			while (result.next()) {
				product = new Product(result.getInt("idproduct"), result.getString("name"), result.getInt("quantity"),
						result.getFloat("price"));

			}
		} catch (SQLException e) {
			System.out.println("Error!");
		}
		return product;
	}
	/* se modifica cantitatea produsul */
	public void updateProduct1(Product product, int quantity) {

		int d = product.getId();
		int i = product.getQuantity() + quantity;
		String update = "UPDATE `bazadedate`.`product` SET `quantity`=" + i + " WHERE `idproduct` =" + d;

		try {
			Statement statement = connect.createStatement();
			statement.executeUpdate(update);

		} catch (SQLException e) {

			System.out.println("Error!");
		}

	}

	public void insertProduct(Product product) {
		ProductDAO p = new ProductDAO(connect);
		Product product1 = p.findByName(product.getName());
		if (product1==null) {
			String insert = "INSERT INTO `bazadedate`.`product`(`idproduct`,`name`,`quantity`,`price`) VALUES("
					+ product.getId() + ",'" + product.getName() + "'," + product.getQuantity() + ","
					+ product.getPrice() + ");";
			try {
				Statement statement = connect.createStatement();
				statement.executeUpdate(insert);
			} catch (SQLException e) {
				System.out.println("Error!");
			}

		} else {
			if(product1.getName().equals(product.getName())) {
				p.updateProduct1(product1, product.getQuantity());
			}
		}
	}

	public void deleteProduct(Product product) {
		String delete = "DELETE FROM `bazadedate`.`product` WHERE `product`.`idproduct`=" + product.getId();
		try {
			Statement statement = connect.createStatement();
			statement.executeUpdate(delete);
		} catch (SQLException e) {

			System.out.println("Error!");
		}
	}

	public int updateProduct(Product product, int quantity) {
		int ok = 0;
		if (product.getQuantity() != 0) {
			int d = product.getId();
			int i = product.getQuantity() - quantity;
			String update = "UPDATE `bazadedate`.`product` SET `quantity`=" + i + " WHERE `idproduct` =" + d;

			try {
				Statement statement = connect.createStatement();
				statement.executeUpdate(update);
				ok = 1;
			} catch (SQLException e) {

				System.out.println("Error!");
			}
		}
		return ok;

	}

	public int numberOfRows(Connection connect) {
		Statement statement;
		try {
			statement = connect.createStatement();
			String sql = "SELECT count(*) FROM `bazadedate`.`product`;";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public ArrayList<Product> ViewAll(Connection connect) {
		ArrayList<Product> results = new ArrayList<Product>();
		String sel = "SELECT * FROM `bazadedate`.`product` ORDER BY `product`.`idproduct`;";
		try {
			Statement state = connect.createStatement();
			ResultSet s = state.executeQuery(sel);
			while (s.next()) {
				Product c = new Product(s.getInt("idproduct"), s.getString("name"), s.getInt("quantity"),
						s.getFloat("price"));
				results.add(c);
			}
		} catch (SQLException e) {
			System.out.println("Error!");
		}
		return results;
	}

	
}
