package org.example.JavaCore.HandleException;

public class Service {
    private int a = 2;

    public void handleException(int b) {
       try{
            if (a !=b){
                throw new CustomException.NotFoundException("Not found");
            }else {
                System.out.println("true");
            }
       }catch (Exception e) {
            System.err.println("This is Exception - : " + e.getMessage());
        }

    }

}
