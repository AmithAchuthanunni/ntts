package com.cts.srv;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cts.dao.ActivityLookupDao;
import com.cts.dao.ActivityLookupDaoFactory;
import com.cts.dao.AppLookupDao;
import com.cts.dao.AppLookupDaoFactory;
import com.cts.dao.TransactionDao;
import com.cts.dao.TransactionDaoFactory;



/**
 * Servlet implementation class WriteExcelSrv
 */
@WebServlet("/WriteExcelSrv")
public class WriteExcelSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteExcelSrv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	
		HttpSession ses= request.getSession();
		String app = (String)ses.getAttribute("AppDataForView");
		
		List<String> ranges = (List<String>)ses.getAttribute("DateBreakup");
		
		ActivityLookupDao ald = ActivityLookupDaoFactory.getActivityLookupDao();

		List<String> activities = ald.getAllActivityById(app);//activity names of selected app
		
		String fulldatepicked =(String)ses.getAttribute("FullDatedata");//as in year-month-date 2019-08-15

		Date d;
		String year="";
		String month="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			 d = sdf.parse(fulldatepicked);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
			year = sdf2.format(d);

			SimpleDateFormat sdf3 = new SimpleDateFormat("MM");
			month = sdf3.format(d);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AppLookupDao appld=AppLookupDaoFactory.getAppLookupDao();
		String appid = appld.getIDByApp(app);//Appid of selected app
		
		TransactionDao tran =TransactionDaoFactory.getTransactionDao();
		
		
        XSSFWorkbook workbook = new XSSFWorkbook(); 
        
        // Create a blank sheet 
        XSSFSheet sheet = workbook.createSheet("effort Details"); 
      
      Map<String, Object[]> data = new TreeMap<String, Object[]>();
      
      Object[] arr = new Object[ranges.size()+1];
      
      arr[0]="Activity";
      
      int i=1;
      int j=1;
      
      for(String range:ranges) {
    	  
    	  arr[i]=range;
    	  i++;
      }
      
      data.put(j+"",arr);
      
      for(String activity:activities) {
    	  
    	  arr=new Object[ranges.size()+1];
    	  
    	  i=0;
    	  arr[i]=activity;
    	  for(String range:ranges) {
    		  
    		  i++;
    		  String effort=tran.getEffortByDateRangeAppIDandActivity(appid,range+"-"+month+"-"+year, activity);
    		  if(effort!=null)
    			  arr[i]=effort;
    		  else
    			  arr[i]=0+"";
    	  }
    	  j++;
    	  data.put(j+"", arr);
    	  
    	  
      }

      // Iterate over data and write to sheet 
      Set<String> keyset = data.keySet(); 
      int rownum = 0; 
      for (String key : keyset) { 
          // this creates a new row in the sheet 
    	  XSSFRow row = sheet.createRow(rownum++); 
          Object[] objArr = data.get(key); 
          int cellnum = 0; 
          for (Object obj : objArr) { 
              // this line creates a cell in the next column of that row 
              Cell cell = row.createCell(cellnum++); 
              if (obj instanceof String) 
                  cell.setCellValue((String)obj); 
              else if (obj instanceof Integer) 
                  cell.setCellValue((Integer)obj); 
          } 
      } 
      try { 
          // this Writes the workbook gfgcontribute 
          FileOutputStream out = new FileOutputStream(new File("C:\\Users\\613161\\DownloadedExcel\\Writesheet.xlsx")); 
          workbook.write(out); 
          out.close(); 
          System.out.println("Writesheet.xlsx written successfully on disk."); 
      } 
      catch (Exception e) { 
          e.printStackTrace(); 
      } 
      
      
      
      /*int i=1;
      String arr[] = new String[ranges.size()+1];
      arr[0]="Activity";
      for(String range:ranges) {
    	  
    	arr[i]=range;
    	i++;
    	  
      }
      data.put("1", arr);
      
      i=2;
      Arrays.fill(arr, null );
      for(String activity:activities) {
    	  
    	  int j=0;
    	  arr[j]=activity;
    	  
    	  for(String range:ranges) {
    		  
    		  String effort=tran.getEffortByDateRangeAppIDandActivity(appid,range+"-"+month+"-"+year,activity);
    		  j++;
    		  arr[j]=effort;
    	  }
    	  data.put(i+"", arr);
    	  Arrays.fill(arr, null );
    	  i++;
      }
      
      
      
    //Iterate over data and write to sheet
      Set < String > keyid = data.keySet();
      int rowid = 0;
      
      for (String key : keyid) {
         row = spreadsheet.createRow(rowid++);
         Object [] objectArr = data.get(key);
         int cellid = 0;
         
         for (Object obj : objectArr){
            Cell cell = row.createCell(cellid++);
            cell.setCellValue((String)obj);
         }
      }
      
      
      
      
      FileOutputStream out = new FileOutputStream(
    	         new File("C:/Users/613161/Documents/Writesheet.xlsx"));
    	      
    	      workbook.write(out);
    	      out.close();
    	      System.out.println("Writesheet.xlsx written successfully");
    	      
    	      */
    	      request.getRequestDispatcher("adminHome.jsp").forward(request, response);

	}

}
