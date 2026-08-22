# Java / Spring Boot Coding Conventions

A generalized coding convention document, extracted from a working Spring Boot codebase and written to be reused across projects. The goal is consistency, predictability, and code that reads like plain English — a developer new to the project should be able to open any file and immediately know what it does and where to find related files.

---

## 1. Guiding Principles

1. **Names must describe the thing.** A method called `deactivateUsersByUserIds` says exactly what it does. A method called `handleUsers` does not. Prefer long, clear names over short, cryptic ones.
2. **One responsibility per class.** Controllers only translate HTTP ⇄ service calls. Services hold business logic. Mappers only convert between shapes. Specifications only build query predicates.
3. **Interface + Impl split.** Every service is defined as an interface first; the implementation lives next to it in an `impl/` package. Callers depend on the interface, never on the concrete class.
4. **Single DTO per resource.** One `XxxDTO` serves as both request and response body. Optional/`null` fields distinguish create vs. update. Only split into `XxxRequestDto` / `XxxResponseDto` when the two shapes genuinely diverge (login is a classic example).
5. **Wrap every HTTP response in a common envelope.** Success and failure both use `BaseApiResponse<T>`. Clients parse one shape.
6. **All business failures throw `BusinessException(ErrorCode, reason)`.** Never return `null`, never leak stack traces, never invent ad-hoc response shapes for errors.
7. **Log at boundaries, not everywhere.** One log line per service entry point, one per request via the interceptor, structured error logs in the global handler. Avoid decorative logs inside loops or private helpers.

---

## 2. Package Layout

```
com.<org>.<app>
├── Application.java                     # @SpringBootApplication entry point
├── annotation/                          # Custom annotations (@ApiResponseMessage, ...)
├── common/
│   ├── entity/BaseEntity.java           # Shared @MappedSuperclass (id, timestamps)
│   ├── response/BaseApiResponse.java    # Unified API envelope
│   └── utils/                           # Constants.java, TraceIdFilter.java, ...
├── config/                              # @Configuration classes
│   ├── AsyncConfig.java
│   ├── email/
│   ├── security/                        # SecurityConfig, JwtAuthenticationFilter, ...
│   │   └── service/{interface + impl/}  # JwtService, TaskUserDetailsService
│   ├── swagger/
│   └── web/WebConfig.java               # Interceptor registration
├── controller/                          # @RestController classes
├── dto/                                 # Single DTO per resource (TaskDTO, UserDTO)
│   ├── email/                           # Feature-scoped DTOs
│   ├── requestDto/                      # Only when request/response truly differ
│   ├── responseDto/                     # Envelope types (ListResponseDTO)
│   └── security/                        # LoginRequestDTO, LoginResponseDTO
├── entity/                              # @Entity JPA classes
├── enums/                               # Domain enums (TaskStatus, TaskPriority)
├── exceptions/                          # BusinessException, ErrorCode, GlobalExceptionHandler
├── interceptor/                         # Spring MVC HandlerInterceptors
├── mapper/                              # Static Entity ⇄ DTO mappers
├── repository/                          # Spring Data JPA interfaces
├── request/                             # Cross-cutting request holder (ParamRequest)
├── service/
│   ├── serviceInterface/                # Public service contracts
│   └── impl/                            # Concrete implementations
├── specification/                       # JPA Specification builders
└── utils/                               # Static utility helpers (CommonUtils, ...)
```

**Rules of thumb**
- Feature-scoped DTOs (`email/`, `security/`) live under `dto/` in their own subpackage.
- Cross-cutting filter classes (`TraceIdFilter`) live under `common/utils/` — they are infrastructure, not business logic.
- Never mix `@Configuration` beans into the `service/` tree.

---

## 3. Naming Conventions

| Layer / Concept       | Suffix / Pattern            | Example                                  |
|-----------------------|-----------------------------|------------------------------------------|
| REST controller       | `Controller`                | `TaskController`, `AuthController`       |
| Service interface     | *No prefix* — the noun      | `TaskService`, `UserService`             |
| Service impl          | `Impl`                      | `TaskServiceImpl`, `UserServiceImpl`     |
| Repository            | `Repository`                | `TaskRepository`                         |
| Entity                | Singular noun               | `Task`, `User`, `Role`                   |
| DTO                   | `DTO`                       | `TaskDTO`, `UserDTO`                     |
| Request-only DTO      | `RequestDTO` / `RequestDto` | `LoginRequestDTO`, `ListingRequestDto`   |
| Response-only DTO     | `ResponseDTO`               | `LoginResponseDTO`, `ListResponseDTO`    |
| Mapper                | `Mapper`                    | `TaskMapper`, `UserMapper`               |
| Specification         | `Specification`             | `TaskSpecification`, `UserSpecification` |
| Enum                  | Singular noun               | `TaskStatus`, `TaskPriority`             |
| Exception             | `Exception`                 | `BusinessException`, `EmailException`    |
| Configuration         | `Config`                    | `SecurityConfig`, `AsyncConfig`          |
| Filter / Interceptor  | `Filter` / `Interceptor`    | `TraceIdFilter`, `RequestLoggingInterceptor` |
| Constants class       | `Constants`                 | `Constants`                              |

**Method naming rules**
- **Say what it does, in plain English.** `getUserByEmailInternal`, `deactivateUsersByUserIds`, `sendUserCreatedEmail`.
- **Verbs, not nouns.** `createTask()`, not `taskCreation()`.
- **Prefer explicit parameters over overloading.** `getRoleById(userEmail, id)` beats a bare `getRole(id)`.
- **Suffix `Internal` for methods that skip user-facing checks / are used by other services.** Example: `getUserByEmailInternal(String userEmail)` is for cross-service use and does not wrap the response in an envelope.
- **`isRecords()`, `hasActiveStatus()`, `existsByEmail()`** — boolean methods start with `is`, `has`, `exists`.

**Variable & field naming**
- `camelCase` fields, `SCREAMING_SNAKE_CASE` constants.
- The header carrying the caller's email is `HEADER_USERID` (a constant) — never hard-code the literal `"X-User"` in a controller.
- Keep names singular vs. plural true to the data (`userIds` for a list, `userId` for one).

---

## 4. Controllers

Controllers are **thin**. They:
1. Read HTTP inputs (path, body, header, query params).
2. Delegate to a service.
3. Wrap the service result in `BaseApiResponse` and return `ResponseEntity`.

### Template

```java
@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<TaskDTO>> createTask(
            @RequestHeader(HEADER_USERID) String userEmail,
            @RequestBody TaskDTO taskDTO) {

        TaskDTO response = taskService.createTask(taskDTO, userEmail);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseApiResponse.<TaskDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Task created successfully")
                        .data(response)
                        .build());
    }
}
```

**Rules**
- Constructor-inject dependencies. Use `@AllArgsConstructor` (Lombok) or `@RequiredArgsConstructor` for `final` fields. Never field-inject with `@Autowired`.
- **URL conventions:** kebab-plural for resources — `/tasks`, `/roles`, `/user` (kept singular here for legacy). Sub-actions use `/{id}/<verb>` — `/{id}/activate`, `/{id}/deactivate`, `/{id}/unassign`.
- **HTTP method mapping**
  - `GET /tasks` — list
  - `GET /tasks/{id}` — one
  - `POST /tasks` — create (returns `201 CREATED`)
  - `PUT /tasks/{id}` — full replace
  - `PATCH /tasks/{id}` — partial update
  - `PATCH /tasks/{id}/<action>` — state transitions (activate, deactivate, unassign)
  - `DELETE /tasks/{id}` — remove
- Every endpoint that requires an authenticated caller reads `@RequestHeader(HEADER_USERID) String userEmail` first.
- List endpoints accept `ParamRequest` as a bound parameter (Spring binds query params to it automatically).
- **Never** put business logic (validation beyond `@Valid`, DB access, mapping) in a controller.
- Always specify the response type generically: `ResponseEntity<BaseApiResponse<TaskDTO>>` — never raw `ResponseEntity<?>`.

---

## 5. The `BaseApiResponse<T>` Envelope

Every successful and every failed response uses the same shape:

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseApiResponse<T> {
    private int status;      // HTTP status code (200, 201, 400, ...)
    private String message;  // Human-readable message
    private T data;          // Payload; null on failure
}
```

**Usage rules**
- Build with the Lombok builder: `BaseApiResponse.<TaskDTO>builder().status(...).message(...).data(...).build()`.
- On success, message is a past-tense confirmation: `"Task created successfully"`, `"Users fetched successfully"`.
- On failure, `data` is `null` and `message` describes the error concisely.
- The `status` field mirrors the HTTP status code — do not invent custom app-level codes.

---

## 6. `BusinessException` + `ErrorCode`

All expected, controllable failures throw `BusinessException`. Never return an error via `null` or a boolean flag.

### `ErrorCode`

A single enum defines the vocabulary of errors and their default HTTP status:

```java
@Getter
public enum ErrorCode {
    // ─── Generic / HTTP ───
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request payload"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"),

    // ─── Authentication / Authorization ───
    INVALID_ACCESS(HttpStatus.UNAUTHORIZED, "Unauthorized access"),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "You do not have permission to perform this action"),

    // ─── Validation ───
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Request validation failed"),

    // ─── Resource ───
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Requested resource not found"),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "Resource already exists");

    private final HttpStatus httpStatus;
    private final String defaultMessage;
    // ...
}
```

**Rules**
- Group codes by concern using comment banners: `Generic / HTTP`, `Auth`, `Validation`, `Resource`, `Domain-specific`.
- New codes are additive — never repurpose an existing code.
- Every code has a sensible `defaultMessage`; the caller can override with a `reason`.

### Throwing

```java
// Preferred: code + human reason
throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found.");

// Also fine: code only (uses defaultMessage)
throw new BusinessException(ErrorCode.PERMISSION_DENIED);
```

Guard clauses live at the top of the method:

```java
if (taskId == null || taskId <= 0) {
    throw new BusinessException(ErrorCode.BAD_REQUEST, "Task id must be valid.");
}
```

### `GlobalExceptionHandler`

A single `@RestControllerAdvice` catches everything and converts it into `BaseApiResponse<Void>`:

- `BusinessException` — status from the code, message from `ex.getMessage()`.
- `MethodArgumentNotValidException`, `BindException`, `ConstraintViolationException` — collect all field errors, join with `, `, return `VALIDATION_ERROR` / `BINDING_ERROR` / `CONSTRAINT_VIOLATION`.
- `HttpMessageNotReadableException` — `UNREADABLE_REQUEST`.
- `MissingRequestHeaderException`, `MissingServletRequestParameterException`, `MethodArgumentTypeMismatchException` — precise `BAD_REQUEST` messages naming the missing / invalid parameter.
- `DataIntegrityViolationException` — inspect `getMostSpecificCause()`; if it contains `"Duplicate entry"`, return `409 CONFLICT` with `"Resource already exists."`.
- `AccessDeniedException` — `PERMISSION_DENIED`.
- `Exception` (catch-all) — `log.error("Unhandled exception", ex)` and return `INTERNAL_SERVER_ERROR`. The user-facing message may be customized per-endpoint via `@ApiResponseMessage(error = "…")`.

### `@ApiResponseMessage`

A method-level annotation that lets a specific controller endpoint override the generic `500` message read by the global handler:

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseMessage {
    String success() default "";
    String error() default "Something went wrong.";
}
```

Use sparingly — only when the default `"An unexpected error occurred"` is misleading for that endpoint.

---

## 7. Logging

Three layers of logging cooperate. Do not add ad-hoc `log.info` calls outside them.

### 7.1 `TraceIdFilter` — one trace ID per request

```java
@Component
public class TraceIdFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);
        res.setHeader("X-Trace-Id", traceId);
        try { chain.doFilter(req, res); } finally { MDC.clear(); }
    }
}
```

- Every request gets a fresh UUID, stored in SLF4J MDC and echoed back in the response header so the client can quote it in a support ticket.
- The logging pattern (in `logback-spring.xml`) should include `%X{traceId}`.

### 7.2 `RequestLoggingInterceptor` — one line in, one line out

```java
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    @Override public boolean preHandle(...) {
        request.setAttribute("startTime", System.currentTimeMillis());
        MDC.put("method", request.getMethod());
        MDC.put("path", request.getRequestURI());
        return true;
    }
    @Override public void afterCompletion(...) {
        long duration = System.currentTimeMillis() - (Long) request.getAttribute("startTime");
        log.info("⬅️ RESPONSE method={} path={} status={} duration={}ms", ...);
        MDC.clear();
    }
}
```

Registered in `WebConfig`:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RequestLoggingInterceptor interceptor;
    @Override public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
```

### 7.3 Service-level logging

- Use `@Slf4j` on the impl class.
- **One `log.info` at the top of each public method**, verb-first and low-noise:
  ```java
  log.info("Creating role: {}", roleDTO);
  log.info("Deactivating users with IDs: {}", userIds);
  ```
- Do **not** log inside tight loops, mappers, or specifications.
- Errors are logged only in `GlobalExceptionHandler` (`log.error("Unhandled exception", ex)`) — services throw; they do not log-and-throw.

---

## 8. DTOs

### 8.1 Single DTO per resource

The default: one `XxxDTO` used for both request and response.

```java
@Data
@Builder
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private TaskType taskType;
    private Long assignedTo;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
}
```

- On **create**, the client sends the writable fields and leaves `id`, timestamps, `createdBy`, `updatedBy` null.
- On **update**, the client sends only the fields it wants to change; the mapper's `updateEntity` copies over non-null values only.
- On **read**, the server populates everything.

Split into request-only + response-only DTOs **only when the two truly diverge** (auth is the standard example):

```java
public class LoginRequestDTO { String username; String password; }        // input
public class LoginResponseDTO { Long userId; String name; String email; Long roleId; String token; } // output
```

### 8.2 Small ad-hoc request DTOs

When an endpoint takes a payload that is not a resource (e.g. "a list of user IDs to deactivate"), create a purpose-built class:

```java
@Data @NoArgsConstructor @AllArgsConstructor
public class UserIdsRequest {
    List<Long> userIds;
}
```

Keep them focused — do not fold them into an unrelated resource DTO.

### 8.3 Envelope response DTO

Every listing endpoint returns a `ListResponseDTO<T>`:

```java
@Data @Builder @JsonInclude(JsonInclude.Include.NON_NULL) @JsonIgnoreProperties(ignoreUnknown = true)
public class ListResponseDTO<T> {
    private Long total;
    private Map<String, Long> entityTypeCounts;
    private Map<String, Long> entityStateCounts;
    private Map<String, Object> groupedEntityCounts;
    private Map<String, Object> additionalMetadata;
    private List<T> records;
}
```

`@JsonInclude(NON_NULL)` keeps the payload tidy — fields the endpoint didn't populate are omitted.

### 8.4 DTO annotations checklist

| Annotation                    | When                                                       |
|-------------------------------|------------------------------------------------------------|
| `@Data`                       | Always                                                     |
| `@Builder` / `@SuperBuilder`  | Always (use `@SuperBuilder(toBuilder = true)` for children of a parent DTO) |
| `@NoArgsConstructor`          | If Jackson needs it (i.e. deserialized from JSON)          |
| `@AllArgsConstructor`         | Whenever `@Builder` is present                             |
| `@JsonInclude(NON_NULL)`      | On response envelopes and other partial-fill DTOs          |
| `@JsonIgnoreProperties(...)`  | On response DTOs to survive schema drift                   |
| `@EqualsAndHashCode(callSuper = true)` | When extending another DTO                        |

### 8.5 Validation

- Validation annotations (`@NotBlank`, `@Email`, `@Size`, `@Min`, `@Max`, `@Pattern`) live on the DTO field.
- Every message uses `{value}` / `{max}` placeholders so the config is self-documenting.
- Controllers add `@Valid` to trigger validation.

```java
@NotBlank(message = "Email must not be blank")
@Email(message = "Email must be valid")
private String username;
```

---

## 9. `ParamRequest` and `ListingRequestDto`

All list / search endpoints accept **one** query-parameter-bound object.

### 9.1 `ListingRequestDto` — pagination & sorting

```java
@Data @SuperBuilder(toBuilder = true) @AllArgsConstructor @NoArgsConstructor
public class ListingRequestDto {
    @Size(max = 50)                                          private String search;
    @Builder.Default @Min(1)                                 private Integer page = 1;
    @Builder.Default @Min(1) @Max(100)                       private Integer size = 10;
    @Size(max = 100)                                         private String sortBy;
    @Builder.Default @Pattern(regexp = "(?i)(asc|desc)$")    private String sortOrder = "desc";
    private Long loggedInUserId;
    private String fromDate;
    private String toDate;
    private String direction;
}
```

### 9.2 `ParamRequest` — domain filters

`ParamRequest` **extends** `ListingRequestDto` and adds all cross-cutting filter fields the app uses:

```java
@EqualsAndHashCode(callSuper = true)
@Data @SuperBuilder(toBuilder = true) @AllArgsConstructor @NoArgsConstructor
public class ParamRequest extends ListingRequestDto {
    private List<String> entityState;
    private List<String> entityType;
    private Boolean records = Boolean.FALSE;
    private Boolean active;
    private Long userRoleId;
    private String search;
    private boolean activateUser;
    // ... domain-specific optional filters (taskLinkType, taskId, ...)

    public boolean isRecords() { return Boolean.TRUE.equals(records); }
}
```

**Rules**
- One `ParamRequest` per project — do not create `TaskFilterRequest`, `UserFilterRequest`, etc. Add the field to `ParamRequest` and only the services that care read it.
- `records=false` (the default) means "give me only the count" — an optimization for UIs that show pagination totals without loading data. Services must respect it:
  ```java
  List<TaskDTO> records = List.of();
  if (request.isRecords()) { /* load and map */ }
  return ListResponseDTO.<TaskDTO>builder().total(repo.count(spec)).records(records).build();
  ```
- Provide safe defaults: `page=1`, `size=10`, `sortOrder=desc`.
- Pagination is 1-based on the wire, 0-based to Spring — `CommonUtils.buildPageRequest()` performs the translation.

### 9.3 `CommonUtils.buildPageRequest`

```java
public static Pageable buildPageRequest(ParamRequest request) {
    int page = Math.max(request.getPage() - 1, 0);
    int size = request.getSize();
    if (StringUtils.isNotBlank(request.getSortBy())) {
        Sort sort = DEFAULT_ORDER.equalsIgnoreCase(request.getDirection())
                ? Sort.by(request.getSortBy()).descending()
                : Sort.by(request.getSortBy()).ascending();
        return PageRequest.of(page, size, sort);
    }
    return PageRequest.of(page, size, Sort.by(DEFAULT_SORT).descending());
}
```

Every list service calls this helper — never construct a `PageRequest` inline.

---

## 10. Mappers

Mappers convert between entities and DTOs. **Static** methods on a **final** utility class with a private constructor — no Spring bean, no state, no injection.

```java
public class TaskMapper {

    private TaskMapper() {}

    public static TaskDTO toDto(Task task) {
        if (task == null) return null;
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                // ...
                .build();
    }

    public static Task toEntity(TaskDTO dto) {
        if (dto == null) return null;
        return Task.builder()
                .title(dto.getTitle())
                // ...
                .build();
    }

    public static void updateEntity(Task task, TaskDTO dto) {
        if (dto.getTitle() != null)       task.setTitle(dto.getTitle());
        if (dto.getDescription() != null) task.setDescription(dto.getDescription());
        // ... only copy non-null fields — this is a PATCH-style merge
    }
}
```

**Rules**
- Three canonical methods: `toDto(entity)`, `toEntity(dto)`, `updateEntity(entity, dto)`.
- **Both** `toDto` and `toEntity` return `null` on `null` input — no exceptions.
- `updateEntity` copies **only non-null** fields — this is what enables `PATCH` semantics.
- `toEntity` does **not** set `id`, timestamps, `createdBy`, `updatedBy`, or `deletedBy` — those are set by the service (or JPA callbacks).
- Never call the repository or another service from a mapper.
- Never mix mapper responsibilities — `TaskMapper` handles only `Task ⇄ TaskDTO`.

---

## 11. Specifications

JPA `Specification` builders live in their own class per entity. Each builder is a `static` method returning a `Specification<T>`.

```java
public class TaskSpecification {

    public static Specification<Task> entityStateIn(List<String> statuses) {
        return (root, query, cb) -> {
            if (statuses == null || statuses.isEmpty()) return cb.conjunction();
            List<TaskStatus> enums = statuses.stream().map(TaskStatus::valueOf).toList();
            return root.get("status").in(enums);
        };
    }

    public static Specification<Task> search(String search) {
        return (root, query, cb) -> {
            if (search == null || search.trim().isEmpty()) return cb.conjunction();
            String keyword = "%" + search.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), keyword),
                    cb.like(cb.lower(root.get("description")), keyword)
            );
        };
    }
}
```

**Rules**
- **Always return `cb.conjunction()` when the filter is absent** — this makes composing them with `Specification.allOf(...)` safe.
- Predicate name matches the field or intent: `hasActiveStatus`, `hasRoleId`, `entityStateIn`, `search`.
- Text search is case-insensitive: lower-case both sides and wrap in `%…%`.
- Convert strings to enums inside the specification — the controller/DTO stays string-based, the DB stays enum-based.
- The service composes them:
  ```java
  Specification<Task> spec = Specification.allOf(
          TaskSpecification.entityStateIn(request.getEntityState()),
          TaskSpecification.search(request.getSearch())
  );
  ```
- The repository must implement `JpaSpecificationExecutor<Entity>` to accept them.

---

## 12. Entities

- Extend a shared `BaseEntity`:

  ```java
  @Data @MappedSuperclass
  public abstract class BaseEntity {
      @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
      @Column(updatable = false) private LocalDateTime createdAt;
      private LocalDateTime updatedAt;

      @PrePersist public void prePersist() { createdAt = LocalDateTime.now(); }
      @PreUpdate  public void preUpdate()  { updatedAt = LocalDateTime.now(); }
  }
  ```
- Table names in the annotation are plural snake_case (`@Table(name = "tasks")`); the entity class is singular PascalCase (`Task`).
- Enums are stored as strings: `@Enumerated(EnumType.STRING)`.
- Boolean fields default to a sensible value using `@Builder.Default`:
  ```java
  @Builder.Default @Column(name = "is_active") private boolean active = true;
  ```
- Audit fields (`createdBy`, `updatedBy`, `deletedBy`) live on the entity and are set by the service using the current user's id (looked up via `userService.getUserByEmailInternal(userEmail)`).
- Standard Lombok stack on every entity: `@Data @Builder @NoArgsConstructor @AllArgsConstructor`.

---

## 13. Repositories

- Extend both `JpaRepository<Entity, Long>` **and** `JpaSpecificationExecutor<Entity>` — the latter is what makes specifications work.
- Annotate with `@Repository` (Spring wires it either way, but the annotation documents intent).
- Prefer derived query methods (`findByEmail`, `existsByEmail`, `findByStatusAndPriority`) over `@Query` when possible.
- Reach for `@Query` only when the derived name would be unwieldy or when you need a projection:
  ```java
  @Query("SELECT u.id FROM User u WHERE u.roleId = :roleId")
  List<Long> findAllUserIdsByRoleId(Long roleId);
  ```
- Return `Optional<T>` for single-item lookups, never `null`.

---

## 14. Services — Interface + Impl split

Every service is a **public contract** (`interface`) with a **single implementation** (`Impl` class in the `impl/` subpackage).

```
service/
├── serviceInterface/
│   ├── TaskService.java
│   └── UserService.java
└── impl/
    ├── TaskServiceImpl.java
    └── UserServiceImpl.java
```

### The interface

- Declares only what callers need — no implementation detail.
- Method names read like English sentences: `createTask(TaskDTO, String userEmail)`, `deactivateUsersByUserIds(List<Long>, String userEmail)`, `getUserByEmailInternal(String)`.
- Include an `Internal` suffix for cross-service methods that skip caller-facing checks (they still throw `BusinessException`, but they don't repeat `getUserByEmailInternal` themselves).

### The impl

Standard annotations:
```java
@Service
@RequiredArgsConstructor   // or @AllArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService { ... }
```

**Method structure**

1. `log.info("...")` — one line, verb-first.
2. **Resolve the caller.** `UserDTO caller = userService.getUserByEmailInternal(userEmail);`
3. **Guard inputs.** `if (id == null || id <= 0) throw new BusinessException(BAD_REQUEST, "…");`
4. **Load referenced resources.** `.orElseThrow(() -> new BusinessException(RESOURCE_NOT_FOUND, "…"))`.
5. **Domain checks / uniqueness.** `if (repo.existsByEmail(...)) throw new BusinessException(DUPLICATE_RESOURCE, "…");`
6. **Mutate + persist.** Set audit fields from `caller.getId()`.
7. **Map back to DTO** and return.

**Rules**
- Depend on **other service interfaces**, not on other repositories directly — this preserves boundaries and keeps `@PreAuthorize` / permission checks in one place.
- Use `@Transactional` (from `jakarta.transaction`) on methods that touch multiple aggregates or fan out to other services.
- Use `@PreAuthorize` on methods that require an authenticated role:
  ```java
  @PreAuthorize("hasAnyRole('ADMIN', 'BACKEND_DEVELOPER')")
  public ListResponseDTO<TaskDTO> getAllTasks(ParamRequest request, String userEmail) { ... }
  ```
- Keep validation helpers private and split by intent: `validateTask(TaskDTO)`, `validateTaskUpdate(TaskDTO)`, `validateAssignee(Long)`.
- **Side-effects (email, notifications) go through their own service** and are called from small, well-named private methods: `sendUserCreatedEmail(user)`.

---

## 15. Security

- `@EnableMethodSecurity` on the security config to enable `@PreAuthorize` on service methods.
- Stateless JWT: `SessionCreationPolicy.STATELESS`, custom `OncePerRequestFilter` (`JwtAuthenticationFilter`) placed before `UsernamePasswordAuthenticationFilter`.
- The auth entry point returns a hand-written `BaseApiResponse`-shaped JSON body so the client sees the same envelope for auth failures as for anything else.
- The security-related service pair (`JwtService`, `TaskUserDetailsService`) follows the same interface/impl split under `config/security/service/`.
- The current caller's email is passed through the API via `X-User` (constant `HEADER_USERID`) so the service can look them up in one place.

---

## 16. Configuration

- One `@Configuration` class per concern: `SecurityConfig`, `AsyncConfig`, `WebConfig`, `SwaggerConfig`, `EmailProperties`, `JwtProperties`.
- Named executors for async work — do **not** use the default `SimpleAsyncTaskExecutor`:
  ```java
  @Bean(name = "emailTaskExecutor")
  public Executor emailTaskExecutor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(2);
      executor.setMaxPoolSize(5);
      executor.setQueueCapacity(100);
      executor.setThreadNamePrefix("email-task-");
      executor.setWaitForTasksToCompleteOnShutdown(true);
      executor.setAwaitTerminationSeconds(30);
      executor.initialize();
      return executor;
  }
  ```
- Externalize secrets via `@ConfigurationProperties` (e.g. `JwtProperties`, `EmailProperties`) — never read `application.yml` values ad-hoc with `@Value` inside services.

---

## 17. Constants

- All string / numeric literals that would otherwise appear more than once live in `common/utils/Constants.java`.
- Static-import them in the classes that use them:
  ```java
  import static com.<org>.<app>.common.utils.Constants.HEADER_USERID;
  ```
- Group logically with comment banners: `// EMAIL constants —`, `// ROLE constants —`.
- Never hard-code header names (`"X-User"`), sort defaults (`"createdAt"`, `"DESC"`), or message templates in a service or controller.

---

## 18. Enums

- One file per enum, in `enums/`.
- Constant names in `SCREAMING_SNAKE_CASE`: `PENDING`, `IN_PROGRESS`, `DONE`.
- The enum name is a singular noun: `TaskStatus`, `TaskPriority`, `TaskType`, `TaskLinkType`, `TaskActivityType`, `EmailTemplate`.
- Enums used in DB columns must be persisted as `@Enumerated(EnumType.STRING)` on the entity — never `ORDINAL`, because reordering values would silently corrupt data.

---

## 19. What every request looks like end-to-end

Following one `POST /tasks` through the stack:

```
Client
  │  X-User: alice@example.com
  │  Authorization: Bearer <jwt>
  ▼
TraceIdFilter                → puts traceId into MDC, adds X-Trace-Id header
JwtAuthenticationFilter      → validates token, populates SecurityContext
RequestLoggingInterceptor    → records start time, method, path
TaskController.createTask
  └─ TaskService (interface) — @PreAuthorize checked here
      └─ TaskServiceImpl.createTask
          ├─ log.info("creating task")
          ├─ validateTask(dto), validateAssignee(dto.assignedTo)
          ├─ userService.getUserByEmailInternal(userEmail)  → caller
          ├─ TaskMapper.toEntity(dto)
          ├─ task.setCreatedBy(caller.getId())
          ├─ taskRepository.save(task)
          └─ return TaskMapper.toDto(task)
Controller wraps in BaseApiResponse (201 CREATED)
RequestLoggingInterceptor    → logs "⬅️ RESPONSE method=POST path=/tasks status=201 duration=42ms"
TraceIdFilter                → clears MDC
```

If anything throws, `GlobalExceptionHandler` converts it into the same `BaseApiResponse<Void>` shape and the client never sees a stack trace.

---

## 20. Quick "smell" checklist for code review

- [ ] Every controller method returns `ResponseEntity<BaseApiResponse<...>>`.
- [ ] Every business failure throws `BusinessException(ErrorCode, reason)`.
- [ ] No `null` returns from services — throw or return an empty envelope.
- [ ] Every service has a matching interface in `serviceInterface/`.
- [ ] No `@Autowired` field injection — constructor injection only.
- [ ] Mappers are static, stateless, and return `null` on `null` input.
- [ ] Specifications return `cb.conjunction()` when the filter is absent.
- [ ] List endpoints accept a `ParamRequest`, respect `isRecords()`, and return `ListResponseDTO<T>`.
- [ ] Method names are English sentences; no acronyms or single-word verbs like `handle`, `process`, `do`.
- [ ] String literals used more than once live in `Constants`.
- [ ] `@Slf4j` on service impls, one `log.info(...)` per public method entry, errors logged only in `GlobalExceptionHandler`.
- [ ] Enums are `EnumType.STRING` in JPA, `SCREAMING_SNAKE_CASE` constants.
- [ ] Audit fields (`createdBy`, `updatedBy`, `deletedBy`) are set from the resolved caller, never from the request payload.

---

*Follow these conventions in every new module. When in doubt, mirror the existing shape — consistency across the codebase beats individual cleverness.*
