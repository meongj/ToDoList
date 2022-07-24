<!-- 등록 모달생성 -->
function modalShow() {
    $('#editModal').modal('show');
}

<!-- 엔터키입력시 등록하기 -->
/* function enter_check() {
     if (event.keyCode == 13) {
         alert("엔터키 누름");
         todoAdd();
         return;
     }
 }*/



/*
해쉬태그 추가하기
 */
function hashTagAdd() {
    //입력태그 있는지 체크
    var hashStr = $("#hashtag").val();
    var hashSize = $("#hashtag").val().length;
    if (hashSize <= 45) { //45글자 제한
        if (hashStr == "") $("#hashtag").css("color", "#0288D1").val(hashStr + "#")
        else $("#hashtag").css("color", "#0288D1").val(hashStr + " #")
        $("#hashtag").focus();
    } else {
        alert("45글자까지 입력 가능합니다!");
    }
}

/*
할일 등록하기
 */
function todoAdd() {
    var title = $("#title").val();
    if(title == "") {
        alert("제목을 입력해주세요");
        $("#title").focus();
        return;
    }

    var start_time = $("#start_time").val();
    var end_time = $("#end_time").val();
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

    var priorityRadio1 = $("#priorityRadio1").is(":checked");
    var priorityRadio2 = $("#priorityRadio2").is(":checked");
    var priorityRadio3 = $("#priorityRadio3").is(":checked");

    if(!(priorityRadio1 || priorityRadio2 || priorityRadio3)) {
        alert("우선순위 여부를 체크 해주세요");
        $("#priorityRadio1").focus();
        return;
    }
    var priority = $('input[name="priorityRadio"]:checked').val();
    if(priority == "undefined") priority = "";
    var content =  $('textarea[name="content"]').val();
    if(content == "undefined") {
        content = "";
    }
    else {
        console.log("content="+content);
        // 줄바꿈 저장
        content = $('textarea[name="content"]').val().replace(/(?:\r\n|\r|\n)/g, '<br>');
    }
    var hashtag =  $('input[name="hashtag"]').val();
    if(hashtag == "undefined") hashtag = "";



    var JSONObject = { 'title': title, 'content' : content, 'hashtag' : hashtag,
        'startTime': start_time , 'endTime' : end_time, 'priority' : priority };
    var jsonData = JSON.stringify(JSONObject);

    // console.log("title="+title +"/ content="+content+ "/ hashtag="+hashtag+"/" +
    //     "start_time="+start_time+"/ end_time="+end_time+"/ priority="+priority);
        $.ajax({
            type: "post",
            url: "/todolist/register",
            dataType: "json",
            async: true,
            contentType: "application/json; charset=utf-8",
            data: jsonData,
            success: function (data) {
                alert("등록이 완료되었습니다!");
                $("#editModal").modal("hide");
            },
            error: function () {
                alert("실패");
            }
        });

}

function modalClose() { $("#editModal").modal("hide");}
