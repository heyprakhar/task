package com.taskflow.taskk.controller;


import com.taskflow.taskk.common.response.BaseApiResponse;
import com.taskflow.taskk.dto.RoleDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.RoleService;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<String>> deleteRole(@RequestHeader(HEADER_USERID) String userEmail, @PathVariable Long id){

        String response = roleService.deleteRole(userEmail, id);

        return ResponseEntity.ok(
                BaseApiResponse.<String>builder()
                        .status(HttpStatus.OK.value())
                        .message("Role deleted successfully")
                        .data(response)
                        .build()
        );
    }
}
