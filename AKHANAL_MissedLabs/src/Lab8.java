import java.util.Scanner;

public class Lab8 {
	
	static Scanner scnr = new Scanner(System.in);
	
	public static int[] createArray() {
		System.out.println("How big do you want your Array to be?");
		int arrayLength = scnr.nextInt();
		int[] x = new int[arrayLength];
		
		for(int i = 0; i < x.length; i++ ) {
			int j = i+1;
			System.out.println("Please input integer for position: " + (j));
			int indInt = scnr.nextInt();
			x[i] = indInt;
		}
		return x;
	}
	
	public static void printArray(int[] x) {
		System.out.println("You entered:");
		System.out.print(x[0]);
		for(int i = 1; i < x.length; i++) {
			System.out.print(" " + x[i]);
		}
		System.out.println();
	}
	
	public static float arrayMean(int[] x) {
		int sum = 0;
		for(int i = 0; i < x.length; i++) {
			sum += x[i];
		}
		float sumFloat = (float) sum;
		float avg = sumFloat / (float) x.length;
		return avg;
	}
	
	public static int arraySum(int[] x) {
		int sum = 0;
		for(int i = 0; i < x.length; i++) {
			sum += x[i];
		}
		return sum;
	}
	
	public static int[] dotProd(int[] x) {
		for(int i = 0; i < x.length; i++) {
			x[i] = x[i] * x[i];
		}
		return x;
	}
	
	public static void main(String[] args) {
		int[] x = createArray();
		printArray(x);
		
		float mean = arrayMean(x);
		System.out.println("The mean of all terms is: " + mean);
		
		int[] x2 = dotProd(x);
		int dotProd = arraySum(x2);
		
		System.out.println("Dot product: " + dotProd);	
	}

}
