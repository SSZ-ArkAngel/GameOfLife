import java.util.Random;
import java.util.Scanner;

public class Lab9 {
	
	public static int[] randArray(int x) {
		int[] a = new int[x];
		for(int i = 0; i < x; i++) {
			double base =  Math.random();
			int num = (int) (base * 100);
			a[i] = num;
		}
		return(a);
	}
	
	public static void printArray(int[] a) {
		System.out.print(a[0]);
		for(int i = 1; i < a.length; i++) {
			System.out.print(" " + a[i]);
		}
		System.out.println();
	}
	
	public static void swap(int[] a, int x, int y) {
		int temp = a[x];
		a[x] = a[y];
		a[y] = temp;
	}
	
	public static void swapMin(int[] a, int x) {
		int temp;
		int min = a[x];
		int minIndex = x;
		
		for(int i = x+1; i < a.length; i++) {
			if(a[i] < min) {
				min = a[i];
				minIndex = i;
			}
		}
		temp = a[x];
		a[x] = a[minIndex];
		a[minIndex] = temp;
	}
	
	public static int[] selectionSort(int[] a) {
		for(int i = 0; i < a.length; i++) {
			swapMin(a, i);
		}
		return a;
	}
	
	public static void main(String[] args) {
		int[] x;
		Scanner scnr = new Scanner(System.in);
		System.out.println("Please enter array size:");
		int size = scnr.nextInt();
		x = randArray(size);
		System.out.println("You entered:");
		printArray(x);
		System.out.println("Sorted:");
		x = selectionSort(x);
		printArray(x);
	}

}
