package eu.openreq.statistics;

public class Enums {
    public enum DependencyType {
        Requires,
        Excludes
    }

    public enum DimensionType {
        None,
        Priority,
        Duration,
        Risk,
        Feasibility,
        Cost,
        Release
    }

    public enum IssueType {
        Auto,
        Manually
    }

    public enum NotificationState {
        Open,
        Closed
    }

    public enum NotificationTarget {
        Attachment,
        Chat,
        Stakeholder
    }

    public enum ReleaseState {
        New,
        Planned,
        Completed,
        Rejected
    }

    public enum RequirementState {
        New,
        Planned,
        InProgress,
        Completed,
        Rejected
    }

    public enum VotingMethod {
        Median,
        Release
    }
}
