package mum.edu.demo.demain;


import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    public static class RoleBuilder implements Builder<Role> {
        private Role role;

        public RoleBuilder() {
            this.role = new Role();
        }

        public Role.RoleBuilder withId(Long id){
            role.setId(id);
            return this;
        }

        public Role.RoleBuilder withRole(String role){
            this.role.setRole(role);
            return this;
        }

        @Override
        public Role build() {
            return role;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role")
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static Role.RoleBuilder create() {
        return new Role.RoleBuilder();
    }
}
