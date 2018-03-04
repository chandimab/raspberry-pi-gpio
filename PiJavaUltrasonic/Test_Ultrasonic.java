
// in testing i got great accuracy

public class Test_Ultrasonic{
	public static void main(String[] args) throws Exception{
		PiJavaUltrasonic sonic=new PiJavaUltrasonic(
			0,//ECO 11
			1,//TRIG 22
			1000,//REJECTION_START ; long
			23529411 //REJECTION_TIME ; long
		);
		System.out.println("Start");
		while(true){
			System.out.println("distance "+sonic.getDistance()+"mm");
			Thread.sleep(1000); //1s
		}
	}
	
}
