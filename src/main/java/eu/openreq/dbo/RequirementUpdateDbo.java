package eu.openreq.dbo;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "or_requirement_update")
public class RequirementUpdateDbo {

    public enum ActionType {
        CREATED, TITLE_CHANGED, DESCRIPTION_CHANGED, STATUS_CHANGED, RELEASE_ASSIGNMENT_CHANGED,
        RELEASE_ASSIGNMENT_REMOVED, DELETED
    }

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name="requirement_id", nullable=false)
    private RequirementDbo requirement;

    @Column(name = "time", nullable=false)
    private Date time;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "action_type", nullable=false)
    private ActionType actionType;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", nullable=true)
    private UserDbo user;

    @Column(name = "previous_state_info", columnDefinition="text", nullable=true)
    private String previousStateInfo;

    @Column(name = "current_state_info", columnDefinition="text", nullable=false)
    private String currentStateInfo;

}
