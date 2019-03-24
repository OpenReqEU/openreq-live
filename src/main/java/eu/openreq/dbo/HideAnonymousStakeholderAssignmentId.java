package eu.openreq.dbo;

import lombok.Data;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class HideAnonymousStakeholderAssignmentId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.DETACH)
    private RequirementDbo requirement;

    @ManyToOne(cascade = CascadeType.DETACH)
    private UserDbo user;

    @ManyToOne(cascade = CascadeType.DETACH)
    private AnonymousUserDbo hiddenAnonymousStakeholder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HideAnonymousStakeholderAssignmentId that = (HideAnonymousStakeholderAssignmentId) o;
        return Objects.equals(requirement, that.requirement) &&
                Objects.equals(user, that.user) &&
                Objects.equals(hiddenAnonymousStakeholder, that.hiddenAnonymousStakeholder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirement, user, hiddenAnonymousStakeholder);
    }

}
