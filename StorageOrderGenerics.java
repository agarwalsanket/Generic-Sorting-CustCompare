/* 
 * StorageOrderGenerics.java 
 * 
 * Version: 
 *     $Id$
 * 
 * Revisions: Initial Version
 *    
 */
/**
 * Understanding Generics & Wildcards
 * StorageOrderGenerics class takes objects and sort them based on two option(Merge/Bubble),
 * and takes care of various operations on the array.
 *
 * @author Sanket Agarwal
 * 
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("rawtypes")
public class StorageOrderGenerics<T extends Comparable> {

	int filled 	= 0;
	ArrayList<T> localData;
	ArrayList<T> data;
	ArrayList<T> dataSort;
	ArrayList<T> neatData;
	static int    interatorPosition = 0;
	private static Scanner sc;

	public StorageOrderGenerics(int initialSize)	{
		data = new ArrayList<T>(initialSize);
		dataSort = new ArrayList<T>(initialSize);
		for (int index=0; index<initialSize; index++)     {
			data.add(null);
			dataSort.add(null);
		}
	}
	public void copy(ArrayList<T> to, ArrayList<T> from)	{
		for (int index=0; index< filled ; index++)     {
			to.add(from.get(index));
		}

	}
	@SuppressWarnings("unchecked")
	public void sort() {
		CompareCustGenerics c = new CompareCustGenerics();
		if ( filled == 1 )
			return;
		localData = new ArrayList<T>(filled); 
		copy(localData, dataSort);
		for (int index=0; index<localData.size() - 1; index++)     {
			for (int walker=0; walker<localData.size() - index - 1; walker++)  {
				T left = localData.get(walker);
				T right = localData.get(walker+1);
				if ( c.compare(left, right) > 0 )        {
					T tmp = localData.get(walker);
					localData.set(walker,localData.get(walker+1));
					localData.set(walker+1, tmp);

				}
			}
		}
		copy(dataSort, localData);
	}

	@SuppressWarnings("unchecked")
	public  void merge(ArrayList<T> left, ArrayList<T> right, ArrayList<T> arr,CompareCustGenerics comp) {
		//compareCustom comp = new compareCustom();
		int leftSize = left.size();
		int rightSize = right.size();
		int i = 0, j = 0, k = 0;
		while (i < leftSize && j < rightSize) {
			if (comp.compare(left.get(i), right.get(j)) > 0) {
				arr.set(k++, right.get(j++));

			} else if(comp.compare(left.get(i), right.get(j)) < 0){
				arr.set(k++, left.get(i++));

			}
			else{
				arr.set(k++, left.get(i++));
				arr.set(k++, right.get(j++));
			}
		}
		while (i < leftSize) {
			arr.set(k++, left.get(i++));

		}
		while (j < rightSize) {
			arr.set(k++, right.get(j++));

		}
	}
	public  void mergeSort(ArrayList<T> inputArray,CompareCustGenerics comp) {
		int size = inputArray.size();
		if (size < 2)
			return;
		int mid = size / 2;
		int leftSize = mid;
		int rightSize = size - mid;
		ArrayList<T>  left  = new ArrayList<T>(leftSize);
		ArrayList<T>  right = new ArrayList<T>(rightSize);
		for (int i = 0; i < mid; i++) {
			left.add(inputArray.get(i));
		}
		for (int i = mid; i < size; i++) {
			right.add(i-mid, inputArray.get(i));

		}
		mergeSort(left,comp);
		mergeSort(right,comp);
		merge(left, right, inputArray,comp);
	}
	public boolean add(T e,CompareCustGenerics comp)	{    

		for (int index = 0; index<data.size() ; index++) { // braces added
			if ( data.get(index) == null )	{
				data.set(index, e);
				filled++;
				neatData = new ArrayList<T>(filled); 
				copy(neatData, data);
				mergeSort(neatData,comp);
				return true;
			}
		}


		return false;
	}
	@SuppressWarnings("unchecked")
	public boolean remove(T e,CompareCustGenerics comp)	{
		for (int index = 0; index < filled; index++)     {
			if (  comp.compare(data.get(index), e) == 0 )	{
				data.set(index, data.get(filled-1));				
				data.set(filled-1, null);			
				filled --;
				neatData = new ArrayList<T>(filled);
				copy(neatData, data);
				mergeSort(neatData,comp);

				return true;
			}
		}
		return false;
	}
	public boolean add(T e)	{    
		for (int index = 0; index<data.size() ; index++) {
			if ( dataSort.get(index) == null )	{
				dataSort.set(index, e);
				filled++;
				sort();
				return true;
			}}

		return false;
	}
	@SuppressWarnings({ "unchecked" })
	public boolean remove(T e)	{
		CompareCustGenerics c = new CompareCustGenerics();
		for (int index = 0; index < filled; index++)     {
			if(c.compare(dataSort.get(index), e) == 0){
				data.set(index, data.get(filled-1));				
				data.set(filled-1, null);			
				filled --;
				neatData = new ArrayList<T>(filled);
				copy(neatData, data);
				sort();
				return true;
			}
		}
		return false;
	}

	public int size()	{
		return filled-1;
	}
	public void startFromBeginning() {
		interatorPosition = 0;
	}
	public boolean hasNext()	{
		return ( interatorPosition < filled );  // <= is changed to < :as the later 
		//takes the iterator to a null position.
	}
	public T next()	{

		return dataSort.get(interatorPosition++);
	}


	@SuppressWarnings({ "unchecked" })
	public static void main(String args[] )     {
		int counter = 0;
		long timeTakenMerge = 0;
		long timeTakenBubble = 0;
		boolean no_exit =true;
		while(no_exit){
			sc = new Scanner(System.in);
			CompareCustGenerics c = new CompareCustGenerics();
			System.out.println("Enter the Size of the list: ");
			int size = sc.nextInt();
			StorageOrderGenerics aStorageOrder = new StorageOrderGenerics(size);	
			System.out.println("Enter 1 to use merge sort and 2 for anything else: ");
			int decision = sc.nextInt();
			if(decision == 1){
				counter++;
				long timeStarted;
				long timeEnd;

				System.out.println("Enter 1 for  numbers to be added randomly or 2 to enter yourself ");
				int random_decision = sc.nextInt();
				
				if(random_decision == 1){
					Random rand = new Random(); 
					for(int i=0;i < 200;i++){
						int value = rand.nextInt(200);
						timeStarted = System.currentTimeMillis();
						aStorageOrder.add(value,c);
						timeEnd = System.currentTimeMillis() ;
						timeTakenMerge += (timeEnd-timeStarted);
					}

				}
				else{
					while(true){
						try{
							System.out.println("Enter the objects to be added and q to stop adding: ");
							String obj = sc.next();
							if(obj.equals("q")){
								break;
							}		

							if(obj.matches("[0-9]+")){
								timeStarted = System.currentTimeMillis();
								aStorageOrder.add(Integer.parseInt(obj),c);
								timeEnd = System.currentTimeMillis() ;
							}
							else{
								timeStarted = System.currentTimeMillis();
								aStorageOrder.add(obj,c);
								timeEnd = System.currentTimeMillis() ;
							}


							timeTakenMerge += (timeEnd-timeStarted);
						}
						catch(InputMismatchException e){
							break;					
						}

					}

				}

				//long timeEnd = (System.currentTimeMillis() - timeStarted);
				System.out.println("Enter 1 to display the sorted array: ");
				int display_decision = sc.nextInt();
				if(display_decision==1){
					System.out.println("Sorted by merge sort");
					for(int i=0;i < aStorageOrder.neatData.size(); i++){
						System.out.println(aStorageOrder.neatData.get(i));
					}
					System.out.println("Merge Sort lasts for:" + timeTakenMerge + " milliseconds");
				}
			}
			else if(decision == 2){
				counter++;
				long timeStarted;
				long timeEnd;

				System.out.println("Enter 1 for  numbers to be added randomly or 2 to enter yourself ");
				int random_decision = sc.nextInt();
				if(random_decision == 1){
					Random rand = new Random(); 
					for(int i=0;i < 200;i++){
						int value = rand.nextInt(200);
						timeStarted = System.currentTimeMillis();
						aStorageOrder.add(value);
						timeEnd = System.currentTimeMillis() ;
						timeTakenBubble += (timeEnd-timeStarted);
					}

				}
				else{
					while(true){
						try{
							System.out.println("Enter the objects to be added and q to stop adding: ");
							String obj = sc.next();
							if(obj.equals("q")){
								break;
							}	
							timeStarted = System.currentTimeMillis();
							if(obj.matches("[0-9]+")){
								timeStarted = System.currentTimeMillis();
								aStorageOrder.add(Integer.parseInt(obj));
								timeEnd = System.currentTimeMillis() ;
							}
							//aStorageOrder.add(Integer.parseInt(num));
							else{
								timeStarted = System.currentTimeMillis();
								aStorageOrder.add(obj);
								timeEnd = System.currentTimeMillis() ;
							}


							timeTakenBubble += (timeEnd-timeStarted);
						}
						catch(InputMismatchException e){
							break;					
						}

					}
				}

				//long timeEnd = (System.currentTimeMillis() - timeStarted);
				System.out.println("Enter 1 to display the sorted array: ");
				int display_decision = sc.nextInt();
				if(display_decision==1){
					System.out.println("Sorted by bubble sort");
					for(int i=0;i < aStorageOrder.filled; i++){
						System.out.println(aStorageOrder.localData.get(i));
					}
					System.out.println("Bubble Sort lasts for:" + timeTakenBubble + " milliseconds");
				}
			}
			if(counter%2 == 0){
				assert timeTakenMerge < timeTakenBubble:"you are testing for less objects"  ;
				System.out.println("Merge sort took less time than bubble sort");
			}
			System.out.println("Do you want to continue(y/n)");
			String exit = sc.next();
			if(exit.equals("n")){
				sc.close();
				System.exit(0);
			}


		}



	}
}