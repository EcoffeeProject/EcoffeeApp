#include<SoftwareSerial.h>
#include<Servo.h> //Servo 라이브러리를 추가
#include "HX711.h" //HX711로드셀 라이브러리 불러오기
#define calibration_factor -7050.0 // 로드셀 초기값을 설정해줍니다. 이렇게 해주어야 처음에 작동시에 0점을 맞추는 것이라고 생각하시면 됩니다.

//로드셀 무게센서 
#define DOUT  3 //엠프 데이터 아웃 핀 
#define CLK  2  //엠프 클락  

//블루투스 모듈 센서 
#define BT_RXD 8
#define BT_TXD 7

//워터펌프 모터
int IN1 = 12;
int IN2 = 13;

//개폐문 모터
Servo servo_door;
int ang_door = 90;

//종이컵 개폐문 모터
Servo servo_paper;
int ang_paper = 90;

//이전무게,현재무게
float before_weight;
float now_weight;

//음료무게, 음료들고나갔는지 확인하는 무게
float drink_weight, exist_weight;

SoftwareSerial bluetooth(BT_RXD, BT_TXD);
HX711 scale(DOUT, CLK); //엠프 핀 선언 

//건너오는 문자열 인식하기위한 변수
String rcvString="";
String rcvString2="";

//check시간을 재기위한..
unsigned long past = 0;  //아두이노 첫 가동시간

void setup() {

Serial.begin(9600);
bluetooth.begin(9600);

pinMode(IN1,OUTPUT);
pinMode(IN2,OUTPUT);

servo_door.attach(9);
servo_paper.attach(10);


scale.set_scale(calibration_factor);  
scale.tare();  



}


void loop() {
  unsigned long now = millis()/1000; // 현재 시간을 저장
  past = now;
  while(bluetooth.available())
  {
      unsigned long now = millis()/1000; // 현재 시간을 저장
      past = now;

    char rcvChar = (char)bluetooth.read();
    if(rcvChar=='\0')
      continue;
      
    rcvString+=rcvChar;
    delay(5);
  } 
  
  if(!rcvString.equals("")){
 

     if(rcvString[0]=='C'){ //check를 받아왔다면

            for(int i=0;i<90;i++){ //자판기 개폐문 열림
                ang_door = ang_door+5;
            if(ang_door>=180)
                ang_door = 180;
       
            servo_door.write(ang_door);
            delay(10);
            }
     scale.set_scale(calibration_factor);  
     scale.tare();      
     before_weight=scale.get_units();
     
        while(now-past <=30){ //무게 찾을때까지 계속 재는중..


        
                delay(1000);

         

 while(bluetooth.available()) // 문자열 하나 더 새로들어왔나
  {

    char rcvChar2 = (char)bluetooth.read();
    if(rcvChar2=='\0')
      continue;
      
    rcvString2+=rcvChar2;
    delay(5);
  } 

 if(rcvString2[0]=='G'){ //종이컵받기 버튼을 사용자가 눌렀을 경우
  rcvString2="";

 for(int i=0;i<90;i++){ //종이컵 개폐문 열림
        ang_paper = ang_paper+5;
        if(ang_paper>=180)
        ang_paper = 180;
       
       servo_paper.write(ang_paper);
       delay(10);
      }
delay(3000);
    scale.set_scale(calibration_factor);  
     scale.tare();      
     before_weight=scale.get_units();
  }
  

   now_weight = scale.get_units()*10;

            if(now_weight <0.0 ){
               now_weight=0.0;
             }
            if(before_weight <0.0 ){
               before_weight=0.0;
             }


            if(now_weight-before_weight>=0.7){ //뭔가 무게가 느껴지는걸 인식 
                 if(now_weight-before_weight >=3) //텀블러로 인식
                 {

                    bluetooth.println("T");
                        delay(2000);
                    for(int i=0;i<90;i++){ //자판기 개폐문 닫힘
                        ang_door = ang_door+5;
                        if(ang_door>=90)
                          ang_door = 90;
       
                            servo_door.write(ang_door);
                          delay(10);
                        }
                 
                  break;
                  }
                 
                else{ //종이컵으로 인식
          
                    bluetooth.println("P");
                         delay(2000);

 
                    for(int i=0;i<90;i++){ //종이컵 개폐문 닫힘
                        ang_paper = ang_paper+5;
                        if(ang_paper>=90)
                          ang_paper = 90;
       
                            servo_paper.write(ang_paper);
                          delay(10);
                        }
                        
                    for(int i=0;i<90;i++){ //자판기 개폐문 닫힘
                        ang_door = ang_door+5;
                        if(ang_door>=90)
                          ang_door = 90;
       
                            servo_door.write(ang_door);
                          delay(10);
                        }

                        
                    break;
                  }

            }
 before_weight = now_weight;
               now = millis()/1000;



              
        }
         //30초 끝났을때..
           for(int i=0;i<90;i++){ //자판기 개폐문 닫힘
                        ang_door = ang_door+5;
                        if(ang_door>=90)
                          ang_door = 90;
       
                            servo_door.write(ang_door);
                          delay(10);
                        }
        }


     else if(rcvString[0]=='P'){ //Pay를 받아왔다면
            bluetooth.println("R");
            
            digitalWrite(IN1,HIGH);
            digitalWrite(IN2,LOW);
            delay(20000); 
            digitalWrite(IN1,LOW);
            digitalWrite(IN2,LOW);
//음료 다 완성했을 경우

 bluetooth.println("C"); //사용자에게 다 되었다고 알린다.


drink_weight = scale.get_units()*10;//음료 무게를 잰다.
exist_weight =drink_weight;




for(int i=0;i<90;i++){ //자판기 개폐문 열림
                ang_door = ang_door+5;
            if(ang_door>=180)
                ang_door = 180;
       
            servo_door.write(ang_door);
            delay(10);
            }

            
            

            while(abs(drink_weight)>=abs(exist_weight)){ //현재 무게가 음료무게보다 작을때까지 잰다..
              exist_weight  = scale.get_units()*10;
     
         
              }
              //while문을 빠져나왔을 경우 
               delay(9000);
                    for(int i=0;i<90;i++){ //자판기 개폐문 닫힘
                        ang_door = ang_door+5;
                        if(ang_door>=90)
                          ang_door = 90;
       
                            servo_door.write(ang_door);
                          delay(10);
                        }
                         for(int i=0;i<90;i++){ //종이컵 개폐문 닫힘
                        ang_paper = ang_paper+5;
                        if(ang_paper>=90)
                          ang_paper = 90;
       
                            servo_paper.write(ang_paper);
                          delay(10);
                        }
             
     }

   rcvString="";
    }
    
  if(Serial.available()){
    char toSend=(char)Serial.read();
    bluetooth.print(toSend);
  }
}