# Cross-Origin Resource Sharing (CORS)

## What is CORS?

CORS (Cross-Origin Resource Sharing) is a security feature implemented by browsers that restricts web pages from making
requests to a domain different from the one that served the original page. This is a security mechanism that helps
prevent malicious websites from accessing sensitive data across origins.

## Why is CORS Necessary in Our Application?

In our Quiz application:

- The frontend runs on `http://localhost:5173` (or a different domain in production)
- The backend API runs on `http://localhost:8080`
- WebSockets communication happens over `ws://localhost:8080`

Since these are different origins (different protocols/domains/ports), browsers will block cross-origin requests by
default, preventing our frontend from communicating with our backend.

## Our Current CORS Configuration

In `WebConfig.java`, we configure CORS to allow specific cross-origin requests:

```java
registry.addMapping("/api/**")
        .allowedOrigins("http://localhost:5173") // Frontend development server
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
```

This configuration:

1. Applies to all `/api/**` endpoints
2. Allows requests only from `http://localhost:5173`
3. Permits specific HTTP methods
4. Allows all headers
5. Enables credentials (cookies, authorization headers) to be included

## WebSocket CORS Configuration

For WebSockets, CORS is configured in `WebSocketConfig.java`:

```java
registry.addEndpoint("/ws")
        .setAllowedOrigins("http://localhost:5173")
        .withSockJS();
```

## Important Considerations When Working with CORS

### 1. Security Implications

- **Never use** `.allowedOrigins("*")` with `.allowCredentials(true)` - this is a security risk and browsers will reject
  it
- Be specific about allowed origins, especially in production environments
- Only expose necessary endpoints through CORS

### 2. Production Configuration

- Update CORS configuration with production domain names before deployment
- Consider using environment variables for allowed origins to support different environments

```java
// Example of using environment variable
@Value("${app.cors.allowed-origins}")
private String[] allowedOrigins;

// Then in configuration
registry.addMapping("/api/**")
        .allowedOrigins(allowedOrigins)
        // rest of configuration
```

### 3. Troubleshooting CORS Issues

Common CORS errors:

- "Access to XMLHttpRequest has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header"
- "Access to fetch at X from origin Y has been blocked by CORS policy"

Troubleshooting steps:

1. Check browser console for specific CORS error messages
2. Verify that the origin making the request matches exactly what's in `.allowedOrigins()`
3. For preflight requests (OPTIONS), ensure all required methods and headers are allowed
4. Confirm that credentials settings are consistent (both client and server)

### 4. Adding New Endpoints

When adding new API endpoints:

1. They will automatically inherit CORS configuration if under `/api/**`
2. For endpoints outside this pattern, update `WebConfig.java` accordingly
3. For WebSocket endpoints, update `WebSocketConfig.java`

### 5. Testing CORS Configuration

Use `WebConfigTest.java` as a reference for testing CORS configuration changes.

## Common CORS Headers

- `Access-Control-Allow-Origin`: Specifies which origins can access the resource
- `Access-Control-Allow-Methods`: Lists allowed HTTP methods
- `Access-Control-Allow-Headers`: Indicates which headers can be used
- `Access-Control-Allow-Credentials`: Indicates whether the request can include credentials
- `Access-Control-Max-Age`: How long preflight results can be cached

## CORS vs. CSRF

Don't confuse CORS with CSRF (Cross-Site Request Forgery):

- CORS is a browser security feature that restricts cross-origin requests
- CSRF is an attack where a malicious site tricks a user into performing unwanted actions on a site they're
  authenticated to

Our application uses both CORS (in `WebConfig.java`) and CSRF protection (in `SecurityConfig.java`) for comprehensive
security.
