package structure_practice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class topKFrequent {
	 public int[] topKFrequent(int[] nums, int k) {
		 //字典统计每个元素出现的次数
		 HashMap<Integer,Integer >map = new HashMap();
		 for(int a:nums) {
			 if(map.containsKey(a)) {
				 map.put(a,map.get(a)+1);
			 }else {
				 map.put(a,1);
			 }
		 }
		 //遍历map，最小堆储存频率最大的k个元素
		 PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
			 @Override
			 public int compare(Integer a,Integer b) {
				 return map.get(a)-map.get(b);//比较函数<0是x比y小
			 }
		 });
		 for(int key:map.keySet()) {
			 if(pq.size()<k) {
				 pq.add(key);
			 }else if(map.get(key)>map.get(pq.peek())) {
				 pq.remove();
				 pq.add(key);
			 }
		 }
		 List<Integer> res = new ArrayList<>();
	        while (!pq.isEmpty()) {
	            res.add(pq.remove());
	        }
	        int[] arr = res.stream().mapToInt(Integer::valueOf).toArray();
	        return arr;
	 }
}
