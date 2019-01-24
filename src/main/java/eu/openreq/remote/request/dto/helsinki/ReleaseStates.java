package eu.openreq.remote.request.dto.helsinki;

//TO DELETE
public enum ReleaseStates {
    NEW {
        @Override
        public String toString() {
            return "NEW";
        }
    },
    PLANNED,
    COMPLETED,
    REJECTED
}
