package core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("role")
    private String role;
    @JsonProperty("role")    public String getRole() {
        return role;
    }
    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }
    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }
}
