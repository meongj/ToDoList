$(document).ready(function() {

    // content 글자수 체크
    $('#content').on('keyup', function() {
        var str_len = $(this).val().length;
        var str = $(this).val();
        var one_char = "";
        var rbyte = 0;
        for (var i = 0; i < str_len; i++) {
            one_char = str.charAt(i);
            if (escape(one_char).length === 6) {
                rbyte += 2; //한글2Byte
            } else {
                rbyte++; //영문 등 나머지 1Byte
            }
        }///////

        if(rbyte > 120) {
            var splitStr= sliceByByte(str, 120);
            $("#content").val(splitStr);
            alert("내용은 120자 까지만 가능합니다");
        }
    });
});

// byte 수로 자르기
function sliceByByte(str, maxByte) {

    for(b=i=0;c=str.charCodeAt(i);) {
        b+=c>>7?2:1;
        if (b > maxByte)
            break;
        i++;
    }

    return str.substring(0,i);
}

// 드롭 다운 필터링 체크
function filterEvent() {
    var complete = $('select[name="complete"]').val();
    var priority = $('select[name="priority"]').val();
    var tagContent = $("input[name='tagContent']").val();

    $.ajax({
        type: "post",
        url: "/todolist/filtering",
        data: { complete : complete, priority : priority, hashTag : tagContent }
    }).done(function (fragment) {
        $("#contentId").replaceWith(fragment);
    });
}


function checkboxChecked(index) {
        var complete = 0;
        var id = index.id;
        var check_flag = index.checked;

        if (check_flag) complete = 1;
        else complete = 0;

        var JSONObject = {'id': id, 'complete': complete};
        var jsonData = JSON.stringify(JSONObject);

        $.ajax({
            type: "post",
            url: "/todolist/completeTask",
            dataType: "json",
            async: true,
            contentType: "application/json; charset=utf-8",
            data: jsonData,
            success: function (data) {
                $("#contentArr").load("/ #contentArr");
            },
            error: function () {
                console.log("실패");
            }
        });
}


<!-- 등록 모달생성 -->
function modalShow(param) {
    if (param == 'edit') {
        $("#toDoFlag").text("등록");
        $("#completeBtn").text("등록");
        // 초기화
        $("#title").val("");
        $("#content").val("");
        $("#hashtag").val("");
        $("#start_time").val("");
        $("#end_time").val("");
        $("input[name='priorityRadio']").prop('checked', false);
    } else {
        $("#toDoFlag").text("수정");
        $("#completeBtn").text("수정");
        $("#hashtag").css("color", "#0288D1")
    }
    $('#editModal').modal('show');
}

// 초, 분 마다 체크
function timer() {
    // 1분마다 체크
    setInterval(function() {
        $("#contentArr").load("/ #contentArr");
        overdueTaskToast();
    }, 60000);
}


/*
해쉬태그 추가하기
 */
function hashTagAdd() {
    //입력태그 있는지 체크
    var hashStr = $("#hashtag").val();
    var hashSize = $("#hashtag").val().length;

    if (hashStr.endsWith("#")) {
        alert("태그를 입력해주세요");
        $("#hashtag").focus();
        return;
    }
    if (hashSize <= 45) { //45글자 제한
        if (hashStr == "") $("#hashtag").css("color", "#0288D1").val(hashStr + "#")
        else $("#hashtag").css("color", "#0288D1").val(hashStr + " #")
        $("#hashtag").focus();
    } else {
        alert("45글자까지 입력 가능합니다!");
        $("#hashtag").focus();
    }
}


/*
할일 등록/수정 하기
 */
function todoAdd() {
    var btn = $("#editBtn").text();
    var flag = (btn == "등록") ? "add" : "edit";
    var id = (btn == "등록") ? "" : $("#editId").val();
    var title = $("#title").val();
    var hashStr = $("#hashtag").val();
    var start_time = $("#start_time").val();
    var end_time = $("#end_time").val();
    var priorityRadio1 = $("#priorityRadio1").is(":checked");
    var priorityRadio2 = $("#priorityRadio2").is(":checked");
    var priorityRadio3 = $("#priorityRadio3").is(":checked");
    var priority = $('input[name="priorityRadio"]:checked').val();
    var hashtag =  $('input[name="hashtag"]').val();
    var content =  $('textarea[name="content"]').val();


    if(title == "") {
        alert("제목을 입력해주세요");
        $("#title").focus();
        return;
    }

    if (hashStr.endsWith("#")) {
        alert("태그를 입력해주세요");
        $("#hashtag").focus();
        return;
    }


    if(start_time == "") {
        alert("시작시간을 지정해주세요");
        $("#start_time").focus();
        return;
    }
    if(end_time == "") {
        alert("종료시간을 지정해주세요");
        $("#end_time").focus();
        return;
    }

    if(start_time > end_time) {
        alert("시작시간이 종료시간보다 클 수 없습니다.");
        $("#start_time").focus();
        return;
    }


    if(!(priorityRadio1 || priorityRadio2 || priorityRadio3)) {
        alert("우선순위 여부를 체크 해주세요");
        $("#priorityRadio1").focus();
        return;
    }

    if(priority == "undefined") priority = "";

    // 줄바꿈 저장
    if(content == "undefined") content = "";
    else content = $('textarea[name="content"]').val().replace(/(?:\r\n|\r|\n)/g, '<br>');

    if(hashtag == "undefined") hashtag = "";

    var JSONObject = { 'id' : id, 'title': title, 'content' : content, 'hashtag' : hashtag,
        'startTime': start_time , 'endTime' : end_time, 'priority' : priority , 'flag' : flag};
    var jsonData = JSON.stringify(JSONObject);


        $.ajax({
            type: "post",
            url: "/todolist/register",
            dataType: "json",
            async: true,
            contentType: "application/json; charset=utf-8",
            data: jsonData,
            success: function (data) {
                alert(btn+"이 완료되었습니다!");
                $("#editModal").modal("hide");
                $("#contentArr").load("/ #contentArr");
                $("#editId").val("");
            },
            error: function () {
                alert("실패");
            }
        });

}

function modalClose() { $("#editModal").modal("hide");}


// task 수정
function reviseModal(p_id, p_title, p_content, p_hashtag, p_start_time,
                     p_end_time, p_priority) {
    $("#editId").val(p_id);
    $("#title").val(p_title);

    if( p_content == "" || p_content == null || p_content == undefined || ( p_content != null && typeof p_content == "object" && !Object.keys(p_content).length ) ) {
    }else{
        // 줄바꿈 저장
        p_content = p_content.replace(/(<br>)/g, '\r\n');
        $("#content").val(p_content);
    }

    if(p_hashtag != "undefined" || p_hashtag != "" || p_hashtag != null) {
        $("#hashtag").val(p_hashtag);
    }

    $("#start_time").val(p_start_time.replace(" pm","").replace(" am",""));
    $("#end_time").val(p_end_time.replace(" pm","").replace(" am",""));
    if (p_priority == 'High') {
        $("#priorityRadio1").prop('checked', true);
    } else if (p_priority == 'Medium') {
        $("#priorityRadio2").prop('checked', true);
    } else {
        $("#priorityRadio3").prop('checked', true);
    }

    modalShow('revise');
}


// task 삭제
function deleteTask(title, id) {
    if (confirm("삭제하시겠습니까?")) {

        var JSONObject = {'title': title, 'id': id};
        var jsonData = JSON.stringify(JSONObject);
        $.ajax({
            type: "post",
            url: "/todolist/delete",
            dataType: "json",
            async: true,
            contentType: "application/json; charset=utf-8",
            data: jsonData,
            success: function (data) {
                $("#contentArr").load("/ #contentArr");
            },
            error: function () {
                alert("실패");
            }
        })
    }
}

// 종료 시간 넘은 할일 알림 메시지 toast
function overdueTaskToast() {
    $.ajax({
        url : "/todolist/overdueTask",
        method : "POST",
        dataType: "json",
        success : function (data) {
            if (data.count == 0) return;
            var toastTitle = '종료 시간이 지난 할 일이 ' + data.count + "개 있습니다." ;
            var toastContent = '';
            for (var i = 0; i < data.count-1; i++) {
                toastContent += data.titles[i] + ', </br>';
            }
            toastContent += data.titles[i];

            toastr.info(toastContent, toastTitle, {"iconClass": 'customer-info'});
            toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": true,
                "progressBar": false,
                "positionClass": "toast-top-right",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            }

        },
        error : function (error) {
            console.log("error: " + error);
        }
    });
}