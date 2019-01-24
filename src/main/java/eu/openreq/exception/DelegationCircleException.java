package eu.openreq.exception;

public class DelegationCircleException extends BaseException {

    private static final long serialVersionUID = 1L;

    public DelegationCircleException() {}

    @Override
    public String getMessage() {
        return "A Delegation Circle Exception occured.";
    }

}
