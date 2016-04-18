<%--
  Created by IntelliJ IDEA.
  User: butioy
  Date: 2016/4/8
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/admin/snippet/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>INSPINIA | Login Page</title>

    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css" rel="stylesheet">

</head>
<body class="gray-bg">
<div class="loginColumns animated fadeInDown">
    <div class="row">
        <div class="col-md-6">
            <h2 class="font-bold">Welcome to IN+</h2>
            <p>
                设计完美和精确的准备管理主题以及超过50页的扩展新web应用视图
            </p>
            <p>
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
            </p>
            <p>
                When an unknown printer took a galley of type and scrambled it to make a type specimen book.
            </p>
            <p>
                <small>It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.</small>
            </p>
        </div>
        <div class="col-md-6">
            <div class="ibox-content">
                <form class="m-t" id="login-form" role="form">
                    <p class="text-muted text-center" style="color: orangered;">
                        <small id="error_msg"></small>
                    </p>
                    <div class="form-group">
                        <input type="text" name="account" class="form-control" required="true" id="account" placeholder="账号" >
                    </div>
                    <div class="form-group">
                        <input type="text" name="password" class="form-control" required="true" id="password" placeholder="密码">
                    </div>
                    <button type="submit" class="btn btn-primary block full-width m-b">登录</button>

                    <a href="#">
                        <small>忘记密码？</small>
                    </a>

                    <a href="#" class="pull-right">
                        <small>注册账号</small>
                    </a>

                    <%--<p class="text-muted text-center">
                        <small>Do not have an account?</small>
                    </p>
                    <a class="btn btn-sm btn-white btn-block" href="register.html">注册账号</a>--%>
                </form>
                <p class="m-t">
                    <small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small>
                </p>
            </div>
        </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-md-6">
            Copyright Example Company
        </div>
        <div class="col-md-6 text-right">
            <small>© 2014-2015</small>
        </div>
    </div>
</div>

<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
<!-- Jquery Validate -->
<script src="${ctx}/static/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}/static/js/plugins/validate/localization/messages_zh.min.js"></script>
<script src="${ctx}/static/js/plugins/jquery-form/jquery.form.min.js"></script>
<script>
    (function($) {
        $(document).ready(function(){
            $("#login-form").validate({
                submitHandler: function(form) {
                    $(form).ajaxSubmit({
                        url:'${ctx}/admin/doLogin',
                        type: 'post',
                        dataType:'json',
                        clearForm:true,
                        resetForm:true,
                        timeout:3000,
                        success: function( resp, statusText, xhr, $form ) {
                            $("#error_msg").html(resp.message);
                            if( resp.status == "success" ) {
                                window.location = "${ctx}/admin/index";
                            }
                        }
                    });
                },
                errorPlacement: function(error, element ) {
                    error.insertAfter( element.parent() );
                },
                highlight: function(element, errorClass) {
                    $(element).addClass(errorClass).parent().prev().children("select").addClass(errorClass);
                }
            });
        });
        $("#password").on("focus", function() {
            if( !($(this).is(":password")) ) {
                $(this).attr("type", "password");
            }
        });
    }(jQuery));
</script>
</body>

</html>
