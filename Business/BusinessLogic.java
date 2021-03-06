package Business;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.itextpdf.text.DocumentException;

import Presentation.ParseFile;

public class BusinessLogic {
	private ParseFile p;
	public BusinessLogic(ParseFile p) {
		this.p=p;
	}
	public static void main(String args[]) throws SQLException, DocumentException, FileNotFoundException {
		ParseFile p=new ParseFile();
		BusinessLogic b=new BusinessLogic(p);
		b.p.readInstructions(args[0]);
		
	}
}
