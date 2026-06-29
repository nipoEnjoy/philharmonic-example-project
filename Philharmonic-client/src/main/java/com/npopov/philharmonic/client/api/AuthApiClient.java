package com.npopov.philharmonic.client.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.npopov.philharmonic.client.model.CrudPermissions;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles POST /auth/login.
 */
public class AuthApiClient extends BaseApiClient {

    private static final AuthApiClient INSTANCE = new AuthApiClient();

    private AuthApiClient() {}

    public static AuthApiClient getInstance() {
        return INSTANCE;
    }

    /**
     * Authenticates and returns a {@link LoginResult} containing the JWT token and roles.
     *
     * @throws ApiException with statusCode 401 if credentials are wrong.
     */
    public LoginResult login(String username, String password) {
        Map<String, String> payload = Map.of(
                "username", username,
                "password", password
        );
        return postAnon("/auth/login", payload, LoginResult.class);
    }

    // ── Response model ────────────────────────────────────────────────────────

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoginResult {
        private String token;
        private List<String> roles;
        private List<String> permissions;

        public Set<CrudPermissions> getPermissionsAsSet() {
            if (permissions == null) return Set.of();
            return permissions.stream()
                    .map(CrudPermissions::valueOf)
                    .collect(Collectors.toSet());
        }

        public List<String> getPermissions() { return permissions; }
        public void setPermissions(List<String> permissions) { this.permissions = permissions; }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public List<String> getRoles() { return roles != null ? roles : List.of(); }
        public void setRoles(List<String> roles) { this.roles = roles; }

        public Set<String> getRolesAsSet() {
            return roles != null ? Set.copyOf(roles) : Set.of();
        }
    }
}
