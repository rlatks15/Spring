$(document).ready(function(){
	let idcheck_value = '';		//id 중복검사시 값
	
	//회원가입 버튼 클릭할때 이벤트 처리 부분
	$("form").submit(function(){
		
	//$.trim(문자열)는 문자열의 앞, 뒤 공백을 제거합니다.
	const id = $("#id");
	if ($.trim(id.val())==""){
		alert("ID를 입력 하세요");
		id.focus();
		return false;
	}
	
	if(!id.prop('readOnly')){ //회원가입 폼과 정보 수정 폼에서 동시에 사용할 js입니다.
							  //회원가입 폼에서만 사용할 문장들 입니다.
							  //정보 수정 폼에서는 아이디를 수정하지 않기 때문에 필요 없는 부분입니다.		
		console.log(id.prop('readOnly'))
		const submit_id_value=$.trim(id.val())
		if(submit_id_value != incheck_value){//submit 당시 아이디값과 아이디 중복검사에 사용된 아이디를 비교합니다.
			alert("ID 중복검사를 하세요");
			$("#opener_message").text('');
			return false;
		}
		
		//아이디 중복 검사를 했지만 사용중인 아이디인 경우에는 submit시 경고장 나타납니다.
		const result=$("#result").val();
		if(result == '-1'){
			alert("사용 가능한 아이디로 다시 입력하세요");
			id.val('').focus();
			$("#opener_message").text('');
			return false;
		}
	}//if(!id.prop('readOnly'))
	
	const pass = $("#pass");
	if ($.trim(pass.val())==""){
		alert("비밀번호를 입력 하세요");
		pass.focus();
		return false;
	}
	
	
	const jumin1 = $("#jumin1");
	if ($.trim(jumin1.val())==""){
		alert("주민번호 앞자리를 입력하세요");
		jumin1.focus();
		return false;
	}
	
	if ($.trim(jumin1.val()).length != 6){
		alert("주민번호 앞자리를 6자리를 입력하세요");
		jumin1.val("").focus();
		return false;
	}
	
	// 주민번호 뒷자리 공백 유효성 검사
	const jumin2 = $("#jumin2");
	if ($.trim(jumin2.val())==""){
		alert("주민번호 뒷자리를 입력하세요");
		jumin2.focus();
		return false;
	}
	

	if ($.trim(jumin2.val()).length != 7){
		alert("주민번호 뒷자리를 7자리를 입력하세요");
		jumin2.val("").focus();
		return false;
	}
	

	const email = $("#email");
	if ($.trim(email.val())==""){
		alert("이메일을 입력 하세요");
		email.focus();
		return false;
	}
	
	 
    const domain = $("#domain");
	if ($.trim(domain.val())==""){
        alert("domain을 입력하세요");
        domain.focus();
        return false;
    }
    
	let cnt = $('input:radio:checked').length;
	if (cnt == 0){
		alert("성별을 선택하세요");
		return false;
	}
	
	cnt = $('input:checkbox:checked').length;
	if (cnt < 2){
		alert("취미는 2개 이상 선택하세요");
		return false;
	}
   
	const post1 = $("#post1");
	if ($.trim(post1.val())==""){
		alert("우편번호를 입력 하세요");
		post1.focus();
		return false;
	}
	
	if(!$.isNumeric(post1.val())){
		alert("우편번호는 숫자만 입력 가능 합니다.");
		post1.val("").focus();
		return false;
	}
	
	const address = $("#address");
	if ($.trim(address.val())==""){
		alert("주소를 입력 하세요");
		address.focus();
		return false;
	}
	
		const intro = $("#intro");
	if ($.trim(intro.val())==""){
		alert("자기소개를 입력 하세요");
		intro.focus();
		return false;
	}
});//submit() end

	$("#idcheck").click(function(){
		
		const id = $("#id");
		//$.trim(문자열)는 문자열의 앞, 뒤 공백을 제거합니다.
		const id_value = $.trim(id.val());
		if(id_value ==""){
			alert("ID를 입력 하세요");
			id.focus();
			return false;
		}else{
			// 첫글자는 대문자이고 두번째부터는 대소문자, 숫자, _로 총 4개 이상
			pattern = /^[A-Z][a-zA-Z0-9]{3,19}$/;
			if (pattern.test(id_value)){
				idcheck_value = id_value;
			const ref = "idcheck.net?id=" + id_value;
			window.open(ref,"idcheck","width=350,height=200");
		}else{
			alert("첫글자는 대문자이고 두번째부터는 대소문자, 숫자, _로 총 4~20자 입니다.");
			id.val('').focus();
		}
	}
});//$("#idcheck").click
	
// 우편검색 버튼 클릭
$("#postcode").click(function(){
	
	Postcode();
	
});//$("#postcode").click

	 function Postcode() {
        new daum.Postcode({
            oncomplete: function(data) {
            	console.log(data.zonecode)
              
                var fullRoadAddr = data.roadAddress; 
                var extraRoadAddr = ''; 

                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }

                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }

                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }
                
                if(fullRoadAddr !== ''){
                    fullRoadAddr += extraRoadAddr;
                }
 
				$('#post1').val(data.zonecode);//5자리 새 우편번호 사용
				$('#address').val(fullRoadAddr);
            }
        }).open();
    }//function Postcode()

// 도메인 선택 부분
$("#sel").change(function(){
	const domain = $("#domain");
	if ($(this).val() == ""){//직접입력 선택한 경우
		domain.val("").focus();
		domain.prop("readOnly", false);
	}else{	//도메인 선택한 경우
		domain.val($(this).val());
		domain.prop("readOnly", true);
	}
});//$("#sel").change()

$('#jumin1').keyup(function(){
	const jumin1Value = $(this).val();
	if($.trim(jumin1Value).length == 6){
		const pattern = /^[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])$/;
		if (pattern.test(jumin1Value)){
			$("#jumin2").focus();// 주민번호 뒷자리로 포커스 이동
		} else{
			alert("숫자 또는 형식에 맞게 입력하세요[yymmdd]");
			$(this).val('').focus();
		}
	}
});//$("#jumin1").keyup()

$("#jumin2").keyup(function(){
	const jumin2Value = $(this).val();
	if ($.trim(jumin2Value).length == 7){
		const pattern = /^[1234][0-9]{6}$/;
		if(pattern.test(jumin2Value)){
			const c = Number(jumin2Value.substring(0, 1));
			const index = (c - 1) % 2; // c=1,3이면 index=0
									   // c=2,4이면 index=1
			$("input[type=radio]").eq(index)
								  .prop("checked", true);
		}else{
			alert("숫자 또는 형식에 맞게 입력하세요")
			$(this).val('').focus();
		}
	}
});//$("#jumin2").keyup()
});