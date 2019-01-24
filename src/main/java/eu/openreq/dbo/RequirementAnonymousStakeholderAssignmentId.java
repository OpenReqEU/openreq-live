package eu.openreq.dbo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class RequirementAnonymousStakeholderAssignmentId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.DETACH)
    private RequirementDbo requirement;

    @ManyToOne(cascade = CascadeType.DETACH)
    private AnonymousUserDbo anonymousStakeholder;

    public RequirementDbo getRequirement() {
        return requirement;
    }

    public void setRequirement(RequirementDbo requirement) {
        this.requirement = requirement;
    }

    public AnonymousUserDbo getAnonymousStakeholder() {
        return anonymousStakeholder;
    }

    public void setAnonymousStakeholder(AnonymousUserDbo anonymousStakeholder) {
        this.anonymousStakeholder = anonymousStakeholder;
    }
}
