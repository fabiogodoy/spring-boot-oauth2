package br.com.fabiogodoy.oauth2.domain;

/**
 * A simple wrapper to provide standard responses to any client
 * @author fabio.godoy
 */
public class Response {
	
	private String[] message;
	private boolean success;
	private Object data;
	
	private Response() {}
	
	public static Response ok(final Object data, final String... message) {
		return ResponseHolder.getIntance(true, data, message);
	}
	
	public static Response fail(final Object data, final String... message) {
		return ResponseHolder.getIntance(false, data, message);
	}
	
	public String[] getMessage() {
		return this.message;
	}
	
	public boolean getSuccess() {
		return this.success;
	}
	
	public Object getData() {
		return this.data;
	}
	
	private static final class ResponseHolder{
		private static final Response _INSTANCE = new Response();
		
		public static Response getIntance(final boolean success, final Object data, final String... message) {
			_INSTANCE.message = message;
			_INSTANCE.success = success;
			_INSTANCE.data = data;
			return _INSTANCE;
		}
	}

}
