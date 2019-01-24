package eu.openreq.remote.request.dto.checkconsistency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstraintDto {

    public enum Type {
        EQUAL, NOT_EQUAL, OR, AT_LEAST_ONE, AT_LEASTONE_A, EXCLUDES, AT_MOST_ONE_A,
        LOWER_THAN, LOWER_EQUAL_THAN, GREATER_THAN, GREATER_EQUAL_THAN, VALUE_DEPENDENCY,
        FIXED, NO_LATER_THAN, NOT_EARLIER_THAN, SUM
    }

    private Type type;
    private Long rx;
    private Long ry;
    private Map<String, Long> meta;

}
