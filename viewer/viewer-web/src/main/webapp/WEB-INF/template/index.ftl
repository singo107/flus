<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>
<link href="${static_path}/css/reset.css" rel="stylesheet" type="text/css"/>
<link href="${static_path}/css/global.css" rel="stylesheet" type="text/css"/>
<link href="${static_path}/css/webuploader.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${static_path}/js/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="http://cdn.staticfile.org/webuploader/0.1.0/webuploader.min.js"></script>
<script>
$(function () {

    var list = $('#thelist');
    var btn = $('#ctlBtn');
    var state = 'pending';
    
	var uploader = WebUploader.create({
	
	    // swf文件路径
	    swf: 'http://cdn.staticfile.org/webuploader/0.1.0/Uploader.swf',
	
	    // 文件接收服务端。
	    server: '${rc.getContextPath()}/upload',
	
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#picker',
	
	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false
	});
	
	// 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        list.append( '<div id="' + file.id + '" class="item">' +
            '<h4 class="info">' + file.name + '</h4>' +
            '<p class="state">等待上传...</p>' +
        '</div>' );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
              '<div class="progress-bar" role="progressbar" style="width: 0%">' +
              '</div>' +
            '</div>').appendTo( $li ).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');

        $percent.css( 'width', percentage * 100 + '%' );
    });

    uploader.on( 'uploadSuccess', function( file ) {
        $( '#'+file.id ).find('p.state').text('已上传');
    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });

    uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

        if ( state === 'uploading' ) {
            btn.text('暂停上传');
        } else {
            btn.text('开始上传');
        }
    });

    btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
    
});
</script>
</head>

<body>
	<div id="uploader" class="wu-example">
	    <!--用来存放文件信息-->
	    <div id="thelist" class="uploader-list"></div>
	    <div class="btns">
	        <div id="picker">选择文件</div>
	        <button id="ctlBtn" class="btn btn-default">开始上传</button>
	    </div>
	</div>
	
	<p id="msg"></p>
</body>
</html>