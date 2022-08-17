package fc.projectboard.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardPrincipal {
    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    Collection<? extends GrantedAuthority> authorities;
    private String email;
    private String nickname;
    private String memo;

    public static BoardPrincipal of(String username, String password, String email, String nickname, String memo) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);
        return new BoardPrincipal(username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                email,
                nickname,
                memo);
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname,
                memo
        );
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public enum RoleType {
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
