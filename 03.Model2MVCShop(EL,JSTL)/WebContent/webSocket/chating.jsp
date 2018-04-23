<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>
<!DOCTYPE>
<html>
<head>
	<meta charset="UTF-8">
    <title>Testing websockets</title>
</head>
<body>
    <fieldset>
        <textarea id="messageWindow" rows="10" cols="50" readonly="true"></textarea>
        <br/>
        <input id="inputMessage" type="text" onkeypress="if(event.keyCode == 13){ send()};"/>
        <input type="submit" value="전송" onclick="send()" />
    </fieldset>
</body>
    <script type="text/javascript">
        var textarea = document.getElementById("messageWindow");
        var serverIP = "ws://"+"${serverIP}"+":8080/chating";
        var webSocket = new WebSocket(serverIP);
        var inputMessage = document.getElementById('inputMessage');
    webSocket.onerror = function(event) {
      onError(event)
    };
    webSocket.onopen = function(event) {
      onOpen(event)
    };
    webSocket.onmessage = function(event) {
      onMessage(event)
    };
    function onMessage(event) {
    	if('${user.role}'=="admin"){
    		textarea.value +=  "user: " + event.data + "\n";
    	}else{
    		textarea.value +=  "운영자: " + event.data + "\n";	
    	}
        
        
        
    }
    function onOpen(event) {
    	if('${user.userId}'!=null){
        textarea.value += "연결되었습니다.\n";
    	}else{
    	textarea.value += "로그인시 이용가능한기능입니다.\n";
    	}
    }
    function onError(event) {
      alert(event.data);
    }
    function send() {
        textarea.value += '${user.userId}'+" : " + inputMessage.value + "\n";
        webSocket.send(inputMessage.value);
        inputMessage.value = "";
    }
  </script>
</html>