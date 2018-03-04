import java.util.*;

//if something go wrong on raspberry pi 3 model b, use pi4j 1.2-snapshot

public class Test_MOTOR{
	public static void main(String[] args){
		/**
			motor_left{pwm0=phy32/bcm12/wiringpi26, en=phy36/bcm16/wiringpi27}
			motor_right{pwm1=phy33/bcm13/wiringpi23, en=phy37/bcm26/wiringpi25}
			servo{pwm0=phy12/bcm18/wiringpi1}
		* 
		* pwm1: phy35/bcm19/wiringpi24
		* 
		**/
		MOTOR_BTS7960_43A motor=new MOTOR_BTS7960_43A(//GPIO args
			0,//R_EN 11
			1,//RPWM 12
			2,//L_EN 13
			26,//LPWM 32
			0,//pwmLow 
			100//pwmHIGH
		);
		
		motor.init();//enable controlling
		
		Scanner in=new Scanner(System.in);
		
		while(in.hasNextInt()){
			int val=in.nextInt();
			motor.drive(val);
			System.out.println("value = "+val);
			
			try{Thread.sleep(100);} catch(Exception ex){}
		}
	}
	
}
