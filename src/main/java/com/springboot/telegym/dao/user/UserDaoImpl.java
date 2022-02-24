package com.springboot.telegym.dao.user;

import com.springboot.telegym.common.*;
import com.springboot.telegym.dto.UserDto;
import com.springboot.telegym.dto.enums.RoleEnum;
import com.springboot.telegym.entity.Role;
import com.springboot.telegym.entity.User;
import com.springboot.telegym.repository.RoleRepository;
import com.springboot.telegym.repository.UserRepository;
import com.springboot.telegym.request.UserResponseQuery;
import com.springboot.telegym.security.JwtUtils;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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
                    .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy Role được chỉ định"));
        } else {
            if ("admin".equals(transmissionRole)) {
                role = roleRepository.findByRolename(RoleEnum.ROLE_ADMIN.getRole())
                        .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy Role được chỉ định"));
            } else {
                role = roleRepository.findByRolename(RoleEnum.ROLE_STAFF.getRole())
                        .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy Role được chỉ định"));
            }
        }

        if (userDto.getId() != null && userDetails != null) {

            User updatedUser = userRepository.updateUser(userDto.getId(),
                    encoder.encode(userDto.getPass()), userDto.getName(), userDetails.getId(), role.getId());
            if (updatedUser != null) {
                MessageResponse.message = "Update thành công";
                return new UserDto(updatedUser);
            } else {
                MessageResponse.message = "Update thất bại";
                return null;
            }

        } else if (userDetails == null) {
            MessageResponse.message = "Không xác định người dùng";
            return null;
        } else {

            User newUser = userRepository.createUser(UUID.randomUUID().toString(), userDto.getUsername(),
                    encoder.encode(userDto.getPass()), userDto.getName(), userDetails.getId(),
                    userDetails.getId(), role.getId());
            if (newUser != null) {
                MessageResponse.message = "Đăng ký thành công";
                return new UserDto(newUser);
            }
            MessageResponse.message = "Tên tài khoản bị trùng";
            return null;
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
    public int deleteById(String id) {
        int lineSuccess = userRepository.deleteUser(id);
        if (lineSuccess > 0)
            MessageResponse.message = "Xoá thành công";
        else
            MessageResponse.message = "Xoá thất bại";
        return lineSuccess;
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
            userRepository.updateJwt(currUser.getTokenValue(), currUser.getId());
            return currUser;
        }
    }

    //Su dung trong AuthenFilter
    @Override
    public UserDetails loadByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    @Override
    public PageData<UserDto> filterUser(Pageable pageable, String search, List<String> roleAccount) {

        String listRole = Refactor.RefactorList(roleAccount);

        List<Object[]> userList = userRepository.searchUserFilter(search, listRole);

        List<UserResponseQuery> userResponseQueryList = new ArrayList<>();

        for (Object[] data : userList) {
            UserResponseQuery userResponseQuery = new UserResponseQuery();
            userResponseQuery.setUsername((String) data[0]);
            userResponseQuery.setName((String) data[1]);
            userResponseQuery.setRolename((String) data[2]);
            userResponseQuery.setCreated_time((Date) data[3]);
            userResponseQueryList.add(userResponseQuery);
        }

        userResponseQueryList.sort(new MyListComparator(pageable));

        PagedListHolder<UserResponseQuery> userPage = new PagedListHolder<>(userResponseQueryList);
        userPage.setPage(pageable.getPageNumber());
        userPage.setPageSize(pageable.getPageSize());

        User u = new User();
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserResponseQuery data : userPage.getPageList()) {
            String username = data.getUsername();
            String name = data.getName();
            Optional<Role> role = roleRepository.findByRolename(data.getRolename());
            u.setUsername(username);
            u.setName(name);
            role.ifPresent(u::setRole);
            userDtoList.add(convertToUserDto(u));
        }

        return new PageData<>(userDtoList, userPage.getPageCount(), userPage.getNrOfElements(), userPage.isLastPage());
    }

    private UserDto convertToUserDto(User u) {
        return UserDto.builder()
                .username(u.getUsername())
                .name(u.getName())
                .role(u.getRole().getRolename())
                .build();
    }
}
