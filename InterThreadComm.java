class SynchronizedMethods
{
	private int d;
	private boolean flag=false;
	public synchronized int getData()
	{
		while(flag==false)
		{
		try{
			wait();
		}
		catch(InterruptedException e)
		{
		System.out.println("exception caught");
		}
		}
		System.out.println("got data:"+d);
		flag=false;
		notifyAll();
		return d;
	}
	
	public synchronized void putData(int d)
	{
		while(flag==true)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
				System.out.println("exception caught");
			}
		}
		this.d=d;
		flag=true;
		System.out.println("put data with value:"+d);
		notifyAll();
	}
}
class Producer extends Thread
{
	private SynchronizedMethods t;

	public Producer(SynchronizedMethods t)
	{
		this.t=t;
		
		
	}
	public void run()
	{
		for(int i=0;i<10;i++)
		{
			
			t.putData(i);
			
		}
	}
}
class Consumer extends Thread
{
	private SynchronizedMethods t;

	public Consumer(SynchronizedMethods t)
	{
		this.t=t;
	
		
	}
	public void run()
	{
		
		
		for(int i=0;i<10;i++)
		{
			
			t.getData();
		}
	}
}

public class InterThreadComm
{
	public static void main(String args[])
	{
		SynchronizedMethods obj1=new SynchronizedMethods();
		Producer p=new Producer(obj1);
		Consumer c=new Consumer(obj1);
		System.out.println("Put called by Producer");
		p.start();
		System.out.println("Get called by Consumer");
		c.start();
		
		
	}
}
