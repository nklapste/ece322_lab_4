public class Main {
	
	public static void main(String[] args){
		Bisect b = new Bisect(new Bisect.polynomial() {
			//Define your function here:
			public double eval(double value) {
				return value - 1;
			}
		});
		
		double result = 0;
		try {
			result = b.run(-10, 10);
			// modifying source code for easier branch coverage identification
		} catch (Bisect.RootNotFound | Bisect.MaxIterationsPassed e) {
			e.printStackTrace();
		}
		System.out.println("Root found at:" + result);
		
	}

}
