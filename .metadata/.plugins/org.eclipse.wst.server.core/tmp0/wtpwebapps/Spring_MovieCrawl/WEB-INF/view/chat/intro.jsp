<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="poly.util.CmmUtil" %>
<%
	String room_name = CmmUtil.nvl((String) session.getAttribute("SS_ROOM_NAME"));
%>
<!DOCTYPE html>
<html>
<head>
<script src="/js/annyang.js"></script>
<script src="/js/jquery-3.4.1.min.js"></script>
<script>
	
	$(window).on("load", function() {
		
		getRoomList();
		
		setInterval("getRoomList()", 5000);
		
	});

	function getRoomList() {
		
		$.ajax({
			url: "/chat/roomList.do",
			type: "post",
			dataType: "JSON",
			success: function(json) {
				
				var roomList = "";
				
				for (var i = 0; i < json.length; i++) {
					roomList += (json[i] + "<br>");
				}
				
				$('#room_list').html(roomList);
				
				getAllMsg();
			}
		})
	}
	
	function getAllMsg() {
		
		$.ajax({
			url: "/chat/getMsg.do",
			type: "post",
			dataType: "JSON",
			data: $("form").serialize(),
			success: function(json) {
				
				$("#view_json").html("getAllMsg : " + json);
				
				doOutputMsg(json);
			}	
		})
	}
	
	function doOutputMsg(json) {
		
		if (json != null) {
			var msgResult = "";
			
			for (var i = 0; i < json.length; i++) {
				msgResult += (json[i].msg);
				msgResult += (" | " + json[i].userNm);
				msgResult += (" | " + json[i].dateTime + "<br />");
			}
			
			$("#total_msg").html(msgResult);
		}
	}
	
	// annyang 
	annyang.start({
		autoRestart: true,
		continuous: true
	})
	
	// 음성인식 값을 받아오기 위한 객체 생성 
	var recognition = annyang.getSpeechRecognizer();
	
	// 최종 음성인식 결과값 저장 변수 
	var final_transcript = "";
	
	// 말하는 동안에 인식되는 값 가져오기(허용) 
	recognition.interimResults = false;
	
	// 음성인식 결과 가져오기 
	recognition.onresult = function(event) {
		var interim_transcript = "";
		final_transcript = "";
		
		for (var i = event.resultIndex; i < event.results.length; ++i) {
			if (event.results[i].isFinal) {
				final_transcript += event.results[i][0].transcript;
			}
		}
		$("#view_msg").html(final_transcript);
		$("#send_msg").val(final_transcript);
		
		// AJAX 호출
		$.ajax({
			url: "/chat/msg.do",
			type: "post",
			dataType: "JSON",
			data: $("form").serialize(),
			success: function(json) {
				
				$("#view_json").html("msg : " + json);
				
				doOutputMsg(json);
			}
		})
	};
</script>
</head>
<body>
	<h1>[<%=room_name%>] 채팅방 '음성대화' 전체 내용</h1>
	<hr />
	<div id="total_msg"></div>
	<h1>내가 방금 말한 전송 메시지</h1>
	<hr />
	<div id="view_msg"></div>
	<br />
	<h1>채팅방 전체 리스트</h1>
	<hr />
	<div id="room_list"></div>
	
	<!-- 음성 인식 데이터를 전송하기 위한 폼 -->
	<form name="form" method="post">
		<input type="hidden" name="send_msg" id="send_msg" />
	</form>
</body>
</html>