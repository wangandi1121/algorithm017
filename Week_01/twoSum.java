package structure_practice;

import java.util.HashMap;
import java.util.Map;

public class twoSum {
	 public int[] twoSum(int[] nums, int target) throws Exception {
		 Map <Integer,Integer> map = new HashMap<>();
		 for(int i = 0;i<nums.length;i++) {
			 
			 if(map.containsKey(target-nums[i])) {
				int res1 =  map.get(target-nums[i]);
				return new int[] {res1,i};
			 }
			 map.put(nums[i], i);
		 }
		 throw new Exception("lala");
	 }

}
