package kz.iitu.office.auth.service.service;


import kz.iitu.office.auth.service.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeInfoService implements UserDetailsService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee = restTemplate.getForEntity("http://localhost:8081/employee/" + s, Employee.class).getBody();

        if (employee == null) {
            throw new UsernameNotFoundException("Member: " + s + " not found!");
        }
        return employee;
    }
}
