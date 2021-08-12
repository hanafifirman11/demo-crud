package com.firman.demo.crud.controller;

import com.firman.demo.crud.config.BaseIT;
import com.firman.demo.crud.dto.BaseResponseDTO;
import com.firman.demo.crud.dto.UserDTO;
import com.firman.demo.crud.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@BaseIT
@Slf4j
public class UserControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    protected int port;

    private StringBuilder getBaseUrl(){
        return new StringBuilder("http://localhost:")
            .append(port)
            .append("/crud");
    }

    private URI getUri(){
        return URI.create(getBaseUrl().append("/user").toString());
    }

    private URI getUriById(String id){
        return URI.create(getBaseUrl().append("/user/").append(id).toString());
    }

    private HttpHeaders getHeader()  {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    @Test
    @Sql(value = {"classpath:db/DataTest_general.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:db/Truncate.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get All User - Success")
    void getAllUserSuccess() {

        var response = testRestTemplate.exchange(getUri(), HttpMethod.GET, new HttpEntity<>(null, getHeader()), new ParameterizedTypeReference<BaseResponseDTO<List<UserDTO>>>() {});
        log.info("response : {}", response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("0", response.getBody().getStatus());
        assertNotNull(response.getBody().getData());
        assertTrue(response.getBody().getData().size() == 1);

        var userDto = response.getBody().getData().get(0);
        assertEquals("firmanh@gmail.com", userDto.getEmail());

    }

    @Test
    @Sql(value = {"classpath:db/DataTest_general.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:db/Truncate.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get User By Id - Success")
    void getUserByIdSuccess() {

        var response = testRestTemplate.exchange(getUriById("1"), HttpMethod.GET, new HttpEntity<>(null, getHeader()), new ParameterizedTypeReference<BaseResponseDTO<UserDTO>>() {});
        log.info("response : {}", response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("0", response.getBody().getStatus());
        assertNotNull(response.getBody().getData());

        var userDto = response.getBody().getData();
        assertEquals("firmanh@gmail.com", userDto.getEmail());

    }

    @Test
    @DisplayName("Get User By Id - User not found")
    void getUserByIdUserNotFound() {

        var response = testRestTemplate.exchange(getUriById("1"), HttpMethod.GET, new HttpEntity<>(null, getHeader()), new ParameterizedTypeReference<BaseResponseDTO>() {});
        log.info("response : {}", response);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEnum.USER_NOT_FOUND.getStatus(), response.getBody().getStatus());
        assertEquals(ResponseEnum.USER_NOT_FOUND.getMessage(), response.getBody().getMessage());
        assertNull(response.getBody().getData());

    }

    @Test
    @Sql(value = {"classpath:db/DataTest_general.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:db/Truncate.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete User By Id - Success")
    void deleteUserByIdSuccess() {

        var response = testRestTemplate.exchange(getUriById("1"), HttpMethod.DELETE, new HttpEntity<>(null, getHeader()), new ParameterizedTypeReference<BaseResponseDTO>() {});
        log.info("response : {}", response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("0", response.getBody().getStatus());

    }

    @Test
    @DisplayName("Delete User By Id - User not found")
    void deleteUserByIdUserNotFound() {

        var response = testRestTemplate.exchange(getUriById("1"), HttpMethod.GET, new HttpEntity<>(null, getHeader()), new ParameterizedTypeReference<BaseResponseDTO>() {});
        log.info("response : {}", response);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEnum.USER_NOT_FOUND.getStatus(), response.getBody().getStatus());
        assertEquals(ResponseEnum.USER_NOT_FOUND.getMessage(), response.getBody().getMessage());

    }
}
