package com.taskflow.taskk.controller;


import com.taskflow.taskk.common.response.BaseApiResponse;
import com.taskflow.taskk.dto.RoleDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.RoleService;
import com.taskflow.taskk.service.serviceInterface.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.taskflow.taskk.common.utils.Constants.HEADER_USERID;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<BaseApiResponse<ListResponseDTO<RoleDTO>>> getAllRoles(@RequestHeader(HEADER_USERID) String userEmail, ParamRequest request){

        ListResponseDTO<RoleDTO> response = roleService.getAllRoles(userEmail, request);

        return ResponseEntity.ok(
                BaseApiResponse.<ListResponseDTO<RoleDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("All roles fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<RoleDTO>> getRoleById(@RequestHeader(HEADER_USERID) String userEmail , @PathVariable Long id){

        RoleDTO response = roleService.getRoleById(userEmail, id);

        return ResponseEntity.ok(
                BaseApiResponse.<RoleDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Role fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<BaseApiResponse<RoleDTO>> createRole(@RequestHeader(HEADER_USERID) String userEmail, @RequestBody RoleDTO roleDTO){

        RoleDTO response = roleService.createRole(userEmail, roleDTO);

        return ResponseEntity.ok(
                BaseApiResponse.<RoleDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Role created successfully")
                        .data(response)
                        .build()
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<RoleDTO>> updateRole(@RequestHeader(HEADER_USERID) String userEmail, @PathVariable Long id, @RequestBody RoleDTO roleDTO){

        RoleDTO response = roleService.updateRole(userEmail, id, roleDTO);

        return ResponseEntity.ok(
                BaseApiResponse.<RoleDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Role updated successfully")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<BaseApiResponse<RoleDTO>> deactivateRole(@RequestHeader(HEADER_USERID) String userEmail,@PathVariable Long id){

        RoleDTO response = roleService.deactivateRole(userEmail, id);

        return ResponseEntity.ok(
                BaseApiResponse.<RoleDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Role deactivated successfully")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<BaseApiResponse<RoleDTO>> activateRole(@RequestHeader(HEADER_USERID) String userEmail,@PathVariable Long id, ParamRequest request){

        RoleDTO response = roleService.activateRole(userEmail, id, request);

        return ResponseEntity.ok(
                BaseApiResponse.<RoleDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Role activated successfully")
                        .data(response)
                        .build()
        );
    }
}
