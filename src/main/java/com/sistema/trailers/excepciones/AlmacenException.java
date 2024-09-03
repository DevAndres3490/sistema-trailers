package com.sistema.trailers.excepciones;



public class AlmacenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlmacenException(String mensaje) {
		
	}
	
	public AlmacenException(String mensaje, Throwable exception) {
		super(mensaje,exception);
	}
	
	
}
