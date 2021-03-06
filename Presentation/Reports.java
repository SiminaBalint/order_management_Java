package Presentation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import Model.Client;
import Model.Product;
import Model.Order;

import java.sql.Connection;
import java.sql.SQLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;

import DataAcces.ClientDAO;
import DataAcces.ConnectionFactory;
import DataAcces.OrderDAO;
import DataAcces.ProductDAO;

public class Reports {
	public Reports() {
	}

	/*
	 * se creeaza raporturile fiecarui client, produs, comanda si se genereaza
	 * facturile
	 */
	public void reportClientsPdf(int name) throws DocumentException {
		Connection connect = ConnectionFactory.getConnection();
		String fileName = "/Users/irinacostin/eclipse-workspace/assigment_3/ClientsReport" + name + ".pdf";
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.open();
		Paragraph intro = new Paragraph("\n The clients report: \n");
		Paragraph intro1 = new Paragraph("\n");
		document.add(intro);
		document.add(intro1);
		PdfPTable table = new PdfPTable(2);
		ArrayList<Client> listOfClients = new ArrayList<Client>();
		ClientDAO client = new ClientDAO();
		listOfClients = client.ViewAll(connect);
		PdfPCell cell1 = new PdfPCell(new Paragraph("Name"));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Address"));
		table.addCell(cell1);
		table.addCell(cell2);

		for (int i = 0; i < client.numberOfRows(connect); i++) {
			cell1 = new PdfPCell(new Paragraph(listOfClients.get(i).getName()));
			cell2 = new PdfPCell(new Paragraph(listOfClients.get(i).getAddress()));
			table.addCell(cell1);
			table.addCell(cell2);
		}
		try {
			document.add(table);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		document.close();
	}

	public void reportProductsPdf(int name) throws DocumentException {
		Connection connect = ConnectionFactory.getConnection();
		String fileName = "/Users/irinacostin/eclipse-workspace/assigment_3/ProductsReport" + name + ".pdf";
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.open();
		Paragraph intro = new Paragraph("\n The products report: \n");
		Paragraph intro1 = new Paragraph("\n");
		document.add(intro);
		document.add(intro1);
		PdfPTable table = new PdfPTable(3);
		ArrayList<Product> listOfProducts = new ArrayList<Product>();
		ProductDAO product = new ProductDAO(connect);
		listOfProducts = product.ViewAll(connect);

		PdfPCell cell1 = new PdfPCell(new Paragraph("Name"));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Quantity"));
		PdfPCell cell3 = new PdfPCell(new Paragraph("Price"));
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);

		for (int i = 0; i < product.numberOfRows(connect); i++) {
			cell1 = new PdfPCell(new Paragraph(listOfProducts.get(i).getName()));
			String quantity = Integer.toString(listOfProducts.get(i).getQuantity());
			cell2 = new PdfPCell(new Paragraph(quantity));
			String price = Float.toString(listOfProducts.get(i).getPrice());
			cell3 = new PdfPCell(new Paragraph(price));
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
		}
		try {
			document.add(table);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		document.close();
	}

	public void reportOrdersPdf(int name) throws DocumentException {
		Connection connect = ConnectionFactory.getConnection();
		String fileName = "/Users/irinacostin/eclipse-workspace/assigment_3/OrdersReport" + name + ".pdf";
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.open();
		Paragraph intro = new Paragraph("\n The orders report: \n");
		Paragraph intro1 = new Paragraph("\n");
		document.add(intro);
		document.add(intro1);
		PdfPTable table = new PdfPTable(3);
		ArrayList<Order> listOfOrders = new ArrayList<Order>();
		OrderDAO order = new OrderDAO(connect);
		listOfOrders = order.ViewAll(connect);
		PdfPCell cell1 = new PdfPCell(new Paragraph("Client name"));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Product name"));
		PdfPCell cell3 = new PdfPCell(new Paragraph("Quantity"));
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		for (int i = 0; i < order.numberOfRows(connect); i++) {
			cell1 = new PdfPCell(new Paragraph(listOfOrders.get(i).getClientName()));
			cell2 = new PdfPCell(new Paragraph(listOfOrders.get(i).getProductName()));
			String quantity = Integer.toString(listOfOrders.get(i).getQuantity());
			cell3 = new PdfPCell(new Paragraph(quantity));
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
		}
		try {
			document.add(table);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		document.close();
	}

	public void makeBill(String elem1, Product elem2, String elem3, int cod) throws DocumentException {
		// Connection connect=ConnectionFactory.getConnection();
		String fileName = "/Users/irinacostin/eclipse-workspace/assigment_3/BillOf" + elem1 + "_" + cod + ".pdf";
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.open();
		if (cod > 0) {
			Paragraph intro = new Paragraph("\n The bill of the client: " + elem1 + "  \n");
			Paragraph intro1 = new Paragraph("\n");
			doc.add(intro);
			doc.add(intro1);
			PdfPTable table = new PdfPTable(3);
			PdfPCell cell1 = new PdfPCell(new Paragraph("Client"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Product"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Price"));
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			cell1 = new PdfPCell(new Paragraph(elem1));
			cell2 = new PdfPCell(new Paragraph(elem2.getName()));
			float price = elem2.getPrice();
			cell3 = new PdfPCell(new Paragraph(Integer.parseInt(elem3) * price + "$"));
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			try {
				doc.add(table);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Paragraph intro = new Paragraph(
					"\n The bill of the client: " + elem1 + "  could not be processed due to amount of items in stock");
			doc.add(intro);
		}
		doc.close();
	}

}
