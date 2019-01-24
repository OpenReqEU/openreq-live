package eu.openreq.dbo;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Data
@Embeddable
public class UserRequirementVoteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.DETACH)
    private RequirementDbo requirement;

    @ManyToOne(cascade = CascadeType.DETACH)
    private UserDbo user;

}
