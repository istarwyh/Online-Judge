import java.util.*;
public class Solution{
    public static void main(String[] args){
		Scanner input = new Scanner(System.in);
        int n,target;
    
         n = input.nextInt();
         int[] nums = new int [n];

         for(int i=0;i<n;i++){
            nums[i] = input.nextInt();
         }
        target = input.nextInt()
        int[] t =new int[2]; t = twoSum(nums, target);
        System.out.print("["+t[0]+" ");
        System.out.print(t[1]+"]");
        input.close();
    }
  	public static int[] twoSum(int[] nums, int target){
        int n =nums.length;
        Map<Integer,Integer> index = new HashMap<>();
        for (int i=0;i<n;i++){
            index.put(nums[i],i);
        }
        for(int i=0;i<n;i++){
            int another = target -nums[i];
            if(index.containsKey(another) && index.get(another)!= i)
                return new int[] {i,index.get(another)};
        }
        return new int[] {-1,-1};
    }
}
                