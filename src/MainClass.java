import java.io.EOFException;


public class MainClass {
	public static void main(String[] args) throws Exception {
		RunFirstTimeClass runner1 = new RunFirstTimeClass();
		runner1.run();
		System.out.println("11111");
		while(true) {
			long startTime = 0;
			long endTime = 0;
			try {
				System.out.println("11111");
				startTime = System.currentTimeMillis();
				RunClass runner2 = new RunClass();
				runner2.start();
			} catch (Exception e){
				Thread.sleep(3*1000);
				e.printStackTrace();
			}
			Thread.sleep(3*1000);
			endTime = System.currentTimeMillis();
			if((endTime-startTime) <30*1000) {
				System.out.println(endTime-startTime + "ms");
				Thread.sleep(30*1000 - (endTime - startTime));
				//Thread.sleep(30*1000);
			}else {
				if(60*1000 - (endTime - startTime) >0) {
					Thread.sleep(60*1000 - (endTime - startTime));
				}else {
					Thread.sleep(30*1000);
				}
			}
			System.out.println("execute finish");
		}
	}
}
