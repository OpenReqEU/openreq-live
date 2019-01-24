package eu.openreq.api.internal.dto;

import eu.openreq.dbo.ProjectSettingsDbo;
import lombok.Data;

@Data
public class ProjectSettingsDto {

    private boolean showDependencies;
    private boolean showStatistics;
    private boolean showSocialPopularityIndicator;
    private boolean showStakeholderAssignment;
    private boolean showAmbiguityAnalysis;
    private ProjectSettingsDbo.EvaluationMode evaluationMode;
    private String twitterChannel;

}
