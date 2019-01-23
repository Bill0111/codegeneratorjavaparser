public class whileloop2{
    public static void main(String[] args){
        double result = 0;
        
        while(result<10){
            result+=3;
        }

        double temp = 0;

        while(result>temp){
            if(temp+result==10)
            {
                break;
            }
        	result--;
            temp++;
        }



        System.out.println(result);
    }
}