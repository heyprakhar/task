You’re right. You want one single outer ```text code block containing the entire README, with no nested code blocks inside it. Here is the full updated README exactly in that format:

# 📌 Project Checkpoints
This document contains important project checkpoints that can be used to restore the application to specific development stages.
Each checkpoint represents a stable milestone in the development of the TASKK backend.
---
## ✅ Checkpoint 1 — TMS Without Spring Security
> **Branch:** `checkpoint/tms-without-spring-security`
### 📖 Description
This checkpoint represents the project **before the integration of Spring Security**.
At this stage:
- ✅ Core project structure is set up.
- ✅ Application is fully functional without Spring Security.
- ✅ No authentication or authorization is implemented.
- ✅ APIs are accessible without JWT authentication.
- ✅ Existing application-level mechanisms are still used.
- ✅ Ideal starting point for understanding and implementing Spring Security from scratch.
### 🌿 Checkout
    git checkout checkpoint/tms-without-spring-security
---
## 🔐 Checkpoint 2 — JWT Authentication Before RBAC
> **Branch:** `checkpoint/security-jwt-before-rbac`
### 📖 Description
This checkpoint represents the stage where **JWT-based authentication has been completely implemented**, but **role-based authorization and granular RBAC have not yet been implemented**.
At this stage:
- ✅ `PasswordEncoder`
- ✅ `BCryptPasswordEncoder`
- ✅ `CustomUserDetails`
- ✅ `UserDetailsService`
- ✅ `DaoAuthenticationProvider`
- ✅ `AuthenticationManager`
- ✅ Login API
- ✅ Login request/response DTOs
- ✅ JWT generation
- ✅ JWT validation
- ✅ `JwtAuthenticationFilter`
- ✅ `SecurityContext`
- ✅ Stateless session management
- ✅ Custom `AuthenticationEntryPoint`
- ✅ First protected endpoint: `/tasks`
- ✅ Valid JWT authentication
- ✅ Invalid/tampered JWT rejection
- ✅ Unauthenticated requests return `401`
### 🚫 Not Included
This checkpoint does **not** contain:
- ❌ Role-based authorization
- ❌ `GrantedAuthority` based on roles
- ❌ `hasRole()`
- ❌ `hasAnyRole()`
- ❌ `@PreAuthorize`
- ❌ Granular permissions
- ❌ `Permission` entity
- ❌ `RolePermission` entity
- ❌ Granular RBAC
### 🌿 Checkout
    git checkout checkpoint/security-jwt-before-rbac
---
## 🛡️ Checkpoint 3 — Role & Method-Level Authorization
> **Branch:** `checkpoint/security-role-method-authorization`
### 📖 Description
This checkpoint represents the stage where **JWT authentication, role-based authorization, and method-level authorization are implemented**.
At this stage:
- ✅ JWT authentication
- ✅ Stateless JWT security
- ✅ `CustomUserDetails`
- ✅ `UserDetailsService`
- ✅ `DaoAuthenticationProvider`
- ✅ `AuthenticationManager`
- ✅ `JwtAuthenticationFilter`
- ✅ `SecurityContext`
- ✅ User role loaded using `User.roleId`
- ✅ Role loaded from the database
- ✅ Role converted to Spring Security `GrantedAuthority`
- ✅ `ROLE_ADMIN`, `ROLE_USER`, etc.
- ✅ Role active/inactive state considered
- ✅ User active/inactive state considered
- ✅ URL-level authentication with `.authenticated()`
- ✅ Method-level security with `@EnableMethodSecurity`
- ✅ `@PreAuthorize`
- ✅ `hasRole()`
- ✅ `hasAnyRole()`
- ✅ `hasAuthority()`
- ✅ Custom `403 Forbidden` response
- ✅ Custom `401 Unauthorized` response
### 🧪 Verified Authorization Behavior
- No JWT → `401 Authentication required`
- Valid JWT + ADMIN → `200 Success`
- Valid JWT + USER accessing ADMIN-only method → `403 Forbidden`
- Tampered JWT → `401 Authentication required`
### 🚫 Not Included
This checkpoint does **not** contain granular RBAC.
Specifically:
- ❌ `Permission` entity
- ❌ `RolePermission` entity
- ❌ Role-to-permission mapping
- ❌ Permission authorities such as `TASK_CREATE`
- ❌ Granular permission-based authorization
- ❌ Resource-level authorization
- ❌ OAuth 2.0 / OpenID Connect
### 🌿 Checkout
    git checkout checkpoint/security-role-method-authorization
---
## 🗺️ Security Development Roadmap
The checkpoints currently represent the following progression:
    Checkpoint 1
    checkpoint/tms-without-spring-security
            ↓
    Application without Spring Security
            ↓
    Checkpoint 2
    checkpoint/security-jwt-before-rbac
            ↓
    JWT Authentication
            ├── Login
            ├── JWT generation
            ├── JWT validation
            ├── SecurityContext
            └── First protected API
            ↓
    Checkpoint 3
    checkpoint/security-role-method-authorization
            ↓
    Role-Based Authorization
            ├── ROLE_ADMIN
            ├── ROLE_USER
            ├── hasRole()
            ├── hasAnyRole()
            ├── hasAuthority()
            └── @PreAuthorize
            ↓
    NEXT
    Granular RBAC
            ├── Permission
            ├── RolePermission
            ├── TASK_CREATE
            ├── TASK_VIEW
            ├── TASK_UPDATE
            └── TASK_DELETE
            ↓
    Resource-Level Authorization
            ↓
    JWT Production Hardening
            ↓
    OAuth 2.0 / OpenID Connect
---
## 🔄 Checkpoint Strategy
New checkpoints should be created whenever a major security learning milestone is completed.
The goal is to keep each checkpoint:
- Stable
- Understandable
- Restorable
- Independent of future security changes
This allows the project to safely move forward while preserving previous learning stages.
---
## 📊 Current Progress
[✓] Application without Spring Security
[✓] Password hashing
[✓] CustomUserDetails
[✓] UserDetailsService
[✓] AuthenticationManager
[✓] DaoAuthenticationProvider
[✓] Login
[✓] JWT generation
[✓] JWT request authentication
[✓] Stateless security
[✓] First protected API
[✓] AuthenticationEntryPoint
[✓] Role-based authorization
[✓] Method-level authorization
[✓] hasRole()
[✓] hasAnyRole()
[✓] hasAuthority()
[✓] 401 / 403 handling
[ ] Granular RBAC
[ ] Permission model
[ ] Role-Permission mapping
[ ] Permission authorities
[ ] Resource-level authorization
[ ] JWT production hardening
[ ] OAuth 2.0 / OIDC
---
## 📌 Current Development Stage
The project is currently at:
    checkpoint/security-role-method-authorization
The next major security milestone is:
    Granular RBAC
The next implementation should introduce:
    Role
      ↓
    RolePermission
      ↓
    Permission
      ↓
    GrantedAuthority
      ↓
    @PreAuthorize("hasAuthority('TASK_CREATE')")
No OAuth 2.0 or resource-level authorization should be implemented until the granular RBAC stage is understood and completed.
