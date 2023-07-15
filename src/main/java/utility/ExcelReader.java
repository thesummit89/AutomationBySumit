package utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import basetest.TestBase;

public class ExcelReader extends TestBase {
	  public Fillo fillo;
	  public Connection connection;
	  public Recordset rs;

	public   List<String> getDataFromExcelColumn(String FileNamenLocation,String coulmnName, String sheetName) throws FilloException {
		List<String> List = new ArrayList<String>();
		try{
			/*
			 * Fillo fillo = new Fillo(); Connection connection =
			 * fillo.getConnection(Constant.TEST_DATA_PATH+FileNamenLocation+".xlsx");
			 */
			Recordset rs=executeQuery(FileNamenLocation, "Select * from "+sheetName);
		
		
		while (rs.next()) {
			ArrayList<String> dataColl = rs.getFieldNames(); 
			//Iterator<String> dataIterator = dataColl.iterator();
			String dataVal = rs.getField(coulmnName);
			if(dataVal.trim()!=null && !dataVal.trim().isEmpty()){
			List.add(dataVal.trim());}
		}
		System.out.println("List is "+List);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail("Issue while Fetching data from Excel Sheet , For Column Name-> "+coulmnName +"and sheet Name ->"+sheetName+ "\n"+ "Please Find Excel Sheet Path: "+FileNamenLocation);
		}
		return List;
		
	}
	
	private Connection getConnection(String fileNamenLocation) {
		try {
			fillo = new Fillo();
			connection = fillo.getConnection(Constant.TEST_DATA_PATH+fileNamenLocation+".xlsx");
		}catch(Exception e) {
			e.printStackTrace();
			Assert.fail("Failed while making connection with excel sheet");
		}
		return connection;
	}
	
	/**
	 * @method - executes query and returns record set 
	 * @param query
	 * @return 
	 * @return 
	 */
	public Recordset executeQuery(String fileName,String query) {
		try {
			 rs= getConnection(fileName).executeQuery(query);
			 
		}catch(Exception e) {
			e.printStackTrace();
			Assert.fail("Failed while executing query");
		
		}
		
		return rs;
		
	}
	
	public void closeConnection() {
		rs.close();
		connection.close();
	}
	
	}
