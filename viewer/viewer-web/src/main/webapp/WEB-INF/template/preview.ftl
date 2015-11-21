<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>模型浏览</title>
<link href="${static_path}/css/reset.css" rel="stylesheet" type="text/css"/>
<link href="${static_path}/css/global.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="https://developer.api.autodesk.com/viewingservice/v1/viewers/style.css" type="text/css">
<script type="text/javascript" src="${static_path}/js/jquery/jquery-1.11.1.min.js"></script>
<script src="https://developer.api.autodesk.com/viewingservice/v1/viewers/viewer3D.min.js"></script>
<script>
$(function () {

	var options = {
		'document' : 'urn:${urn}',
		'env':'AutodeskProduction',
		'getAccessToken': getToken,
		'refreshToken': getToken,
	};

	var viewerElement = document.getElementById('viewer');
	
	// 显示模型
	var viewer = new Autodesk.Viewing.Viewer3D(viewerElement, {});
	Autodesk.Viewing.Initializer(options,function() {
		viewer.initialize();
		loadDocument(viewer, options.document);
	});
	
	function getToken() {
		return '${token}';
	}
	
	function loadDocument(viewer, documentId) {
	
		Autodesk.Viewing.Document.load(documentId, function(doc) {
			
			var geometryItems = [];
			geometryItems = Autodesk.Viewing.Document.getSubItemsWithProperties(doc.getRootItem(), {
				'type' : 'geometry',
				'role' : '3d'
			}, true);
			
			if (geometryItems.length > 0) {
				viewer.load(doc.getViewablePath(geometryItems[0]));
			}
		}, function(errorMsg) {
			alert("Load Error: " + errorMsg);
		});
	}

});
</script>
</head>

<body>
	<div id="viewer" style="position:absolute; width:100%; height:100%;"></div>
</body>
</html>