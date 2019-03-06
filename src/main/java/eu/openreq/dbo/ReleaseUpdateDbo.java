package eu.openreq.dbo;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "or_release_update")
public class ReleaseUpdateDbo {

    public enum ActionType { NAME_CHANGED, DESCRIPTION_CHANGED, STATUS_CHANGED, DEADLINE_CHANGED, DELETED, CAPACITY_CHANGED }

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name="release_id", nullable=false)
    private ReleaseDbo release;

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
