package eu.openreq.remote.request.dto.stakeholderrecommendation;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BatchProcessDto {

    private List<PersonDto> persons = new ArrayList<>();
    private List<ProjectDto> projects = new ArrayList<>();
    private List<RequirementDto> requirements = new ArrayList<>();
    private List<ResponsibleDto> responsibles = new ArrayList<>();

    public void addPerson(PersonDto person) {
        persons.add(person);
    }

    public void addProject(ProjectDto project) {
        projects.add(project);
    }

    public void addRequirement(RequirementDto requirement) {
        requirements.add(requirement);
    }

    public void addResponsible(ResponsibleDto responsible) {
        responsibles.add(responsible);
    }

}
