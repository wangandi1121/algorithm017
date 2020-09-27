package structure_practice;

import java.util.Deque;
import java.util.LinkedList;

public class deque1 {
	public static void main(String[] args) {
		Deque<Integer> res = new LinkedList<Integer>();
		res.addFirst(1);
		res.addLast(2);
		System.out.println(res);
		res.removeFirst();
		System.out.println(res);
	}
}
