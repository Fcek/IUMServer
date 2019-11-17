package entities;

import javax.persistence.*;
import java.util.Date;
@Table(name = "account")
@Entity
@NamedQueries({
        @NamedQuery(
                name = "de.clickhealthy.manage.db.accountEntity.findAll",
                query = "SELECT i FROM AccountEntity i"),
        @NamedQuery(
                name = "de.clickhealthy.manage.db.accountEntity.findById",
                query = "SELECT i FROM AccountEntity i WHERE i.id = :id"),
        @NamedQuery(
                name = "de.clickhealthy.manage.db.accountEntity.findByEmail",
                query = "SELECT i FROM AccountEntity i WHERE i.email = :email"),
        @NamedQuery(
                name = "de.clickhealthy.manage.db.accountEntity.size",
                query = "SELECT COUNT(*) FROM AccountEntity i"),
        @NamedQuery(
                name = "de.clickhealthy.manage.db.accountEntity.isActive",
                query = "SELECT active FROM AccountEntity i where email = :email")
})
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "activation_hash")
    private String activationHash;

    @Column(name = "created")
    private Date created;


    @Column(name = "role")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getActivationHash() {
        return activationHash;
    }

    public void setActivationHash(String activationHash) {
        this.activationHash = activationHash;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
