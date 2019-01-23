public class forloop2{
    public static void main(String[] args){
        double result = 0;
        for(int i=0; i<5;i++)
        {
        	for(int j=3;j<=9;j+=3)
            {
                if(i+j>10)
                {
            	   result+=i;
                }
            }
        }
        System.out.println(result);
    }
}