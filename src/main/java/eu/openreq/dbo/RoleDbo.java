package eu.openreq.dbo;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "or_role")
public class RoleDbo {

	public enum Role { ROLE_ADMIN, ROLE_REQUIREMENTS_MANAGER, ROLE_STAKEHOLDER }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

    @Enumerated(EnumType.STRING)
	@Column(name = "role", nullable=false)
	private Role role;

	public RoleDbo() {}

	public RoleDbo(Role role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RoleDbo roleDbo = (RoleDbo) o;
		return Objects.equals(id, roleDbo.id) &&
				role == roleDbo.role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, role);
	}

}
