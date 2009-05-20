package exception;
 
 public class MembreException extends Exception{
	private static final long serialVersionUID = -9195069281095177034L;

		public MembreException(String message){
                 super("MembreException : "+message);
         }
 }
  