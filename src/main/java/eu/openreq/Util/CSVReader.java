package eu.openreq.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {

    @Data
    @AllArgsConstructor
    public static class StudentDto {
        private String firstName;
        private String lastName;
        private String matriculationNumber;
        private String email;
        private long groupNumber;
    }

    @Data
    @AllArgsConstructor
    public static class RequirementDto {
        private String ID;
        private String description;
    }

    public static Map<Long, List<StudentDto>> parseStudents() {
        final String cvsSplitBy = ";";
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        Map<Long, List<StudentDto>> students = new HashMap<>();

        try {
            inputStreamReader = new InputStreamReader((new ClassPathResource("groups.csv")).getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            int lineNumber = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (lineNumber == 0) {
                    ++lineNumber;
                    continue;
                }

                // Gruppe;lfd.Nr.;Platz;Familien- oder Nachname;Vorname;Incoming;Matrikelnummer;Kennzahl
                // 0      1       2     3                       4       5        6              7
                // Studium;Semester im Studium;Anmeldedatum;E-Mail;Anmerkung
                // 8       9                   10           11     12
                String[] columns = line.split(cvsSplitBy);
                long groupNumber = Long.parseLong(columns[0]);
                String lastName = columns[3].trim();
                String firstName = columns[4].trim();
                String matriculationNumber = columns[6].trim();
                String email = columns[11].trim();
                StudentDto studentDto = new StudentDto(firstName, lastName, matriculationNumber, email, groupNumber);

                if (!students.containsKey(groupNumber)) {
                    students.put(groupNumber, new ArrayList<>());
                }

                List<StudentDto> studentsOfGroup = students.get(groupNumber);
                studentsOfGroup.add(studentDto);
                ++lineNumber;
            }
            return students;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return students;
        }
    }

    public static List<RequirementDto> parseRequirements() {
        final String cvsSplitBy = ";";
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        List<RequirementDto> requirements = new ArrayList<>();

        try {
            inputStreamReader = new InputStreamReader((new ClassPathResource("requirements.csv")).getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            int lineNumber = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (lineNumber == 0) {
                    ++lineNumber;
                    continue;
                }

                // id;description;isRequirement
                // 0  1           2
                String[] columns = line.split(cvsSplitBy);
                String ID = columns[0];
                String description = columns[1].trim();
                boolean isRequirement = (Integer.parseInt(columns[2].trim()) == 1);
                if (!isRequirement) {
                    continue;
                }

                RequirementDto requirementDto = new RequirementDto(ID, description);
                requirements.add(requirementDto);
                ++lineNumber;
            }
            return requirements;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return requirements;
        }
    }
}
