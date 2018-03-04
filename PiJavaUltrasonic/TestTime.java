public class TestTime{
	public static void main(String[] args){
		long ms=1000000;
		System.out.println("start- wait ms "+ms);
		busyWaitMicros(ms);
		System.out.println("end");
	}
	
	public static void busyWaitMicros(long micros){
        long waitUntil = System.nanoTime() + (micros * 1_000);
        while(waitUntil > System.nanoTime()){
            ;
        }
    }
}
