package com.kanven.dynamic;

public class DynamicDataSourceException extends Exception {

	private static final long serialVersionUID = 4205658793852414657L;
	
	
	public DynamicDataSourceException(){
		super();
	}
	
	public DynamicDataSourceException(String msg){
		super(msg);
	}
	
	public DynamicDataSourceException(Throwable t){
		super(t);
	}
	
	public DynamicDataSourceException(String msg,Throwable t){
		super(msg, t);
	}

}
