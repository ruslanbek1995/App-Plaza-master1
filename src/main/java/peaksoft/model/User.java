package peaksoft.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.model.enums.SubscriptionStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String gmail;
    private String password;
    private LocalDate localDate;
    private boolean subscribeToTheNewsLetter;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;
    @ManyToMany(cascade = {CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinTable(name = "user_and_application",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "application_id"))
    private List<Application> applications;

    @ManyToMany(cascade ={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE} ,fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles= new ArrayList<>();

    //рольдорду опередетить этип берет
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role:roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return gmail;
    }
    //Срок действия учетной записи не истек
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //акаунт не блокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
//Срок действия учетных данных не истек
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
// включен
    @Override
    public boolean isEnabled() {
        return true;
    }
}
