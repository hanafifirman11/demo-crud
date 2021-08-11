package com.firman.demo.crud.controller;

import com.firman.demo.crud.dto.BaseResponseDTO;
import com.firman.demo.crud.dto.UserDTO;
import com.firman.demo.crud.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Api(description = "API Crud User", value = "API Crud User", produces = "application/json", consumes = "application/json")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Get All User", produces = "application/json")
    @GetMapping
    public BaseResponseDTO<List<UserDTO>> user() {
        return new BaseResponseDTO<>(userService.findAll().stream().map(UserDTO::new).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Get User by Id", produces = "application/json")
    @GetMapping("/{id}")
    public BaseResponseDTO<UserDTO> user(
        @ApiParam(name = "id", value = "The Id of the User to be viewed", required = true)
        @PathVariable
            Long id) {
        return new BaseResponseDTO<>(userService.findUserById(id));
    }

    @ApiOperation(value = "Save User", produces = "application/json", consumes = "application/json")
    @PostMapping
    public BaseResponseDTO<UserDTO> save(
        @Validated
        @RequestBody
        @ApiParam(name = "body", value = "Body to send", required = true)
            UserDTO userDTO) {
        return new BaseResponseDTO<>(userService.saveUser(userDTO));
    }

    @ApiOperation(value = "Update User", produces = "application/json", consumes = "application/json")
    @PutMapping("/{id}")
    @SuppressWarnings("rawtypes")
    public BaseResponseDTO update(
        @Validated
        @RequestBody
            UserDTO userDTO,
        @ApiParam(name = "id", value = "The Id of the User to be updated", required = true)
        @PathVariable
            Long id) {
        userService.updateUser(id, userDTO);
        return new BaseResponseDTO();
    }

    @ApiOperation(value = "Delete User", produces = "application/json")
    @DeleteMapping("/{id}")
    @SuppressWarnings("rawtypes")
    public BaseResponseDTO delete(
        @ApiParam(name = "id", value = "The Id of the User to be deleted", required = true)
        @PathVariable
            Long id) {
        userService.deleteUser(id);
        return new BaseResponseDTO();
    }

}
