package attend.geo.attend.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static attend.geo.attend.constants.Permission.ADMIN_READ;
import static attend.geo.attend.constants.Permission.USER_READ;

@RequiredArgsConstructor
public enum Role {
  ADMIN(Set.of(ADMIN_READ)),
  USER(Set.of(USER_READ));

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
