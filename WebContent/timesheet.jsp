<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.cts.dao.TransactionDao"%>
<%@page import="com.cts.dao.TransactionDaoFactory"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.cts.dao.AppLookupDao"%>
<%@page import="com.cts.dao.AppLookupDaoFactory"%>
<%@page import="java.util.List"%>
<%@page import="com.cts.dao.ActivityLookupDao"%>
<%@page import="com.cts.dao.ActivityLookupDaoFactory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
LocalDate ld = LocalDate.now();
String today = DateTimeFormatter.ofPattern("yyy-MM-dd").format(ld);

String app = (String)session.getAttribute("AppData");

List<String> ranges = (List<String>)session.getAttribute("DateBreakup");//date range breakup of month

ActivityLookupDao ald = ActivityLookupDaoFactory.getActivityLookupDao();

List<String> activities = ald.getAllActivityById(app);//activity names of selected app

int datepicked =Integer.parseInt((String)session.getAttribute("Datedata")); //picked date

AppLookupDao appld=AppLookupDaoFactory.getAppLookupDao();
String appid = appld.getIDByApp(app);//Appid of selected app

String fulldatepicked =(String)session.getAttribute("FullDatedata");//as in year-month-date 2019-08-15

Date d;
Date d1;
String year="";
String month="";

String year_today="";
String month_today="";
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
try {
	 d = sdf.parse(fulldatepicked);
	 d1 =sdf.parse(today);
	 
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
	year = sdf2.format(d);

	SimpleDateFormat sdf3 = new SimpleDateFormat("MM");
	month = sdf3.format(d);
	
	year_today = sdf2.format(d1);

	month_today = sdf3.format(d1);

	
	
} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

%>
<br><br>
<form action="SubmitTimesheetSrv" method="post">
<table align="center">

<tr> 
	<th>Non Ticketing Activity</th>
	<%
for(String range:ranges){
%>
<th><%=range %></th>

<%} %>
</tr>


<%
for(String activity:activities){
%>
<tr>
<td><%=activity %></td>
<%
for(String range:ranges){
	
	String arr[]=range.split("-");
	
	int start = Integer.parseInt(arr[0]);
	int end = Integer.parseInt(arr[1]);
	
	TransactionDao tran =TransactionDaoFactory.getTransactionDao();
	boolean flag = tran.checkDataEntryByDateRangeAppIDandActivity(appid, range+"-"+month+"-"+year,activity);
	
	if(datepicked>=start || Integer.parseInt(month)<Integer.parseInt(month_today) || Integer.parseInt(year)<Integer.parseInt(year_today)){//check to prevent future date entry
		
		if(!flag){
%>
<td><input type="number" name="<%=activity+range%>" required="required"></td>

<% 
		}else{
			
			String effort=tran.getEffortByDateRangeAppIDandActivity(appid,range+"-"+month+"-"+year,activity);
%>
<td><input type="number" readonly="readonly" value="<%=effort%>"></td>
<% 				
		} 		
	}else{
%>
<td><input type="number" readonly="readonly"></td>
<% 		
	} 	
}
%>
</tr>
<% 
}
%>
</table>


<input type="submit" value="SUBMIT">
</form>
</body>
</html>


