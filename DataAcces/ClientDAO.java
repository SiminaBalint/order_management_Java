package DataAcces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Client;

public class ClientDAO {
	private Connection connect;

	public ClientDAO() {
	}

	public ClientDAO(Connection connect) {
		this.connect = connect;
	}

	/*
	 * aici se insereaza clientul in tabelul corespunzator folosind tipul de data
	 * Satement, iar in cazul in care querry-ul nu este corecet sau insert-ul nu se
	 * poate realiza se arunca o exceptie
	 */
	public void insertClient(Client client) {
		String insert = "INSERT INTO `bazadedate`.`client`(`idclient`,`name`,`address`) VALUES(" + client.getId() + ",'"
				+ client.getName() + "','" + client.getAddress() + "');";
		try {
			Statement statement = connect.createStatement();
			statement.executeUpdate(insert);
		} catch (SQLException e) {
			System.out.println("Error!");
		}
	}

	/*
	 * aici se sterge clientul in tabelul corespunzator folosind tipul de data
	 * Satement, iar in cazul in care querry-ul nu este corecet sau delete-ul nu se
	 * poate realiza se arunca o exceptie
	 */
	public void deleteClient(Client client) {
		String delete = "DELETE FROM `bazadedate`.`client` WHERE `client`.`idclient`=" + client.getId();
		try {
			Statement statement = connect.createStatement();
			statement.executeUpdate(delete);
		} catch (SQLException e) {

			System.out.println("Error!");
		}
	}

	/* se gaseste clientul dupa nume */
	public Client findClient(String name) {

		Client client = null;
		String sel = "SELECT * FROM `bazadedate`.`client` WHERE `client`.`name`=" + "'" + name + "';";
		try {
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery(sel);
			while (result.next()) {
				client = new Client(result.getInt("idclient"), result.getString("name"), result.getString("address"));

			}
		} catch (SQLException e) {
			System.out.println("Error!");
		}
		return client;
	}
	/* se gaseste numarul de linii dintr-un tabel*/
	public int numberOfRows(Connection connect) {
		Statement statement;
		try {
			statement = connect.createStatement();
			String sql = "SELECT count(*) FROM `bazadedate`.`client`;";
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
	/* se gaseste lista toatala de clienti */
	public ArrayList<Client> ViewAll(Connection connect) {
		ArrayList<Client> results = new ArrayList<Client>();
		String sel = "SELECT * FROM `bazadedate`.`client` ORDER BY `client`.`idclient`;";
		try {
			Statement state = connect.createStatement();
			ResultSet s = state.executeQuery(sel);
			while (s.next()) {
				Client c = new Client(s.getInt("idClient"), s.getString("Name"), s.getString("Address"));
				results.add(c);
			}
		} catch (SQLException e) {
			System.out.println("Error!");
		}
		return results;
	}

}
