package eu.openreq.dbo;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Data
@Embeddable
public class UserStakeholderAttributeVoteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.DETACH)
    private RequirementDbo requirement;

    @ManyToOne(cascade = CascadeType.DETACH)
    private StakeholderRatingAttributeDbo ratingAttribute;

    @ManyToOne(cascade = CascadeType.DETACH)
    private UserDbo ratedStakeholder;

    @ManyToOne(cascade = CascadeType.DETACH)
    private UserDbo user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStakeholderAttributeVoteId that = (UserStakeholderAttributeVoteId) o;
        return Objects.equals(requirement, that.requirement) &&
                Objects.equals(ratingAttribute, that.ratingAttribute) &&
                Objects.equals(ratedStakeholder, that.ratedStakeholder) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirement, ratingAttribute, ratedStakeholder, user);
    }
}
