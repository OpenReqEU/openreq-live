package eu.openreq.dbo;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "or_requirement_vote_activity")
public class UserRequirementVoteActivityDbo {

    public enum ActionType { CREATED, UPDATED, DELETED }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "requirement_id", nullable=false)
    private long requirementID;

    @Column(name = "attribute_id", nullable=false)
    private long attributeID;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "evaluation_mode", nullable=false)
    private ProjectSettingsDbo.EvaluationMode evaluationMode;

    @Column(name = "time", nullable=false)
    private Date time;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "action_type", nullable=false)
    private ActionType actionType;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", nullable=true)
    private UserDbo user;

    @Column(name = "state_info", columnDefinition="text", nullable=true)
    private String stateInfo;

}
