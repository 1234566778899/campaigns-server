package backend.project.serviceimpl;

import backend.project.dtos.DTOUser;
import backend.project.entities.Authority;
import backend.project.entities.User;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.UserRepository;
import backend.project.security.SecurityUser;
import backend.project.services.AuthorityService;
import backend.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityService authorityService;

    @Override
    public User findByUsername(String username) {
        User userFound = userRepository.findByUserName(username);
        if (userFound==null){
            throw new ResourceNotFoundException("User with username: "+ username+ " can not be found");
        }
        return userFound;
    }

    @Override
    public User findById(Long id) {
        User userFound = userRepository.findById(id).orElse(null);
        if (userFound==null){
            throw new ResourceNotFoundException("User with id: "+ id + " can not be found");
        }
        return userFound;
    }


    public List<Authority> authorityListFromString(String authorityString) {

        //List<String> authorityListString =  Arrays.stream(authorityString.split(";")).toList();
        List<String> authorityListString =  List.of(authorityString.split(";"));

        List<Authority> authorityList = new ArrayList<>();

        for (String authorityStringItem: authorityListString){
            Authority authority = authorityService.findByName(authorityStringItem);
            authorityList.add(authority);
        }

        return  authorityList;
    }

    @Override
    public User addUser(DTOUser dtoUser) {

        User newUser = new User();
        //FALTA VALIDAR QUE NO SE REPITA USERNAME
        newUser.setUserName(dtoUser.getUserName());
        //FALTA VALIDAR QUE NO SE REPITA PASSWORD, LONGITUD MINIMA, REGLAS MAY/MIN
        newUser.setPassword( new BCryptPasswordEncoder().encode(dtoUser.getPassword()));

        newUser.setEnabled(true);
        //se puede poner false y mandar un mail con un link de activacion, capturar con GET cuando el usuario hace click en link
        //analizar las PathVariables de la ruta y activar el usuario recien creado

        newUser.setAuthorities(authorityListFromString(dtoUser.getAuthorities()));



        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(findByUsername(username));
    }
}
