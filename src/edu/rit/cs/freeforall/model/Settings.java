package edu.rit.cs.freeforall.model;

public class Settings 
{
	private static Settings instance;
	
	private final int SLOWEST = 1000;
	private final int SLOWER  = 750;
	private final int NORMAL  = 500;
	private final int FAST    = 250;
	private final int FASTEST = 100;
	
	private final int[] speeds = { SLOWEST, SLOWER, NORMAL, FAST, FASTEST };
	
	private int currentSpeedIdx;
	
	private Settings( )
	{
		currentSpeedIdx = 2;
	}
	
	public int getSpeed( )
	{
		return speeds[ currentSpeedIdx ];
	}
	
	public void incrementSpeed( )
	{
		if( currentSpeedIdx + 1 > speeds.length )
		{
			// do nothing, at max
		}
		else
		{
			currentSpeedIdx++;
		}
	}
	
	public void decrementSpeed( )
	{
		if( currentSpeedIdx - 1 < 0 )
		{
			// do nothing, at min
		}
		else
		{
			currentSpeedIdx--;
		}
	}
	
	public static Settings Instance( )
	{
		if( instance == null )
		{
			instance = new Settings( );
		}
		return instance;
	}
}
