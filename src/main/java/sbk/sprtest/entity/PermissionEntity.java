package sbk.sprtest.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Entity
@Table(name = "permission")
public class PermissionEntity {

    @Id
    @Column(name = "permission_id")
    Long id;

    @Column(name = "permission_name")
    String name;

    @ManyToMany(mappedBy="permissions")
    private Set<RoleEntity> roles = newHashSet();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
