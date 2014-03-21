package cn.yuol.exception;

public class MyException extends RuntimeException {
	private static final long serialVersionUID = 499091869069687402L;

	public MyException() {
		super();
	}

	public MyException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MyException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyException(String message) {
		super(message);
	}

	public MyException(Throwable cause) {
		super(cause);
	}

}
