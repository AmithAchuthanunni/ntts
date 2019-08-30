package com.cts.srv;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DateSrv
 */
@WebServlet("/DateSrv")
public class DateSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DateSrv() {
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
		
		String datepicked = request.getParameter("day");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date d;
		try {
			d = sdf.parse(datepicked);
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("EEEEE");
			String day = sdf1.format(d);

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
			String year = sdf2.format(d);

			SimpleDateFormat sdf3 = new SimpleDateFormat("MM");
			String month = sdf3.format(d);

			SimpleDateFormat sdf4 = new SimpleDateFormat("dd");
			String date = sdf4.format(d);

			System.out.println(year);
			System.out.println(month);
			System.out.println(date);
			
			
			int noOfdays[]={31,28,31,30,31,30,31,31,30,31,30,31};
			String days[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
			int monDays = noOfdays[Integer.parseInt(month.trim())-1];

			int start=0;
			int end=0;

			ArrayList<String> al = new ArrayList<String>();

			for(int i=1;i<=monDays;){
				String modDate=year+"-"+month+"-"+i;
				String augDays = sdf1.format(sdf.parse(modDate));
				System.out.println(i+" "+augDays);
				
				if(!augDays.equalsIgnoreCase("Saturday") && !augDays.equalsIgnoreCase("Sunday"))
				{
					start=i;
					for(int j=0;j<days.length;j++)
					{
						if(days[j].equalsIgnoreCase(augDays))
						{
							i=i+(4-j);
							end=i;
							i=i+3;
							break;
						}
					}
					if(end<=monDays){
						System.out.println(start+"-"+end);
						al.add(start+"-"+end);
						
					}
					else{
						System.out.println(start+"-"+monDays);
						al.add(start+"-"+monDays);
					}
				}
				else if(augDays.equalsIgnoreCase("Saturday")){
					i=i+2;
				}
				else
				{
					i=i+1;
				}
				
			}
			HttpSession ses=request.getSession();
			ses.setAttribute("DateBreakup",al);
			ses.setAttribute("Datedata",date);
			ses.setAttribute("FullDatedata",datepicked);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("adminHome.jsp").forward(request, response);
		
		
	}

}
