package eu.openreq.dbo;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "or_project_settings")
public class ProjectSettingsDbo {

    public enum EvaluationMode { BASIC, NORMAL, ADVANCED }

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @MapsId
    private ProjectDbo project;

    @Column(name = "read_only", columnDefinition = "boolean default false", nullable=false)
    private boolean readOnly;

    @Column(name = "dependency_analysis_project", columnDefinition = "boolean default false", nullable=false)
    private boolean dependencyAnalysisProject;

    @Column(name = "dependency_analysis_project_visible", columnDefinition = "boolean default false", nullable=false)
    private boolean dependencyAnalysisProjectVisible;

    @Column(name = "show_dependencies", columnDefinition = "boolean default false", nullable=false)
    private boolean showDependencies;

    @Column(name = "show_statistics", columnDefinition = "boolean default false", nullable=false)
    private boolean showStatistics;

    @Column(name = "show_social_popularity_indicator", columnDefinition = "boolean default false", nullable=false)
    private boolean showSocialPopularityIndicator;

    @Column(name = "show_stakeholder_assignment", columnDefinition = "boolean default false", nullable=false)
    private boolean showStakeholderAssignment;

    @Column(name = "show_ambiguity_analysis", columnDefinition = "boolean default false", nullable=false)
    private boolean showAmbiguityAnalysis;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "evaluation_mode", columnDefinition = "int default 0",  nullable=false)
    private EvaluationMode evaluationMode;

    @Column(name = "twitter_channel", nullable=false)
    private String twitterChannel;

}
