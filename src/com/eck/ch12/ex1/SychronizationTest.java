/**
 * 
 */
package com.eck.ch12.ex1;

/**
 * A program designed to see how hard it might be to get an error while using an
 * unsychronized counter with many threads. The user is able to choose
 * the number of threads and the number of increments each thread will complete.
 * After all the threads have died, the value of the counter is printed out as
 * well as what the correct count should have been for comparison.
 * 
 * @author toddnordquist
 *
 */

public class SychronizationTest {

	private static int sum; // The final

	static Counter total = new Counter(); // The counter that increments

	private static class IncrementCounterThread extends Thread {

		int increments;
		int summation = 0;

		public IncrementCounterThread(int increments) {
			this.increments = increments;
		}

		public void run() {
			for (int i = 0; i < increments; i++) {
				total.inc();

			}
			sum = total.getCount() + 1;

		}
	}

	/**
	 * Start several IncrementCounterThreads.  It prompts the user to enter
	 * a legal number of threads and a legal number of times each thread will
	 * increment the counter.  The main program then prints out the expected
	 * value and then the value of the counter. 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int numberOfThreads = 0;
		int numberOfIncrements = 0;

		while (numberOfThreads < 1 || numberOfThreads > Integer.MAX_VALUE) {
			System.out.print("How many threads do you want to use?");
			numberOfThreads = TextIO.getlnInt();
			if (numberOfThreads < 1 || numberOfThreads > Integer.MAX_VALUE) {
				System.out.println("Please choose a number 1 through "
						+ Integer.MAX_VALUE + "!");
			}
		} // numberOfThreads loop

		while (numberOfIncrements < 1 || numberOfIncrements > 1000000) {
			System.out
					.print("How many times do you want each thread to increment?");
			numberOfIncrements = TextIO.getlnInt();
			if (numberOfIncrements < 1 || numberOfIncrements > 1000000) {
				System.out.println("Please choose a number 1 through " + 10000000
						+ "!");
			}
		} // numberOfIncrements loop

		IncrementCounterThread[] worker = new IncrementCounterThread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++)
			worker[i] = new IncrementCounterThread(numberOfIncrements);
		for (int i = 0; i < numberOfThreads; i++)
			worker[i].start();

		for (int i = 0; i < numberOfThreads; i++) {
			try {
				worker[i].join(); // Wait until worker[i] finishes, if it hasn't
									// already.
			} catch (InterruptedException e) {
			}
		}
		// At this point, all the worker threads have terminated.

		int totalSum = numberOfThreads * numberOfIncrements;
		System.out.println("The count should have been " + totalSum + ".");

		System.out.println("The actual count was " + sum + " or "+total.getCount()+".");
	}

	static class Counter {
		int count;

		void inc() {
			count = count + 1;
		}

		int getCount() {
			return count;
		}
	}

} // end class SynchronizationTest
