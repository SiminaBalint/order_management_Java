package Presentation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.itextpdf.text.DocumentException;

import DataAcces.ClientDAO;
import DataAcces.ConnectionFactory;
import DataAcces.OrderDAO;
import DataAcces.ProductDAO;
import Model.Client;
import Model.Product;
import Model.Order;

public class ParseFile {

	public ParseFile() {

	}

	/*
	 * aici se desparte fisierul pentru a putea face apel de functile din DAO si
	 * clasa Report
	 */
	public void readInstructions(String path) throws SQLException, FileNotFoundException, DocumentException {
		Connection connect = ConnectionFactory.getConnection();
		ClientDAO client = new ClientDAO(connect);
		ProductDAO product = new ProductDAO(connect);
		OrderDAO order = new OrderDAO(connect);
		Reports rep = new Reports();
		int idC = 0;
		int idP = 0;
		int idO = 0;
		int idC1 = 0;
		int idP1 = 0;
		int idO1 = 0;
		int cod = 0;

		try {
			Scanner myReader = new Scanner(new File(path));

			while (myReader.hasNext()) {
				String readL = myReader.nextLine();
				int poz = readL.indexOf(" ");
				if (poz != 0) {
					String instr = readL.substring(0, poz);
					if (instr.contains("Insert")) {
						String item = readL.substring(poz + 1, readL.indexOf(":"));
						if (item.contains("client")) {
							String elem1 = readL.substring(readL.indexOf(":") + 2, readL.indexOf(","));
							String elem2 = readL.substring(readL.indexOf(",") + 2, readL.length());
							idC1++;
							Client c = new Client(idC1, elem1, elem2);
							client.insertClient(c);
						} else if (item.contains("product")) {
							String elem1 = readL.substring(readL.indexOf(":") + 2, readL.indexOf(","));
							String elem2 = readL.substring(readL.indexOf(",") + 2, readL.lastIndexOf(","));
							String elem3 = readL.substring(readL.lastIndexOf(",") + 2, readL.length());
							idP1++;
							Product p = new Product(idP1, elem1, Integer.parseInt(elem2), Float.parseFloat(elem3));
							product.insertProduct(p);
						}
					} else if (instr.contains("Delete")) {
						String item = readL.substring(poz + 1, readL.indexOf(":"));
						if (item.contains("client")) {
							String elem1 = readL.substring(readL.indexOf(":") + 2, readL.indexOf(","));
							Client c = client.findClient(elem1);
							client.deleteClient(c);
						} else if (item.contains("Product")) {
							String elem1 = readL.substring(readL.indexOf(":") + 2, readL.length());
							Product p = product.findByName(elem1);
							product.deleteProduct(p);
						}
					} else if (instr.contains("Report")) {
						String item = readL.substring(readL.indexOf(" ") + 1, readL.length());
						if (item.contains("client")) {
							idC++;
							rep.reportClientsPdf(idC);
						} else if (item.contains("product")) {
							idP++;
							rep.reportProductsPdf(idP);
						} else if (item.contains("order")) {
							idO++;
							rep.reportOrdersPdf(idO);
						}
					} else if (instr.contains("Order")) {

						String elem1 = readL.substring(readL.indexOf(":") + 2, readL.indexOf(","));
						String elem2 = readL.substring(readL.indexOf(",") + 2, readL.lastIndexOf(","));
						String elem3 = readL.substring(readL.lastIndexOf(",") + 2, readL.length());
						idO1++;
						Order o = new Order(idO1, elem1, elem2, Integer.parseInt(elem3));
						order.createOrder(o);
						Product p = product.findByName(elem2);
						if (p.getQuantity() >= Integer.parseInt(elem3)) {
							cod++;
							rep.makeBill(readL.substring(readL.indexOf(":") + 2, readL.indexOf(",")), p,
									readL.substring(readL.lastIndexOf(",") + 2, readL.length()), cod);
							product.updateProduct(p, Integer.parseInt(elem3));
						} else {
							cod = -1;
						}
					} else {
						System.out.println("ERROR!");

					}
				}
			}

		}

		// }
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
