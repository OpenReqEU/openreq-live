package eu.openreq.api.internal.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StakeholderVoteDto {

    private long ratedStakeholderID;
    private List<VoteDimensionDto> attributeVotes;
    private boolean anonymousUser;

    public StakeholderVoteDto() {
        this.ratedStakeholderID = 0;
        this.attributeVotes = new ArrayList<>();
        this.anonymousUser = false;
    }

}
