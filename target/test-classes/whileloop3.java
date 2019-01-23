public class whileloop3{
    public static void main(String[] args){
        double result = 0;


        double temp =0;
        while(result<10)
        {
        	while(result+temp>2)
        	{
        		if(result<temp){
        			result++;
                }

        		temp-=1;
        	}
        	result+=2;
        }




        System.out.println(result);
    }
}