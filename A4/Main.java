/* Name: Rebecca Lassman
 * File: Main.java
 * Desc:
 *
 * The main driver program for Assignment 4.
 *
 * This program creates an ArrayDeque and TwoStacksQueue and
 * tests their methods.
 *
 */

public class Main {

      public static void main(String[] args) {

	  ArrayDeque<Integer> dq = new ArrayDeque<>(7);
	  
	  System.out.println(dq.size());
	  System.out.println(dq.isEmpty());
	  System.out.println(dq.last());
	  System.out.println(dq.removeLast());
	  dq.addLast(-1);
	  dq.addLast(1);
	  dq.addLast(2);
	  dq.addFirst(3);
	  dq.addLast(4);
	  dq.addFirst(7);
	  dq.addLast(8);
	  System.out.println(dq.removeLast());
	  System.out.println(dq.first());
	  System.out.println(dq.last());
	  System.out.println(dq.isEmpty());
	  System.out.println(dq);
	  System.out.println(dq.size());
      	  System.out.println(dq.removeFirst());
	  System.out.println(dq);
	  System.out.println(dq.size());
	  System.out.println(dq.removeFirst());
	  System.out.println(dq.removeLast());
	  System.out.println(dq.removeFirst());
	  System.out.println(dq.removeLast());
	  System.out.println(dq.removeLast());
	  System.out.println(dq);
	  System.out.println(dq.size());
	  System.out.println(dq.isEmpty());
	  System.out.println(dq.first());
	  System.out.println(dq.last());
	  dq.addFirst(0);
	  dq.addFirst(1);
	  dq.addFirst(12);
	  dq.addLast(3);
	  System.out.println(dq);
	  System.out.println(dq.size());
	  System.out.println(dq.isEmpty());
	  System.out.println(dq.first()+"\n");

	  
	  TwoStacksQueue<Integer> q = new TwoStacksQueue<>();
	
	  System.out.println(q.size());
	  System.out.println(q.isEmpty());
	  q.enqueue(67);
	  q.enqueue(1);
	  q.enqueue(2);
	  q.enqueue(3);
	  q.enqueue(4);
	  System.out.println(q.first());
	  System.out.println(q);
	  System.out.println(q.size());
	  System.out.println(q.isEmpty());
	  System.out.println(q.dequeue());
	  System.out.println(q.first());
	  System.out.println(q);
	  System.out.println(q.size());
	  System.out.println(q.isEmpty());
	  System.out.println(q.dequeue());
	  System.out.println(q.dequeue());
	  System.out.println(q.dequeue());
	  System.out.println(q.dequeue());
	  System.out.println(q);
	  System.out.println(q.dequeue());
	  System.out.println(q.first());
	  q.enqueue(11);
	  System.out.println(q);
	  System.out.println(q.first());
    }
}
