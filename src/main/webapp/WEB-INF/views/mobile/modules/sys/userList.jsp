<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<section id="user_section">
    <header>
        <nav class="left">
            <a href="#" data-icon="previous" data-target="back">返回</a>
        </nav>
        <h1 class="title">停车管理</h1>
    </header>
    <article class="active">
    	<div class="indented">
	            <form id="loginForm" action="${ctx}/park/parkDoorControl/change" method="post">
	            	<div>&nbsp;</div>
	            	<div class="input-group">
		                <div class="input-row">
		                    <label for="doorControlId">门卡号</label>
		                    <input type="text" name="doorControlId" id="doorControlId" placeholder="请填写门卡号">
		                </div>
		                <div class="input-row">
		                    <label for="number">车牌号</label>
		                    <input type="text" name="number" id="number" placeholder="请填写车牌号">
		                </div>
		                <div class="input-row">
		                    <label for="isuse">停车/取车</label>
		                    <input type="text" name="isuse" id="isuse" placeholder="停车写1，取车写0">
		                </div>
		            </div>
	            	<div>&nbsp;</div>
	                <button id="btn" class="submit block" data-icon="key">提交</button>
	            </form>
	        </div>
    </article>
    <script type="text/javascript">
	$('body').delegate('#user_section','pageinit',function(){
	});
	$('body').delegate('#user_section','pageshow',function(){
        var hash = J.Util.parseHash(location.hash);
        console.log(hash.param);
	});
	</script>
</section>