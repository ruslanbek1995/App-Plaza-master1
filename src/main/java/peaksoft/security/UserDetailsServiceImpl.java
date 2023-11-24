package peaksoft.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import peaksoft.model.User;
import peaksoft.service.impl.UserService;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    // username аркылуу бир userди алып чыгат
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userService.findByGmail(username);
        if (user== null) {
            throw new UsernameNotFoundException("exseptions : " + username);
        }
        return user;
    }
}
