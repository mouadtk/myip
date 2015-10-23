<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8" lang="en"><![endif]-->
<!--[if IE 9]><html class="ie9" lang="en"><![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- start: HEAD -->
<!-- Mirrored from www.cliptheme.com/demo/rapido/pages_blank_page.html by HTTrack Website Copier/3.x [XR&CO'2014], Sun, 27 Jul 2014 06:19:20 GMT -->
<head>
	<title>IP Owner Ranges</title>
	<!-- start: META -->
	<meta charset="utf-8" />
	<!--[if IE]><meta http-equiv='X-UA-Compatible' content="IE=edge,IE=9,IE=8,chrome=1" /><![endif]-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="" name="description" />
	<meta content="" name="author" />
	<!-- end: META -->
	<!-- start: MAIN CSS -->
	<link href='http://fonts.googleapis.com/css?family=Raleway:400,300,500,600,700,200,100,800' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/plugins/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="assets/plugins/iCheck/skins/all.css">
	<link rel="stylesheet" href="assets/plugins/perfect-scrollbar/src/perfect-scrollbar.css">
	<link rel="stylesheet" href="assets/plugins/animate.css/animate.min.css">
	<!-- end: MAIN CSS -->
	<!-- start: CSS REQUIRED FOR SUBVIEW CONTENTS -->
	<link rel="stylesheet" href="assets/plugins/owl-carousel/owl-carousel/owl.carousel.css">
	<link rel="stylesheet" href="assets/plugins/owl-carousel/owl-carousel/owl.theme.css">
	<link rel="stylesheet" href="assets/plugins/owl-carousel/owl-carousel/owl.transitions.css">
	<link rel="stylesheet" href="assets/plugins/summernote/dist/summernote.css">
	<link rel="stylesheet" href="assets/plugins/fullcalendar/fullcalendar/fullcalendar.css">
	<link rel="stylesheet" href="assets/plugins/toastr/toastr.min.css">
	<link rel="stylesheet" href="assets/plugins/bootstrap-select/bootstrap-select.min.css">
	<link rel="stylesheet" href="assets/plugins/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css">
	<link rel="stylesheet" href="assets/plugins/DataTables/media/css/DT_bootstrap.css">
	<link rel="stylesheet" href="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.min.css">
	<link rel="stylesheet" href="assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css">
	<!-- end: CSS REQUIRED FOR THIS SUBVIEW CONTENTS-->
	<!-- start: CSS REQUIRED FOR THIS PAGE ONLY -->
	<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2.css" />
		
	<!-- end: CSS REQUIRED FOR THIS PAGE ONLY -->
	<!-- start: CORE CSS -->
	<link rel="stylesheet" href="assets/css/styles.css">
	<link rel="stylesheet" href="assets/css/styles-responsive.css">
	<link rel="stylesheet" href="assets/css/plugins.css">
	<link rel="stylesheet" href="assets/css/themes/theme-default.css" type="text/css" id="skin_color">
	<link rel="stylesheet" href="assets/css/print.css" type="text/css" media="print"/>
	<!-- end: CORE CSS -->
	<link rel="shortcut icon" type="image/ico"  href="assets/images/favicon.ico" />
</head>
<!-- end: HEAD -->
<!-- start: BODY -->
<body>
	<!-- start: SLIDING BAR (SB) -->
	<!-- end: SLIDING BAR -->
	<div class="main-wrapper">
	<!-- start: MAIN CONTAINER -->
		<div class="main-container inner">
			<!-- start: PAGE -->
			<div class="main-content" style="min-height: 341px;margin-right: 120px;margin-left: -132px;">
				<!-- end: SPANEL CONFIGURATION MODAL FORM -->
				<div class="container">
					<!-- start: PAGE HEADER -->
					<!-- start: TOOLBAR -->
					<div class="toolbar row">
						<div class="col-sm-6 hidden-xs">
							<div class="page-header">
								<h1>HOTMAIL <small>Mail headers</small></h1>
							</div>
						</div>
					</div>
					<!-- end: TOOLBAR -->
					<!-- end: PAGE HEADER -->
					<!-- start: BREADCRUMB -->
					<div class="row">
						<div class="col-md-12">
							<ol class="breadcrumb">
								<li>
									<a href="#">
										Dashboard
									</a>
								</li>									
							</ol>
						</div>
					</div>
					<!-- end: BREADCRUMB -->
					<!-- start: PAGE CONTENT -->
					<div class="row">
						<div class="col-md-12">
							<div class="panel panel-white" style="position: relative;margin: 20px auto;width: 60%;">
								<div class="panel-body results">
									<div class="panel-body">
										<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="/index/formProcess">
											<div class="form-group">
												<div class="col-sm-6">
													<label>
														Server Fileze
													</label>
													<div data-provides="fileupload" class="fileupload fileupload-new">
														<span class="btn btn-file btn-primary"><i class="fa fa-folder-open-o"></i> <span class="fileupload-new">Select file</span><span class="fileupload-exists">Source file</span>
															<input type="file" name="file">
														</span>
														<span class="fileupload-preview"></span>
														<a data-dismiss="fileupload" class="close fileupload-exists float-none" href="">
															&times;
														</a>
													</div>
													<p class="help-block">
														Upload servers source file.
													</p>
												</div>
												<div class="col-sm-6">
													<br /> Name: <input type="text" name="name"><br /> <br /> 
													<input class="btn btn-primary" type="submit" value="Upload"> Press here to upload the file!
												</div>
											</div>
										</form>
									</div>
								</div>
								<!-- end: PAGE CONTENT-->
							</div>
							<!-- start: DYNAMIC TABLE PANEL -->
								<div class="panel panel-white tablePanel" style="  min-height: 300px;">
									<div class="panel-heading">
										<h4 class="panel-title">Title<span class="text-bold" id="mailLabel"> </span> sub title</h4>										
									</div>
									<div class="panel-body panel-result">
										<table class="table  table-striped table-bordered table-hover table-full-width" id="sample_1" >
											<thead>
												<tr>
													<th>Owner Name</th>
													<th class="hidden-xs">Nbr Servers</th>
													<th>Servers </th>
													<th class="hidden-xs"> IP Ranges</th>
												</tr>
											</thead>
											<tbody>
											<c:forEach items="${_owners}" var="owner">
												<tr>
													<td>${owner.name}</td>
													<td class="hidden-xs" style="width:70px">${fn:length(owner.servers)}</td>
													<td>
														<c:forEach items="${owner.servers}" var="server">
															${server.name},
														</c:forEach>
													</td>
													<td class="hidden-xs">
														<c:forEach items="${owner.range}" var="range">
															[${range.value}]
														</c:forEach>
													</td>
												</tr>
											</c:forEach>						
											</tbody>
										</table>
									</div>
									</div>

								</div>  
								<!-- end: DYNAMIC TABLE PANEL -->
						</div>
						<!-- end: PAGE -->
					</div>
					<!-- end: MAIN CONTAINER -->
					<!-- start: FOOTER -->
					<footer class="inner">
						<div class="footer-inner">
							<div class="pull-left">
								2015 &copy; IT OPM - A&M.
							</div>
							<div class="pull-right">
								<span class="go-top"><i class="fa fa-chevron-up"></i></span>
							</div>
						</div>
					</footer>
					<!-- end: FOOTER -->
					<!-- start: SUBVIEW SAMPLE CONTENTS -->
					<!-- *** NEW NOTE *** -->		
				</div>
				<!-- start: MAIN JAVASCRIPTS -->
		<!--[if lt IE 9]>
		<script src="assets/plugins/respond.min.js"></script>
		<script src="assets/plugins/excanvas.min.js"></script>
		<script type="text/javascript" src="assets/plugins/jQuery/jquery-1.11.1.min.js"></script>
		<![endif]-->
		<!--[if gte IE 9]><!-->
		<script src="assets/plugins/jQuery/jquery-2.1.1.min.js"></script>
		<!--<![endif]-->
		<script src="assets/plugins/jquery-ui/jquery-ui-1.10.2.custom.min.js"></script>
		<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
		<script src="assets/plugins/blockUI/jquery.blockUI.js"></script>
		<script src="assets/plugins/iCheck/jquery.icheck.min.js"></script>
		<script src="assets/plugins/moment/min/moment.min.js"></script>
		<script src="assets/plugins/perfect-scrollbar/src/jquery.mousewheel.js"></script>
		<script src="assets/plugins/perfect-scrollbar/src/perfect-scrollbar.js"></script>
		<script src="assets/plugins/bootbox/bootbox.min.js"></script>
		<script src="assets/plugins/jquery.scrollTo/jquery.scrollTo.min.js"></script>
		<script src="assets/plugins/ScrollToFixed/jquery-scrolltofixed-min.js"></script>
		<script src="assets/plugins/jquery.appear/jquery.appear.js"></script>
		<script src="assets/plugins/jquery-cookie/jquery.cookie.js"></script>
		<script src="assets/plugins/velocity/jquery.velocity.min.js"></script>
		<!-- end: MAIN JAVASCRIPTS -->
		<!-- start: JAVASCRIPTS REQUIRED FOR SUBVIEW CONTENTS -->
		<script src="assets/plugins/owl-carousel/owl-carousel/owl.carousel.js"></script>
		<script src="assets/plugins/jquery-mockjax/jquery.mockjax.js"></script>
		<script src="assets/plugins/toastr/toastr.js"></script>
		<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js"></script>
		<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"></script>
		<script src="assets/plugins/fullcalendar/fullcalendar/fullcalendar.min.js"></script>
		<script src="assets/plugins/bootstrap-switch/dist/js/bootstrap-switch.min.js"></script>
		<script src="assets/plugins/bootstrap-select/bootstrap-select.min.js"></script>
		<script src="assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
		<script src="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
		<script src="assets/plugins/DataTables/media/js/jquery.dataTables.min.js"></script>
		<script src="assets/plugins/DataTables/media/js/DT_bootstrap.js"></script>
		<script src="assets/plugins/truncate/jquery.truncate.js"></script>
		<script src="assets/plugins/summernote/dist/summernote.min.js"></script>
		<script src="assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
		<script src="assets/js/subview.js"></script>
		<script src="assets/js/subview-examples.js"></script>
		<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
		<script type="text/javascript" src="assets/js/table-data.js"></script>
		<script type="text/javascript" src="assets/scripts/script_01.js"></script>
		<!-- end: JAVASCRIPTS REQUIRED FOR SUBVIEW CONTENTS -->
		<!-- start: JAVASCRIPTS REQUIRED FOR THIS PAGE ONLY -->
		<!-- end: JAVASCRIPTS REQUIRED FOR THIS PAGE ONLY -->
		<!-- start: CORE JAVASCRIPTS  -->
		<script src="assets/js/main.js"></script>
		<!-- end: CORE JAVASCRIPTS  -->
		<script>
			jQuery(document).ready(function() {
				Main.init();
				TableData.init();
			});
		</script>
	</body>
	<!-- end: BODY -->

	<!-- Mirrored from www.cliptheme.com/demo/rapido/pages_blank_page.html by HTTrack Website Copier/3.x [XR&CO'2014], Sun, 27 Jul 2014 06:19:20 GMT -->
	</html>