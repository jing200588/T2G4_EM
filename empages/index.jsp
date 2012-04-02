<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    %>
<!doctype html>
<!--[if lt IE 9]><html class="ie"><![endif]-->
<!--[if gte IE 9]><!--><html><!--<![endif]-->

<head>
	<meta charset="utf-8"/>
	<title>EMan Web View</title>	
	
	<link rel="stylesheet" href="eman.css" type="text/css" />
	</head>
	<body><div id="eman">
	
		<div id="header">
			<h1>EMan - Online Event Listing</h1>
		</div>

		<jsp:useBean id="main" scope="application" class = "emserver.BeanMain" />
		<jsp:setProperty name="main" property="*" />
		
		<!--  
		<div id="nav">
			<ul>
				<li><a href="#upcoming">Upcoming Events</a></li>
				<li><a href="#past">Past Events</a></li>			
			</ul>
		</div>
		-->
		
		
		
		<div id="main-section">
			<div id="upcoming" class="event">
				<h2>Upcoming Events</h2>
			
				<%= main.getEventList() %>
				
			</div>
			
			<div id="past" class="event">
				<h2>Past Events</h2>
			
				<%= main.getEventListArchive() %>
			</div>	
			
			<div class="clear"></div>
		</div>
		
		<div class="clear"></div>
	</div></body>
</html>
	