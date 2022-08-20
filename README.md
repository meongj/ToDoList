## To-Do-List

기존에 사용하고 있던 할 일 리스트 중에서 내가 쓰기 더 편하게 만들 순 없을까? 해서 만든 Custom 버전의 To-do-list 웹 페이지입니다. 

![screencapture-localhost-9090-2022-08-20-14_00_26](https://user-images.githubusercontent.com/89771235/185729920-274d431c-e903-4485-b4db-8815179e938f.png)




## Skills
- 개발 언어 : Java(1.8), Javascript
- Framework : Spring boot(2.7.3 ver), Mybatis
- DBMS : MySQL
- 스타일 : Bootstrap, css
- 라이브러리 : Thymeleaf, Toastr.js
- Tool : Intellij, MySQLWorkbench, Postman


## Installation
**빌드 환경**

`jdk 1.8 , Gradle 7.4.1`

**1. Gradle 빌드 도구 설치**

Java jdk 8버전 이상 기본적으로 설치되어 있어야 합니다

Mac version (Homebrew 사용)

```
brew install gradle
```

Window version

- https://gradle.org/install/ 접속
- Installing manually > Download > v7.4.1 버전 complete 다운로드 
- gradle-7.4.1-all.zip 압축해제
- 설치하고 싶은 위치로 파일 이동
- Path 환경 변수 추가
- gradle -v 명령어로 설치 확인

**2. 프로젝트 Clone**
```
Git clone https://github.com/meongj/ToDoList.git
```

**3. 프로젝트 설치 경로로 이동 후 Gradle 로 프로젝트 빌드**


```
cd <todolist Path>/
```

Mac version

```
./gradlew build
```

Window version

```
./gradlew.bat build
```


**4. jar 파일 실행**

빌드 폴더로 이동

```
cd build/libs
```

jar 파일 실행


```
java -ver todolist-0.0.1-SNAPSHOT.jar
```


웹 브라우저에서 확인 (Chrome 실행 권장)

```
localhost:9090
```


## Preview
![812D7F6A-924C-4999-B53B-E29897E307FC_1_201_a](https://user-images.githubusercontent.com/89771235/185731465-36877687-6b5c-4f89-992a-41dfe1ecb8c0.jpeg)


<img width="1192" alt="스크린샷 2022-08-20 오후 3 12 05" src="https://user-images.githubusercontent.com/89771235/185731694-0cde65bf-6506-4fc2-82f1-817bf6b14d94.png">


<img width="818" alt="스크린샷 2022-08-20 오후 3 14 56" src="https://user-images.githubusercontent.com/89771235/185731784-266915f1-4f73-4985-a5bf-b5207d5ad3b4.png">


## Features
주요 기능 소개

**1. 할 일 추가하기**
- Add 버튼을 눌러 새로운 할 일을 추가할 수 있습니다.
- 제목, 내용(선택사항), 태그(선택사항), 시작시간, 종료시간, 우선순위를 작성 할 수 있습니다.
- '#' 버튼을 클릭하여 태그를 추가할 수 있습니다.
- 시작시간은 현재 시간이 기본입니다.
- 우선순위 레벨은 High, Medium, Low 순으로 선택할 수 있습니다.

**2. 할 일 수정하기**
- 목록에서 수정 버튼을 눌러 내용을 수정할 수 있습니다. 

**3. 할 일 삭제하기**
- 목록에서 삭제 버튼을 눌러 할 일을 삭제할 수 있습니다.

**4. 할 일 체크 완료하기**
- 완료된 일은 체크박스를 눌러 완료할 수 있습니다.
- 체크된 할 일은 줄이 그어지며 '완료된 할 일입니다.' 라고 변하며 할 일이 완료되었다는 것을 알 수 있습니다.

**5. 할 일 목록 보기**
- 오늘 날짜와 함께 남은 할 일 개수 볼 수 있습니다.
- 기본 시작시간이 빠른 순으로 자동으로 정렬되어 보여집니다.
- Select Box 버튼을 클릭하면 완료된 일, 미완료된 일 을 선택하여 Filtering 할 수 있습니다
- 시작시간이 빠른 순, 느린순 , 우선순위 높은 순, 낮은 순으로 정렬 할 수 있습니다. 
- 현재 시간부터 남은 시간이 얼마나 남았는지 볼 수 있습니다.
- 종료 시간이 지나면 '기한이 지난 할 일입니다' 라고 알려줍니다. 

**6. 태그 검색하기**
- 할 일 중에 태그를 검색하여 태그에 포함된 할 일들만 볼 수 있습니다. 


**7. 종료된 할 일 알림 메시지**
- 종료 시간이 기일했지만 완료되지 못한 할 일은 개수, 제목으로 메시지 알림을 띄워줍니다. 
