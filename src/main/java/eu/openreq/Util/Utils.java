package eu.openreq.Util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import eu.openreq.UserInfo;
import eu.openreq.dbo.*;
import eu.openreq.service.EmailService;
import eu.openreq.service.UserServiceI;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import eu.openreq.dbo.ReleaseDbo.Status;
import eu.openreq.repository.ProjectRepository;
import eu.openreq.repository.ReleaseRepository;
import eu.openreq.repository.RequirementRepository;
import eu.openreq.repository.SkillRepository;
import eu.openreq.repository.UserRepository;
import eu.openreq.service.UserService;
import javax.servlet.http.HttpServletRequest;

public class Utils {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean isDescending) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        if (isDescending) {
            Collections.reverse(list);
        }

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static List<String> extractLinks(String text) {
		List<String> links = new ArrayList<>();
		String regex = "\\(?\\b(http://|https://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while(m.find()) {
			String urlStr = m.group();
			if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
				urlStr = urlStr.substring(1, urlStr.length() - 1);
			}
			links.add(urlStr);
		}
		return links;
	}

	public static String removeURL(String text) {
    	return text.replaceAll("http.*?\\s", " ");
	}

	public static long binomial(int n, int k) {
		if (k > n - k) {
			k = n - k;
		}

		long b = 1;
		for (int i = 1, m = n; i <= k; i++, m--) {
			b = b * m / i;
		}
		return b;
	}

	public static String generateRandomKey(int length) {
		String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
		RandomString tickets = new RandomString(length, new SecureRandom(), easy);
		return tickets.nextString();
	}

	public static String generateRandomProjectKey(ProjectRepository projectRepository) {
		int keyLength = 8;
		return generateRandomProjectKey(projectRepository, keyLength);
	}

	public static String generateRandomProjectKey(ProjectRepository projectRepository, int keyLength) {
		String uniqueProjectKey = generateRandomKey(keyLength);
		while (projectRepository.findOneByUniqueKey(uniqueProjectKey) != null) {
			uniqueProjectKey = generateRandomKey(keyLength);
		}
		return uniqueProjectKey;
	}

	public static String generateRandomResetPasswordKey() {
		return generateRandomKey(12);
	}

	public static String generateRandomPassword() {
		return generateRandomKey(10);
	}

	public static void generateDefaultRatingSchemeAndAddToProject(ProjectDbo project) {
		final RatingAttributeDbo profitAttribute = new RatingAttributeDbo("Profit", "Profit of the mentioned requirement", "monetization_on", 1, 10, 1, 1.0f, false, project);
		final RatingAttributeDbo riskAttribute = new RatingAttributeDbo("Risk", "Taken risk for developing the mentioned requirement", "report_problem", 1, 10, 1, 1.0f, true, project);
		final RatingAttributeDbo effortAttribute = new RatingAttributeDbo("Effort", "Incurred costs for developing the mentioned requirement", "build", 1, 10, 1, 1.0f, true, project);
		final StakeholderRatingAttributeDbo appropriatenessAttribute = new StakeholderRatingAttributeDbo("Appropriateness", "Degree of appropriateness of the stakeholder for the mentioned requirement", "fitness_center", 1, 10, 0.5f, project);
		final StakeholderRatingAttributeDbo availabilityAttribute = new StakeholderRatingAttributeDbo("Availability", "Availability of the stakeholder for the mentioned requirement", "access_time", 1, 10, 0.5f, project);

		if ((project.getRatingAttributes() == null) || project.getRatingAttributes().size() == 0) {
			project.addRatingAttribute(profitAttribute);
			project.addRatingAttribute(riskAttribute);
			project.addRatingAttribute(effortAttribute);
		}

		if ((project.getStakeholderRatingAttributes() == null) || project.getStakeholderRatingAttributes().size() == 0) {
            project.addStakeholderRatingAttribute(appropriatenessAttribute);
            project.addStakeholderRatingAttribute(availabilityAttribute);
		}
	}

	public static String getShortFullName(String firstName, String lastName, int maxLength) {
		String fullName = firstName + " " + lastName;
		if (fullName.length() <= maxLength) {
			return fullName;
		}

		String shortenizedFullName = firstName + " " + lastName.charAt(0) + ".";
		if (shortenizedFullName.length() <= maxLength) {
			return shortenizedFullName;
		}

		shortenizedFullName = firstName.substring(0, (maxLength - 5)) + ". " + lastName.charAt(0) + ".";
		return shortenizedFullName;
	}

	public static UserDbo createSuperuser(String username, String hashedPassword, String firstName,
                                          String lastName, String email, UserRepository userRepository) {
		UserDbo superUser = userRepository.findOneByUsername(username);
		final RoleDbo adminRole = new RoleDbo(RoleDbo.Role.ROLE_ADMIN);
		final HashSet<RoleDbo> roles = new HashSet<>();
		roles.add(adminRole);

		if (superUser == null) {
			superUser = new UserDbo(firstName, lastName, username, email, hashedPassword);
			superUser.setRoles(roles);
			superUser.setEnabled(true);
			userRepository.save(superUser);
		}
		return superUser;
	}

	public static UserDbo getCurrentUser(Authentication authentication, UserRepository userRepository) {
		UserDbo currentUser = null;
		if ((authentication != null) && authentication.isAuthenticated()) {
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			currentUser = userRepository.findOneByUsername(userInfo.getUsername());
		}
		return currentUser;
	}

	public static String generateErrorMessageAndSendEmail(HttpServletRequest request, UserDbo user, EmailService emailService) {
    	String clientIpAddress = request.getRemoteAddr();
		String forwardedIpAddress = request.getHeader("X-FORWARDED-FOR");
		String userAgentInfo = request.getHeader("User-Agent");

		String header = "<ul>";
        header += "<li><b>IP Address</b> " + clientIpAddress + "</li>";
        header += "<li><b>Forwarded IP Address:</b> " + forwardedIpAddress + "</li>";
        header += "<li><b>User Agent:</b> " + userAgentInfo + "</li>";
        header += "<li><b>Method:</b> " + request.getMethod() + "</li>";
        header += "<li><b>URL:</b> " + request.getRequestURL() + "</li>";
        header += "<li><b>Session ID:</b> " + request.getRequestedSessionId() + "</li>";
        header += "<li><b>Query String:</b> " + request.getQueryString() + "</li>";
        String referer = null;

        for (Enumeration<?> e = request.getHeaderNames(); e.hasMoreElements();) {
            String headerName = (String) e.nextElement();
            String headerValue = request.getHeader(headerName);
            header += "<li><b>" + headerName + "</b>: " + headerValue + "</li>";
            if (headerName.toLowerCase().equals("referer")) {
                referer = headerValue;
            }
        }

        boolean sendEmail = (referer == null) || (referer.length() == 0);
        header += "</ul>";

        if (user != null) {
            String errorMessage = "<p>Dear administrators,</p>" +
                    "<p>what is " + user.getFirstName() + " " + user.getLastName() +
                    " (User-ID: " + user.getId() + ") doing??? This user is not allowed to access this resource!</p>" +
                    "<p>In case this user tries to access such resources again the account will be locked automatically " +
                    "and the attack will be reported!</p><p>" + header + "</p>";

            if (sendEmail) {
                emailService.sendEmailAsync(
                        user.getMailAddress(),
                        "[InnoSensr] !!! ATTACKING THE SYSTEM !!!",
                        errorMessage,
                        errorMessage);
            }
            return errorMessage;
        }

        String errorMessage = "<p>Dear administrators,</p>" +
                "<p>Someone is trying to access a forbidden resource!</p>" +
                "<p>" + header + "</p>";

        if (sendEmail) {
            emailService.sendEmailAsync(
                    "ralleaustria@gmail.com",
                    "[InnoSensr] !!! ATTACKING THE SYSTEM !!!",
                    errorMessage,
                    errorMessage);
        }
        return errorMessage;
	}

	public static String generateDeadlineErrorMessageAndSendEmail(HttpServletRequest request, UserDbo user, EmailService emailService) {
    	String clientIpAddress = request.getRemoteAddr();
		String forwardedIpAddress = request.getHeader("X-FORWARDED-FOR");
		String userAgentInfo = request.getHeader("User-Agent");

		String header = "<ul>";
        header += "<li><b>IP Address</b> " + clientIpAddress + "</li>";
        header += "<li><b>Forwarded IP Address:</b> " + forwardedIpAddress + "</li>";
        header += "<li><b>User Agent:</b> " + userAgentInfo + "</li>";
        header += "<li><b>Method:</b> " + request.getMethod() + "</li>";
        header += "<li><b>URL:</b> " + request.getRequestURL() + "</li>";
        header += "<li><b>Session ID:</b> " + request.getRequestedSessionId() + "</li>";
        header += "<li><b>Query String:</b> " + request.getQueryString() + "</li>";
        String referer = null;

        for (Enumeration<?> e = request.getHeaderNames(); e.hasMoreElements();) {
            String headerName = (String) e.nextElement();
            String headerValue = request.getHeader(headerName);
            header += "<li><b>" + headerName + "</b>: " + headerValue + "</li>";
            if (headerName.toLowerCase().equals("referer")) {
                referer = headerValue;
            }
        }

        header += "</ul>";

		String errorMessage = "<p>Dear administrators,</p>" +
				"<p>" + user.getFirstName() + " " + user.getLastName() +
				" (User-ID: " + user.getId() + ") tried to make changes after the deadline. This user is not allowed to access this resource any more!</p>"
				+ header;

		emailService.sendEmailAsync(
				user.getMailAddress(),
				"[InnoSensr] !!! DEADLINE IS OVER !!!",
				errorMessage,
				errorMessage);
		return errorMessage;
	}

	public static void createDefaultUsers(UserServiceI userService, UserRepository userRepository) {
        List<UserRegistrationDbo> users = new ArrayList<>();
        UserRegistrationDbo user1 = new UserRegistrationDbo();
        user1.setUsername("stetti1");
        user1.setFirstName("Martin");
        user1.setLastName("Mustermann #1");
        user1.setPassword("123456");
        user1.setEmail("test1@example.com");
        users.add(user1);

        UserRegistrationDbo user2 = new UserRegistrationDbo();
        user2.setUsername("stetti2");
        user2.setFirstName("Martin");
        user2.setLastName("Mustermann #2");
        user2.setPassword("123456");
        user2.setEmail("test2@example.com");
        users.add(user2);

        UserRegistrationDbo user3 = new UserRegistrationDbo();
        user3.setUsername("stetti3");
        user3.setFirstName("Martin");
        user3.setLastName("Mustermann #3");
        user3.setPassword("123456");
        user3.setEmail("test3@example.com");
        users.add(user3);

        UserRegistrationDbo user4 = new UserRegistrationDbo();
        user4.setUsername("stetti4");
        user4.setFirstName("Martin");
        user4.setLastName("Mustermann #4");
        user4.setPassword("123456");
        user4.setEmail("test4@example.com");
        users.add(user4);

        UserRegistrationDbo user5 = new UserRegistrationDbo();
        user5.setUsername("stetti5");
        user5.setFirstName("Martin");
        user5.setLastName("Mustermann #5");
        user5.setPassword("123456");
        user5.setEmail("test5@example.com");
        users.add(user5);

        for (UserRegistrationDbo userRegistrationData : users) {
            if (userRepository.findOneByUsername(userRegistrationData.getUsername()) != null) {
                continue;
            }

            userService.save(userRegistrationData, "", true);
        }
	}

	@SuppressWarnings("serial")
	public static void createDefaultProjects(String superUsername, ProjectRepository projectRepository, ReleaseRepository releaseRepository,
			UserService userService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			SkillRepository skillRepository, RequirementRepository requirementRepository) {
		SimpleDateFormat dateformat3 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (projectRepository.count() > 0) {
				return;
			}

			projectRepository.deleteAll();
			releaseRepository.deleteAll();
			requirementRepository.deleteAll();
			userRepository.deleteAll();
			skillRepository.deleteAll();
			UserDbo adminUser = userRepository.findOneByUsername(superUsername);

			List<List<String>> skills = new ArrayList<>();
			skills.add(Arrays.asList("German Fluent", "English Fluent", "Android Development", "UI/UX Expert", "Project Management Beginner"));
			skills.add(Arrays.asList("English Fluent", "Android", "UI/UX Expert"));
			skills.add(Arrays.asList("English Fluent", "Java Advanced"));
			skills.add(Arrays.asList("German Beginner", "English Fluent", "Java Advanced"));
			skills.add(Arrays.asList("German Fluent", "English Fluent", "Project Management Master", "Scrum Master", "Scrum Product Owner"));
			skills.add(Arrays.asList("English Fluent", "C++ Developer", "C# Master", "Scrum Project Owner"));
			skills.add(Arrays.asList("English Fluent", "German Fluent", "Italian Fluent", "Graphics / Design Master", "Project Management", "Photoshop", "Indesign"));
			skills.add(Arrays.asList("English Fluent", "Italian Fluent", "Java Development Master", "Project Management"));
			skills.add(Arrays.asList("English Fluent", "German Fluent", "Electronics", "Project Management"));
			skills.add(Arrays.asList("English Fluent", "Railway Systems Engineer"));

			List<String> names = new ArrayList<String>();
			names.add("Deadra Waltrip");
			names.add("Jerrica Muscarella");
			names.add("Sharleen Plewa");
			names.add("Avery Brokaw");
			names.add("Luther Owensby");
			names.add("Kacie Gutierres");
			names.add("Lucienne Ruddell");
			names.add("Marquerite Sandstrom");
			names.add("Cristal Mae");
			names.add("Earline Whitley");

			List<String> requirementNames = new ArrayList<String>();
			requirementNames.add("Login");
			requirementNames.add("Registrierung");
			requirementNames.add("Home / Project Overview");
			requirementNames.add("Create New Project");
			requirementNames.add("Configuration Backend");
			requirementNames.add("Configurator Interface");

	        Date p1_start = dateformat3.parse("17/07/2016");
	        Date p2_start = dateformat3.parse("03/08/2016");
	        Date p3_start = dateformat3.parse("01/12/2017");
	        Date p1_end = dateformat3.parse("01/01/2017");
	        Date p2_end = dateformat3.parse("07/09/2018");
	        Date p3_end = dateformat3.parse("31/12/2020");

	        ProjectDbo project1 = new ProjectDbo(Utils.generateRandomProjectKey(projectRepository), "ChoiclaWeb", "ChoiclaWeb offers the possibility of establishing a central decision knowledge base. Clearly definded decision processes enable traceability and transparency.", p1_start, p1_end, "/upload/logo_choiclaweb.svg", true, adminUser);
	        	ProjectDbo project2 = new ProjectDbo(Utils.generateRandomProjectKey(projectRepository), "AGILE", "AGILE aims to develop the software and hardware required to build modular gateway solutions for managing the devices and data in IoT solutions. It will support the local management of devices, data, app development, and runtime, and include security features that allow users to share data in a trusted way.", p2_start, p2_end, "/upload/logo_ase.png", true, adminUser);
	        adminUser.addCreatedProject(project1);
	        adminUser.addCreatedProject(project2);

			int index = 0;
			List<UserDbo> users = new ArrayList<>();
			Map<String, SkillDbo> allSkills = new HashMap<>();

			userService.setUserRepository(userRepository);

			for (String name : names) {
				UserDbo newUser = userService.findByUsername(name);
				if (newUser != null) {
					users.add(newUser);
					++index;
					continue;
				}

				final String firstName = name.split(" ")[0];
				final String lastName = name.split(" ")[1];
				final String email = firstName + "." + lastName + "@ist.tugraz.at";
				final HashSet<RoleDbo> roles = new HashSet<>();
				roles.add(new RoleDbo(RoleDbo.Role.ROLE_STAKEHOLDER));

				newUser = new UserDbo(firstName, lastName, name.replace(' ', '.'), email, passwordEncoder.encode("test"));
		        newUser.setRoles(roles);
		        newUser.setEnabled(true);

				for (String skillKeyword : skills.get(index)) {
					SkillDbo skill = allSkills.get(skillKeyword);
					if (skill == null) {
						skill = new SkillDbo(skillKeyword);
						allSkills.put(skillKeyword, skill);
					}
					skill.addUser(newUser);
					newUser.addUserSkill(skill);
				}

				ProjectUserParticipationDbo projectUserParticipation1 = new ProjectUserParticipationDbo(project1, newUser);
				ProjectUserParticipationDbo projectUserParticipation2 = new ProjectUserParticipationDbo(project2, newUser);
				newUser.addProjectUserParticipation(projectUserParticipation1);
				newUser.addProjectUserParticipation(projectUserParticipation2);
				users.add(newUser);
				++index;
			}

	        ProjectDbo project3 = new ProjectDbo(Utils.generateRandomProjectKey(projectRepository), "Catrobat",
	        		"Program, play, and share your own games, animations, interactive art, music videos, and many kinds of other apps, directly on your phone or tablet!\n" +
	        		"\nOne Hour of Code tutorial: https://catrob.at/HourOfCode - for other tutorials see http://catrob.at/i and http://catrob.at/help",
	        		p3_start, p3_end, "https://www.catrobat.org/images/logo.png", true, users.get(0));

	        ReleaseDbo release1 = new ReleaseDbo("Release #1", "-", p1_end, 100, project1);
	        ReleaseDbo release2 = new ReleaseDbo("Release #2", "-", p1_end, 110, project2);
	        ReleaseDbo release3 = new ReleaseDbo("Release #3", "-", p2_end, 300, project3);
//	        ReleaseDbo release4 = new ReleaseDbo("Release #4", "-", p2_start, p2_end, 420, project2);
//	        ReleaseDbo release5 = new ReleaseDbo("Release #5", "-", p2_start, p2_end, 350, project2);
//	        ReleaseDbo release6 = new ReleaseDbo("Release #6", "-", p3_start, p3_end, 90, project3);
	        release1.setCapacity(100);
	        release2.setCapacity(120);
	        release3.setCapacity(60);
//	        release4.setCapacity(70);
//	        release5.setCapacity(75);
//	        release6.setCapacity(105);

	        release2.setStatus(Status.COMPLETED);
//	        release4.setStatus(Status.COMPLETED);
//	        release5.setStatus(Status.COMPLETED);

	        project1.addRelease(release1);
	        project2.addRelease(release2);
	        project3.addRelease(release3);
//	        project2.addRelease(release4);
//	        project2.addRelease(release5);
//	        project3.addRelease(release6);

	        Map<String, Set<UserDbo>> requirementAssignments = new HashMap<>();
			final HashSet<UserDbo> loginAssignments = new HashSet<>();
			loginAssignments.add(users.get(0));
			loginAssignments.add(users.get(1));
			loginAssignments.add(users.get(2));
			loginAssignments.add(users.get(3));
			loginAssignments.add(users.get(4));
			loginAssignments.add(users.get(5));
			loginAssignments.add(users.get(6));
			loginAssignments.add(users.get(7));
			loginAssignments.add(users.get(8));
			loginAssignments.add(users.get(9));

			final HashSet<UserDbo> registerAssignments = new HashSet<>();
			registerAssignments.add(users.get(0));
			registerAssignments.add(users.get(2));
			registerAssignments.add(users.get(3));
			registerAssignments.add(users.get(4));
			registerAssignments.add(users.get(5));
			registerAssignments.add(users.get(6));
			registerAssignments.add(users.get(7));
			registerAssignments.add(users.get(8));
			registerAssignments.add(users.get(9));

			final HashSet<UserDbo> configuratorInterfaceAssignments = new HashSet<>();
			configuratorInterfaceAssignments.add(users.get(1));

	        requirementAssignments.put("Login", loginAssignments);
	        requirementAssignments.put("Registrierung", registerAssignments);
	        requirementAssignments.put("Configurator Interface", configuratorInterfaceAssignments);

	        Map<String, List<String>> requirementSkillAssignments = new HashMap<>();
	        requirementSkillAssignments.put("Login", Arrays.asList("Android Development", "UI/UX Design", "Data validation"));
	        requirementSkillAssignments.put("Registrierung", Arrays.asList("Android Development", "UI/UX Design", "Data validation"));
	        requirementSkillAssignments.put("Home / Project Overview", Arrays.asList("Java", "UI/UX Design", "Data validation"));
	        requirementSkillAssignments.put("Create New Project", Arrays.asList("Android","Java","UI/UX Design", "Railway", "Project Management"));
	        requirementSkillAssignments.put("Configuration Backend", Arrays.asList("Android","Java","UI/UX Design","Railway Domain","Knowledge-based Configuration","Knowledge-based Configuration", "Railway Domain", "Decision Psychology"));
	        requirementSkillAssignments.put("Configurator Interface", Arrays.asList("UI/UX Design", "Graphics Design", "Railway Domain", "English Fluent", "Android"));

	        generateDefaultRatingSchemeAndAddToProject(project1);
	        generateDefaultRatingSchemeAndAddToProject(project2);
	        generateDefaultRatingSchemeAndAddToProject(project3);

	        int counter = 0;

 			for (String requirementName : requirementNames) {
				RequirementDbo requirement = requirementRepository.findOneByTitle(requirementName);
				if (requirement == null) {
					project1.incrementLastProjectSpecificRequirementId();
					requirement = new RequirementDbo(project1.getLastProjectSpecificRequirementId(), requirementName, "", project1);

					project1.addRequirement(requirement);

					if (counter < 2) {
						requirement.setRelease(release1);
						release1.addRequirement(requirement);
					}
					counter++;
				}

				Set<UserDbo> stakeholderUsers = requirementAssignments.get(requirementName);
				if ((stakeholderUsers != null) && (stakeholderUsers.size() > 0)) {
				    /*
					for (UserDbo stakeholderUser : stakeholderUsers) {
						stakeholderUser.assignResponsibleRequirement(requirement);
						requirement.assignResponsibleUser(stakeholderUser);
					}
					*/
				}

				List<String> skillNames = requirementSkillAssignments.get(requirementName);
				if (skillNames == null) {
					continue;
				}

				for (String skillKeyword : skillNames) {
					SkillDbo skill = allSkills.get(skillKeyword);
					if (skill == null) {
						skill = new SkillDbo(skillKeyword);
						allSkills.put(skillKeyword, skill);
					}
					skill.addRequirement(requirement);
					requirement.addRequirementSkill(skill);
				}
			}

	        projectRepository.save(project1);
	        projectRepository.save(project2);
	        projectRepository.save(project3);

		} catch (Exception e) {
			System.out.println("!!! Exception: " + e.getClass().getSimpleName());
			System.out.println(e.getMessage());
		}
	}

}
