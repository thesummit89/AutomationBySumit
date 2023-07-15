package threadconstant;

public class TC {
	private static ThreadLocal<ThreadConstant> Setup = new ThreadLocal<ThreadConstant>();
	
   
	public static void set(ThreadConstant tc) {
		Setup.set(tc);
		
	}
	
	public static ThreadConstant get()
	{
		return Setup.get();
	}

}
