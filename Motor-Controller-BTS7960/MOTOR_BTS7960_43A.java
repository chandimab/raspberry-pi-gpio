/**
 * 	TelepresenceBot - MotorControlling & GPIO API
 * 	author: chandimab.github.io
 * 
 * 	pins are GPIO
 **/

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

//to control pwm pins
//pwm1 23,24 ; pwm0 1, 26
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

		/**
		 motor_left{pwm0=phy32/bcm12/wiringpi26, en=phy36/bcm16/wiringpi27}
		 motor_right{pwm1=phy33/bcm13/wiringpi23, en=phy37/bcm26/wiringpi25}
		 servo{pwm0=phy12/bcm18/wiringpi1}
		 * 
		 * pwm1: phy35/bcm19/wiringpi24
		 * 
		 **/

public class MOTOR_BTS7960_43A{
	//bcm, GPIO_#
	private int MOTOR_1_LEFT_EN,
				MOTOR_1_LEFT_PWM,
				MOTOR_1_RIGHT_EN,
				MOTOR_1_RIGHT_PWM;
	private int PWM_LOW=0,PWM_HIGH=100,PWM_GAP=100;

	private GpioController gpio;//gpio controller ; using io.gpio
	private GpioPinDigitalOutput//gpio output pins; using io.gpio, digital pins
		motor_1_left_en,
		motor_1_right_en;
	
	public MOTOR_BTS7960_43A(int R_EN, int RPWM, int L_EN, int LPWM, int pwmLow,int pwmHigh){ //GPIO
		
		this.MOTOR_1_RIGHT_EN=R_EN;
		this.MOTOR_1_RIGHT_PWM=RPWM;
		this.MOTOR_1_LEFT_EN=L_EN;
		this.MOTOR_1_LEFT_PWM=LPWM;	
		this.PWM_LOW=pwmLow; this.PWM_HIGH=pwmHigh; this.PWM_GAP=PWM_HIGH-PWM_LOW;
		
		gpio=GpioFactory.getInstance();// create gpio controller , io.gpio
		Gpio.wiringPiSetup(); //initializing wiringpi library
		
		//setup pins ; provision gpio pins
/* Motor-1*/		
		//motor_1_left_en=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "motor_1_left_en", PinState.HIGH);
		motor_1_left_en=gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(MOTOR_1_LEFT_EN), "motor_1_left_en", PinState.HIGH);//pin,tag,initial-state
		motor_1_left_en.setShutdownOptions(true, PinState.LOW);
		SoftPwm.softPwmCreate(MOTOR_1_LEFT_PWM, PWM_LOW, PWM_HIGH); // softPwmCreate(int pin, int value, int range)
		
		motor_1_right_en=gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(MOTOR_1_RIGHT_EN), "motor_1_right_en", PinState.HIGH);
		motor_1_right_en.setShutdownOptions(true, PinState.LOW);
		SoftPwm.softPwmCreate(MOTOR_1_RIGHT_PWM, PWM_LOW, PWM_HIGH); // softPwmCreate(int pin, int value, int range)
		
		
		pwm_motor_left(0);
		pwm_motor_right(0);

	}
	
	private int mapPwm(int pwm){
		return Math.abs(pwm);
	}
	
	public void init(){
		en_motor_left(true);
		en_motor_right(true);
		System.out.println("init motor");
	}
	
	public void drive(int speed){
		if(speed>0){//forward
			pwm_motor_left(0); //en_motor_left(false);
			pwm_motor_right(mapPwm(speed));
			System.out.println("forward "+speed);
		}else if(speed<0){
			pwm_motor_right(0); //en_motor_right(false);
			pwm_motor_left(mapPwm(speed));
			System.out.println("backward "+speed);
		}else{
			pwm_motor_left(0);
			pwm_motor_right(0);
			//en_motor_left(false);
			//en_motor_right(false);
			System.out.println("stop "+speed);
		}
	}
	
	public void en_motor_left(boolean state){ if(state){ motor_1_left_en.high();} else{motor_1_left_en.low();} }
	public void en_motor_right(boolean state){ if(state){ motor_1_right_en.high();} else{motor_1_right_en.low();} }
	
	public void pwm_motor_left(int pwm){ SoftPwm.softPwmWrite(MOTOR_1_LEFT_PWM, pwm); }
	public void pwm_motor_right(int pwm){ SoftPwm.softPwmWrite(MOTOR_1_RIGHT_PWM, pwm); }
	
}
