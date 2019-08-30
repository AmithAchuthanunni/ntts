<%@page import="com.cts.dao.TransactionDaoFactory"%>
<%@page import="com.cts.dao.TransactionDao"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.cts.dao.AppLookupDaoFactory"%>
<%@page import="com.cts.dao.AppLookupDao"%>
<%@page import="com.cts.dao.ActivityLookupDaoFactory"%>
<%@page import="com.cts.dao.ActivityLookupDao"%>
<%@page import="java.util.List"%>
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
String app = (String)session.getAttribute("AppDataForView");

List<String> ranges = (List<String>)session.getAttribute("DateBreakup");

ActivityLookupDao ald = ActivityLookupDaoFactory.getActivityLookupDao();

List<String> activities = ald.getAllActivityById(app);//activity names of selected app

int datepicked =Integer.parseInt((String)session.getAttribute("Datedata")); //picked date

AppLookupDao appld=AppLookupDaoFactory.getAppLookupDao();
String appid = appld.getIDByApp(app);//Appid of selected app

String fulldatepicked =(String)session.getAttribute("FullDatedata");//as in year-month-date 2019-08-15

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

%>
<form action="WriteExcelSrv" method="post">
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
				
		String effort=tran.getEffortByDateRangeAppIDandActivity(appid,range+"-"+month+"-"+year,activity);
	%>
	<td><input type="number" readonly="readonly" value="<%=effort%>"></td>
	<% 				
			} 		
	%>
	</tr>
	<% 
	}
	%>
	</table>
	
	<br><br>
	<div align="center"><input type="submit" value="Export to Excel"></div>
</form>	
</body>
</html>