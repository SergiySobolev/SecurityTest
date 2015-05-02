package sbk.sprtest.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="role")
public class RoleEntity extends BaseEntity{

	@Id
	@Column(name = "role_id")
	private Integer id;

	@Column(name = "role_name")
	private String roleName;

	@ManyToMany(mappedBy="roles")
	private Set<PrincipalEntity> principals = new HashSet<PrincipalEntity>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<PrincipalEntity> getPrincipals() {
		return principals;
	}

	public void setPrincipals(Set<PrincipalEntity> principals) {
		this.principals = principals;
	}
}
