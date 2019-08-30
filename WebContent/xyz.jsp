<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.cts.dao.TransactionDaoFactory"%>
<%@page import="com.cts.dao.TransactionDao"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="com.cts.dao.AppLookupDao"%>
<%@page import="com.cts.dao.AppLookupDaoFactory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>



<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

<%
LocalDate ld = LocalDate.now();
String Today = DateTimeFormatter.ofPattern("yyy-MM-dd").format(ld);

String selectedrange="";
String year="";
String month="";

if(session.getAttribute("FullDatedata")!=null){

	String fulldatepicked =(String)session.getAttribute("FullDatedata");//as in year-month-date 2019-08-15

	Date d;

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

	
}



if(session.getAttribute("DateBreakup")==null){	
%>
<form action="DateSrv" method="post">
	<i class="fa fa-calendar" style="font-size:18px;color:black"></i>
	<input type="date" max="<%=Today%>" name="day">
	<input type="submit" value="Pick a date">
</form>

<% 	
}else{
	List<String> ranges =(List<String>)session.getAttribute("DateBreakup");
	String s=(String)session.getAttribute("Datedata");
	
	for(String range:ranges){
		
		String arr[]=range.split("-");
		
		ranges.indexOf(range);
		
		int start = Integer.parseInt(arr[0]);
		int end = Integer.parseInt(arr[1]);
		int pickeddate = Integer.parseInt(s);
		
		if(pickeddate>=start && pickeddate<=end){
			selectedrange=range+"-"+month+"-"+year;
%>
<h3>For <%=ranges.indexOf(range)+1%>nd week of August(<%=start %>-<%=end %>)</h3>
<a href="ResetDateSrv">Want to pick another date?</a>
<% 					
		}
	} 		
}
%>
<br>
<br>
<br>
<button class="collapsible">LSR</button>
	<div class="content">
	<%
	
	AppLookupDao ald = AppLookupDaoFactory.getAppLookupDao();
	TransactionDao tran = TransactionDaoFactory.getTransactionDao();

	List<String> l = ald.getAllAppByID("LSR");

	for(String app:l){
		
		AppLookupDao appld=AppLookupDaoFactory.getAppLookupDao();
		String appid = appld.getIDByApp(app);//APPID
	%>
		<button class="collapsible"><%=app %></button>
		
		<%if(session.getAttribute("DateBreakup")!=null){ 
			
			System.out.println(tran.checkDataEntryByDateRangeAndAppID(appid, selectedrange));
			if(!tran.checkDataEntryByDateRangeAndAppID(appid, selectedrange)){
				
			%>
				<a href=EditTimesheetSrv?appsel=<%=app%>><i class="material-icons" style="font-size:20px;color:red">mode_edit</i></a>
			<% 
				
			}
			else{
			%>
					<a href=ViewTimesheetSrv?appselforview=<%=app%>><i class="material-icons" style="font-size:20px;color:green">remove_red_eye</i></a>
			<% 
				
			}
		}	
	}
	%>
	</div>

<button class="collapsible">MN</button>
	<div class="content">
	<%
	List<String> l1 = ald.getAllAppByID("MN");

	for(String app:l1){
	%>
		<button class="collapsible"><%=app %></button>

<%if(session.getAttribute("DateBreakup")!=null){ 
		%>
		<a href=EditTimesheetSrv?appsel=<%=app%>><i class="material-icons" style="font-size:20px;color:red">mode_edit</i></a>
		<a href=ViewTimesheetSrv?appselforview=<%=app%>><i class="material-icons" style="font-size:20px;color:green">remove_red_eye</i></a>
		<% 
		}	
	}
	%>
	</div>
	
<button class="collapsible">EMEA</button>
	<div class="content">
	<%
	List<String> l2 = ald.getAllAppByID("EMEA");

	for(String app:l2){
	%>
		<button class="collapsible"><%=app %></button>
		
		<%if(session.getAttribute("DateBreakup")!=null){ 
		%>
		<a href=EditTimesheetSrv?appsel=<%=app%>><i class="material-icons" style="font-size:20px;color:red">mode_edit</i></a>
		<a href=ViewTimesheetSrv?appselforview=<%=app%>><i class="material-icons" style="font-size:20px;color:green">remove_red_eye</i></a>
		<% 
		}	
	}
	%>
	</div>
	
	
	
	
	

	
<script>
var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.maxHeight){
      content.style.maxHeight = null;
    } else {
      content.style.maxHeight = content.scrollHeight + "px";
    } 
  });
}


</script>	
	
	
	
</body>
</html>