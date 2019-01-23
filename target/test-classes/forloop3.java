public class forloop3{
    public static void main(String[] args){
        double result = 0;
        for(int i=0; i<5;i++)
        {
        	for(int j=3;j<=9;j+=3)
            {
            	for(int k=5;k<j;k++)
            	{
                    if(i+j<k)
                    {
            		  result+=1;
                    }
            	}
            }
        }
        System.out.println(result);
    }
}