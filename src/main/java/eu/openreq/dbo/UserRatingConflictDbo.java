package eu.openreq.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "or_rating_conflict")
public class UserRatingConflictDbo {

    public enum RatingType { BASIC, NORMAL, ADVANCED }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating_type", nullable=false)
    private RatingType ratingType;

    @Column(name = "time", nullable=false)
    private Date time;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_a_id", nullable=true)
    private UserDbo userA;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_b_id", nullable=true)
    private UserDbo userB;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "requirement_id", nullable=false)
    private RequirementDbo requirement;

    @Column(name = "distance", nullable=false)
    private int distance;

    @Column(name = "conflict_id", nullable=false)
    private String conflictID;

    @Column(name = "comment_a_id", nullable=true)
    private long commentAID;

    @Column(name = "comment_b_id", nullable=true)
    private long commentBID;

    @Column(name = "attribute_id", nullable=false)
    private long attributeID;

}
