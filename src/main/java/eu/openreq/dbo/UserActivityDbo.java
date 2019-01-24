package eu.openreq.dbo;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "or_user_activity")
public class UserActivityDbo {

    public enum ActionType { LOGIN }

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "action_type", nullable=false)
    private ActionType actionType;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", nullable=false)
    private UserDbo user;

    @Column(name = "time", nullable=false)
    private Date time;

    @Column(name = "ip_address", nullable=true)
    private String ipAddress;

    @Column(name = "session_id", nullable=true)
    private String sessionID;

    @Column(name = "forwarded_ip_address", nullable=true)
    private String forwardedIpAddress;

    @Column(name = "user_agent_info", nullable=true)
    private String userAgentInfo;

}
