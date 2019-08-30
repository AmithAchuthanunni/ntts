package com.cts.srv;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cts.beans.TransactionBean;
import com.cts.dao.ActivityLookupDao;
import com.cts.dao.ActivityLookupDaoFactory;
import com.cts.dao.AppLookupDao;
import com.cts.dao.AppLookupDaoFactory;
import com.cts.dao.TransactionDao;
import com.cts.dao.TransactionDaoFactory;

/**
 * Servlet implementation class SubmitTimesheetSrv
 */
@WebServlet("/SubmitTimesheetSrv")
public class SubmitTimesheetSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitTimesheetSrv() {
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
		
		//pick activity
		
		HttpSession ses=request.getSession();
		
		List<String> ranges = (List<String>)ses.getAttribute("DateBreakup");//range
		
		String app = (String)ses.getAttribute("AppData");
		
		ActivityLookupDao ald = ActivityLookupDaoFactory.getActivityLookupDao();
		AppLookupDao appld=AppLookupDaoFactory.getAppLookupDao();

		List<String> activities = ald.getAllActivityById(app);//activity names of selected app
		
		String appid = appld.getIDByApp(app);
		
		int datepicked =Integer.parseInt((String)ses.getAttribute("Datedata")); //picked date as in 1,2,3,31,23..
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
		
		TransactionDao tran =TransactionDaoFactory.getTransactionDao();
		
		for(String activity:activities) {
			
			for(String range:ranges) {
				
				String arr[]=range.split("-");
				
				int start = Integer.parseInt(arr[0]);
				int end = Integer.parseInt(arr[1]);
				
				boolean flag = tran.checkDataEntryByDateRangeAppIDandActivity(appid, range+"-"+month+"-"+year,activity);
				
				if(datepicked>=start && !flag){
					
					TransactionBean tb = new TransactionBean(appid, activity,range+"-"+month+"-"+year,request.getParameter(activity+range) );
					
					//TransactionDao tran =TransactionDaoFactory.getTransactionDao();
					System.out.println(tran.addTransaction(tb));
					
					/*System.out.print(appid+" ");
					System.out.print(activity+" ");
					System.out.print(range+"-"+month+"-"+year+" ");
					System.out.println(request.getParameter(activity+range));*/	
				}	
			}
		}
		
		
		request.getRequestDispatcher("submitSuccess.jsp").forward(request, response);
		
	}

}
