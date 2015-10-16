<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8"/>
<title> MyApp-Test</title>
 <%@include file="../tiles/css.jsp" %>
</head>
<body class="page-header-fixed page-quick-sidebar-push-content">
<div class="page-header navbar navbar-fixed-top">
		 <% //@include file="../tiles/header.jsp" %>

</div>
<div class="page-container">
		<%@include file="../tiles/menu.jsp" %>

	<div class="page-content-wrapper">
		<div class="page-content">	
			<h3 class="page-title">
			Push Content <small>push content on quick sidebar expand</small>
			</h3>
			<div class="row">
				<div class="col-md-12">								
						<div class="portlet-body flip-scroll">
							<table class="table table-bordered table-striped table-condensed flip-content">
							<thead class="flip-content">
								<tr>
									<th>
										<div class="checkbox-list">
											 <input type="checkbox" class="checkbox-inline" id="selectAll">
										</div>
									</th>
									<th>
										 Account
									</th>
									<th class="numeric">
										 Inbox
									</th>
									<th class="numeric">
										 Spam
									</th>
									<th>
										 Origin
									</th>
									<th width="190"></th>									
								</tr>
							</thead>
							<tbody>
							
						</tbody>
						</table>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</div>
	<%//@include file="../tiles/footer.jsp" %>
	<%@include file="../tiles/scripts.jsp" %>

</body>
</html>