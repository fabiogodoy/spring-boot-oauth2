package br.com.fabiogodoy.oauth2.domain;

public class Response {
	
	private String message;
	private boolean success;
	
	private Response() {}
	
	public static Response ok(final String message) {
		ResponseHolder.getIntance().message = message;
		ResponseHolder.getIntance().success = true;
		return ResponseHolder.getIntance();
	}
	
	public static Response fail(final String message) {
		ResponseHolder.getIntance().message = message;
		ResponseHolder.getIntance().success = false;
		return ResponseHolder.getIntance();
	}
	
	public boolean ok() {
		return this.success;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	private static final class ResponseHolder{
		private static final Response _INSTANCE = new Response();
		
		public static Response getIntance() {
			return _INSTANCE;
		}
	}

}
