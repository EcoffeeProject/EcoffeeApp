# ECOFFEE
*환경을 바꾸는 작은 실천을 도와주는 커피머신과 주문앱 개발*
<br>
<br>
![ECOFFEE](https://github.com/EcoffeeProject/EcoffeeApp/blob/master/images/ecoffee_project.jpg)
<br>
<br>

## 🖥️ 프로젝트 소개
**무인 카페의 편리성,합리적인 가격, 멤버십혜택으로 커피한잔에 환경보호를 동참하다**
- **개발 배경**
<br>환경 보호 기사 및 관련 보도 자료조사 진행 
<br>>> ESG 경영? 지속가능한 경영 'ESG'를 중요시 여기며 기업의 상품에서 환경의 중요성이 증가됨 
<br>>> 커피로 인한 일회용품 배출량? 우리나라 커피 수요는 전세계 평균 기준 2.7배 높음! 일회용품 쓰레기 배출량 가늠 어려움. 빨대만으로 연간 약 *657톤* 추정 
<br> 사람들의 커피 수요와 환경보호를 모두 충족시킬 것을 기대할 수있는 아이디어 'ECOffee' 앱을 기획 및 제작

- **차별성 및 목표**
<br>최근 들어 무인 카페의 이용률과 성장률이 증가
<br>>> 반면 무인점포 특성상 일회용품 관리의 어려움
<br>무인카페에서도 무분별한 일회용품의 사용을 막으며 저렴한 값의 커피를 제공할 것  
<br>기계의 인지능력으로 종이컵과 텀블러 사용자를 구별
<br>>> 서로 다른 멤버십을 제공해 종이컵 사용자를 텀블러 사용자로 유입시킬 것

![프로젝트개요](https://github.com/EcoffeeProject/EcoffeeApp/blob/master/images/machine.png)

## 🕰️ 개발 기간
* 2021.09.06~2021.10.04

### ⚙️ 개발 환경
- Language : java, PHP, C
- IDE :  Android Studio, Arduino IDE
- DB IDE : PHPMyAdmin
- FTP client : FileZilla
- H/W 
    - device : ECOffee machine
    - equipment : water pump, weight Sensor(Load Cell), servo motor
    - communication : HC-06 Bluetooth
<details>
<summary>H/W Circuit Diagram</summary>

![회로도](https://github.com/EcoffeeProject/EcoffeeApp/blob/master/images/hw.jpg)

</details>

![개발환경](https://github.com/EcoffeeProject/EcoffeeApp/blob/master/images/devlop_system.jpg)

### 🧑‍🤝‍🧑 맴버 구성
 - 팀원1 : 박지연 - 에코피 앱 기능개발, 단계별 주문하기, UI 디자인,구현 🔗[github/codingGoover](https://github.com/codingGoover)
 - 팀원2 : 현명은 - 에코피 머신 제작, 아두이노 & 앱 서버담당, UI디자인  🔗[github/Wise-eun](https://github.com/Wise-eun)
 <br>

## 📌 주요 기능
![주요기능](https://github.com/EcoffeeProject/EcoffeeApp/blob/master/images/app_storyboard.png)
<br>

### **#A. ECOffee App**

#### 단계별 주문하기
<details>
<summary>이미지 보기🔎</summary>

![주문기능](https://github.com/EcoffeeProject/EcoffeeApp/blob/master/images/order_background.png)

</details>

- **각 스텝 구현 *Fragment* 활용**
    - 프래그먼트 전환 : *FragmentTransaction* 활용
    - 프래그먼트 간 값 전달 : *Bundle* 활용
      
- **STEP1 앱↔자판기 블루투스 연결**
    - 사용자 단말기 블루투스 자동 *ON* 
    - 에코피머신과 블루투스 자동연결 성공 → STEP2 진입
- **STEP2 텀블러/종이컵을 머신에 올림** 
    - 대기시간 30초 타이머 *Start*
    - 에코피머신 내부 타이머 작동 트리거 신호전송
    - 대기시간 5초 경과 → 종이컵 뽑기 버튼 *visible*
    - 대기시간 종료 → 블루투스 *disconnect* 
- **STEP3 종이컵/텀블러 사용자 구분**
    - 에코피머신 인지결과 수신 → 앱 타이머 *Shutdown* 
    - 사용자 모드 설정 *(Tumbler/Paper)*
- **STEP4 음료 종류 선택**
- **STEP5 결제 및 음료 제공**
    - 텀블러 사용자 : 텀블러 할인적용O, 보유쿠폰 사용O
    - 종이컵 사용자 : 텀블러 할인적용X, 보유쿠폰 사용X
    - 에코피머신 커피추출 트리거 신호전송
    - 커피추출 완료 → 블루투스 *disconnect*

<br>

#### 로그인, 회원가입
- 아이디 중복 확인

#### 멤버십 기능
- ECOFFEE머니 결제/충전
- 스탬프 적립
    - 음료 구매 1회시 스탬프 1개 자동 적립
    - 스탬프 적립 8개 → 아메리카노 교환권 1개 (할인쿠폰) 
- 쿠폰 발급/사용
    - 결제하기 단계시 텀블러고객만 사용 가능
<details>
<summary>이미지 보기🔎</summary>

![멤버십기능](https://github.com/EcoffeeProject/EcoffeeApp/blob/master/images/remain_background.png)

</details>
<br>

### **#B. ECOffee Machine**
#### 단계별 주문하기
<details>
<summary>흐름도 보기🔎</summary>

![단계별주문](https://github.com/EcoffeeProject/EcoffeeApp/blob/master/images/ordersystem.png)

</details>

- **STEP1 블루투스 연결**
    - 30초 내부 타이머 Start (현재시간 이용)
    - 자판기 개폐문 Open (서보모터 이용)
- **STEP2 종이컵/텀블러 음료컵 구분**
    - 로드셀 무게센서 오차율 조정
    - 종이컵 뽑기 신호 수신 → 컵 개폐문 Open
    - 무게 감지시 최초 측정무게 vs 현재값을 비교
        - 텀블러로 인식 : 결과값 전송, 자판기 문 Close 
        - 종이컵으로 인식 : 결과값 전송, 자판기&컵 문 Close
    - 내부 타이머 Shutdown → 자판기 문 Close
- **STEP3 커피 추출** 
    - 결제완료 신호 수신 → 워터펌프 작동  
    - 커피 추출 완료시 
        - 신호 전송
        - 자판기 개폐문 Open
        - 음료포함 무게 측정 
- **STEP4 사용자 행동 감지**
    - 음료포함 무게 > 현재무게 순간을 감지
    - 사용자가 음료컵 가져감
        - 무게측정 종료  
        - 자판기 개폐문 Close

<br>

✅ **블루투스 통신**
- Android Studio
    - *Bluetooth Helper API* 활용 🔗[OpenSource-github](https://github.com/BasicAirData/BluetoothHelper)
 - Android Studio ➡️ Arduino
    - 최초 무게측정 및 대기시간 시작 알림 (Check)
    - 종이컵 뽑기 메시지 (Get)  
    - 사용자 결제 완료 메세지 (Pay)
- Arduino ➡️ Android Studio
    - 종이컵(P) or 텀블러(T) 정보
    - 음료 준비중(R) 정보
    - 음료 추출완료(C) 정보

![통신구성도](https://github.com/EcoffeeProject/EcoffeeApp/blob/master/images/system.jpg)
<br>

## 🎥 시연 영상

[**[YouTube] <span style="font-size:89%">ECOffee: 환경을 바꾸기 위한 작은 실천을 돕다</span>**](https://www.youtube.com/watch?v=bfVl7gEw7QU)
<br>
[![Thumbnail](https://github.com/EcoffeeProject/EcoffeeApp/blob/master/images/Tumbnail.jpg)](https://www.youtube.com/watch?v=bfVl7gEw7QU)
<br>
<br>

<br>
<br>


## 🏆 수상 실적
**2021 IoT 경진대회 (대학혁신사업단 후원) 최우수상 수상**
<br>
[[영남대 학과소식] <span style="font-size:80%">박지연, 현명은 학생, 교내 IoT 경진대회 최우수상 수상</span>](https://www.yu.ac.kr/cse/community/news.do?mode=view&articleNo=5372809&article.offset=0&articleLimit=10)
<br>
제4회 KB국민은행 소프트웨어 경진대회 출품
<br>

