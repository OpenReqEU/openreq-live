package eu.openreq.exception;

public class DboException extends BaseException {
	private static final long serialVersionUID = 1L;

	@Override
    public String getMessage() {
        return "A Dbo Exception occured: " + super.getMessage();
    }
}
