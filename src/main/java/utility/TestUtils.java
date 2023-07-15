package utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.MultiValueMap;
import org.junit.Assert;

public class TestUtils {
	public SimpleDateFormat dateFormat;
	
	
	public String returnDateBasedonLocale(String date) {
		String custDate=null;
		try {
			dateFormat = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
			Date sdate = dateFormat.parse(date);
			dateFormat.applyPattern("EEE MMM dd yyyy");
			custDate= dateFormat.format(sdate);
			//System.out.println("date us "+ custDate);
		}catch(Exception e) {
			e.printStackTrace();
			Assert.fail("Failed while returnig date"+ e.getMessage());
		}
		return custDate;
	}
	
	public String getMonthAndyear(String date) {
		String custDate=null;
		try {
			dateFormat = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
			Date sdate = dateFormat.parse(date);
			dateFormat.applyPattern("MMMMyyyy");
			custDate= dateFormat.format(sdate);
			
		}catch(Exception e) {
			e.printStackTrace();
			Assert.fail("Failed while returnig date"+ e.getMessage());
		}
		return custDate;
	}

	
	  
	  /**
	   * @author bsumm
	   * @param fileName - Name file xlsx
	   * @param col1 - TestCase
	   * @param col2 - AutomationTest
	   * @param col3 - RunMOde
	   * @param sheetName - tab name
	   * @return  - it will retun multiValuemap where key as AutomationTest and value as TestCase & Rummode
	   ****/
	  public MultiValueMap loadMultiValMap(String fileName,String col1 ,String col2, String col3, String sheetName) {
		  ExcelReader reader = new ExcelReader();
		  MultiValueMap map = new  MultiValueMap();
		
		  try {
		   List<String> listcol1 = reader.getDataFromExcelColumn(fileName, col1, sheetName);
		   List<String> listcol2 = reader.getDataFromExcelColumn(fileName, col2, sheetName);
		   List<String> listcol3 = reader.getDataFromExcelColumn(fileName, col3, sheetName);
		   
		   for(int i=0 ;i<listcol1.size();i++) {
			   map.put(listcol2.get(i),listcol1.get(i).toString().replace("[", "").replace("]", ""));
			   map.put(listcol2.get(i),listcol3.get(i).toString().replace("[", "").replace("]", ""));
		   }
			  System.out.println(map);
		  }catch(Exception e) {
			  e.printStackTrace();
			  Assert.fail("Failed while ...");
		  }
			
		  return map;
		  
	  }
	  
	  /**
	   * 
	   * @param fileName
	   * @param fieldName
	   * @param testName
	   * @param sheetName
	   * @return
	   */
	  public HashMap<String, String> loadTestDataMap(String fileName,String fieldName ,String testName, String sheetName) {
		  ExcelReader reader = new ExcelReader();
		  HashMap<String, String> map = new  HashMap();
		
		  try {
		   List<String> fieldNameList = reader.getDataFromExcelColumn(fileName, fieldName, sheetName);
		   List<String> testDataList = reader.getDataFromExcelColumn(fileName, testName, sheetName);
			   
		   for(int i=0 ;i<fieldNameList.size();i++) {
			  map.put(fieldNameList.get(i), testDataList.get(i));
		   }
			  System.out.println(map);
		  }catch(Exception e) {
			  e.printStackTrace();
			  Assert.fail("Failed while ...");
		  }
		      
		

		  return map;
		  
	  }
	  
	  
	  
	  public static void main(String [] args) { TestUtils utils = new TestUtils();
	 // utils.returnDateBasedonLocale("29/08/2023"); 
	  MultiValueMap map =utils.loadMultiValMap("TestDataMasterSheet", "TestCase", "AutomationTest", "RunMode",
				"Runner");
	  String str=  map.get("e2e_select_toAndFromCity").toString();
	  
	  String arr [] = str.split(",");
	  for(int i=0; i<arr.length; i++) {
		System.out.println(arr[i]);
	  }
	 
	     
	  }
	  
	  
}
