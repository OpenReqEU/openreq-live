package eu.openreq.dbo;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

@Data
@Embeddable
public class BotUserStakeholderAttributeVoteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.DETACH)
    private RequirementDbo requirement;

    @ManyToOne(cascade = CascadeType.DETACH)
    private StakeholderRatingAttributeDbo ratingAttribute;

    @ManyToOne(cascade = CascadeType.DETACH)
    private UserDbo ratedStakeholder;

}
