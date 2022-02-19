package com.springboot.telegym.dao.user;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dto.UserDto;
import com.springboot.telegym.dto.enums.RoleEnum;
import com.springboot.telegym.entity.Role;
import com.springboot.telegym.entity.User;
import com.springboot.telegym.repository.RoleRepository;
import com.springboot.telegym.repository.UserRepository;
import com.springboot.telegym.security.JwtUtils;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Component
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    public UserDaoImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto createOrUpdate(UserDto userDto) {
        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();
        String transmissionRole = userDto.getRole();
        Role role;
        // Nếu không truyền thì set role mặc định là ROLE_STAFF
        if (userDto.getRole() == null) {
            role = roleRepository.findByRolename(RoleEnum.ROLE_STAFF.getRole())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        } else {
            if ("admin".equals(transmissionRole)) {
                role = roleRepository.findByRolename(RoleEnum.ROLE_ADMIN.getRole())
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            } else {
                role = roleRepository.findByRolename(RoleEnum.ROLE_STAFF.getRole())
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }
        }

        if (userDto.getId() != null && userDetails != null) {
            Optional<User> addingAccount = userRepository.findById(userDto.getId());
            if (addingAccount.isPresent()) {
                User updatedUser;
                updatedUser = addingAccount.get();
                updatedUser.setRole(role);
                updatedUser.setPass(encoder.encode(userDto.getPass()));
                updatedUser.setName(userDto.getName());
                updatedUser.setUpdated_time(new Date());
                updatedUser.setUpdated_by(updatedUser.getId());
                userRepository.save(updatedUser);
                return new UserDto(updatedUser);
            } else {
                MessageResponse.message = "Không tìm thấy id";
                return null;
            }

        } else if (userDetails == null) {
            MessageResponse.message = "Không xác định người dùng";
            return null;
        } else {
            UserDto checkUsernameExist = findByUsername(userDto.getUsername());
            if (checkUsernameExist != null) {
                MessageResponse.message = "Tên tài khoản bị trùng";
                return null;
            }
            User newUser = new User();
            newUser.setId(UUID.randomUUID().toString());
            newUser.setUsername(userDto.getUsername());
            newUser.setName(userDto.getName());
            newUser.setPass(encoder.encode(userDto.getPass()));
            newUser.setRole(role);
            newUser.setCreated_time(new Date());
            newUser.setUpdated_time(new Date());
            newUser.setCreated_by(userDetails.getId());
            newUser.setUpdated_by(userDetails.getId());
            userRepository.save(newUser);
            return new UserDto(newUser);
        }
    }

    @Override
    public UserDto getById(String id) {
        User user;
        Optional<User> accountEntity = userRepository.findById(id);
        if (accountEntity.isPresent()) {
            user = accountEntity.get();
            return new UserDto(user);
        }
        return null;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user;
        Optional<User> accountEntity = userRepository.findByUsername(username);
        if (accountEntity.isPresent()) {
            user = accountEntity.get();
            return new UserDto(user);
        } else {
            MessageResponse.message = "Không tìm thấy kết quả phù hợp";
            return null;
        }

    }

    @Override
    public boolean existById(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        Optional<User> account = userRepository.findById(id);
        if (account.isPresent()) {
            account.get().set_deleted(true);
            account.get().setUpdated_time(new Date());
            userRepository.save(account.get());
        }
    }

    @Override
    public UserDto login(SearchObject searchObject) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(searchObject.getStr1(), searchObject.getStr2()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);

        UserDto currUser = getById(userDetails.getId());
        if (currUser == null || currUser.isDelete()) {
            MessageResponse.message = "Không xác định tài khoản hoặc tài khoản đã bị xoá";
            return null;
        } else {
            currUser.setTokenValue(jwt);
            currUser.setUpdated_time(new Date());
            userRepository.updateJwt(currUser.getTokenValue(), currUser.getUpdated_time(), currUser.getId());
            return currUser;
        }
    }

    @Override
    public UserDetails loadByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    @Override
    public PageData<UserDto> filterUser(Pageable pageable, String search, List<String> roleAccount) {
        Page<Object[]> userPage;
        userPage = userRepository.searchUserByFilter(pageable, search, roleAccount);
        User u = new User();
        List<UserDto> userDtoList = new ArrayList<>();
        for (Object[] data : userPage.getContent()) {
            String username = (String) data[0];
            String name = (String) data[1];
            Optional<Role> role = roleRepository.findByRolename((String) data[2]);
            u.setUsername(username);
            u.setName(name);
            role.ifPresent(u::setRole);
            userDtoList.add(convertToUserDto(u));
        }
        return new PageData<>(userDtoList, userPage.getTotalPages(), userPage.getTotalElements(), userPage.hasNext());
    }

    private UserDto convertToUserDto(User u) {
        return UserDto.builder()
                .username(u.getUsername())
                .name(u.getName())
                .role(u.getRole().getRolename())
                .build();
    }
}
