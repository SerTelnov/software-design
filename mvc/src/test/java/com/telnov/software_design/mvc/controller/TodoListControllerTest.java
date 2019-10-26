package com.telnov.software_design.mvc.controller;

import com.telnov.software_design.mvc.FunctionalTest;
import com.telnov.software_design.mvc.TestUtil;
import com.telnov.software_design.mvc.model.TodoDTO;
import com.telnov.software_design.mvc.model.TodoStatus;
import com.telnov.software_design.mvc.service.TodoListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoListControllerTest extends FunctionalTest {

    private static final String BASE_URL = "http://localhost";
    private static final String API_URL = "api/todo";
    private RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private TodoListService serviceMock;

    @BeforeEach
    void setup() {
        Mockito.when(serviceMock.notContainTodoId(Mockito.anyLong())).thenReturn(false);
        restTemplate = new RestTemplate();
    }

    @Test
    void getTodoListTest() {
        Mockito.when(serviceMock.getTodoList())
                .thenReturn(LongStream.of(1, 2)
                        .mapToObj(TestUtil::makeTodoEntity)
                        .collect(Collectors.toList())
                );

        String url = getUriBuilder()
                .path("/")
                .toUriString();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        String expected = /*language=JSON*/ "" +
                "[" +
                "  {" +
                "    \"id\": 1," +
                "    \"name\": \"TODO_1\"," +
                "    \"creationTime\": \"2019-10-26T02:15:30Z\"," +
                "    \"status\": \"BACKLOG\"" +
                "  }," +
                "  {" +
                "    \"id\": 2," +
                "    \"name\": \"TODO_2\"," +
                "    \"creationTime\": \"2019-10-26T02:15:30Z\"," +
                "    \"status\": \"BACKLOG\"" +
                "  }" +
                "]";

        Mockito.verify(serviceMock, Mockito.times(1))
                .getTodoList();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expected.replace(" ", ""));
    }

    @Test
    void addTodoTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<TodoDTO> requestBody = List.of(new TodoDTO("ToDo_DTO"));
        HttpEntity<List<TodoDTO>> entity = new HttpEntity<>(requestBody, headers);

        String url = getUriBuilder()
                .path("/add")
                .toUriString();

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        Mockito.verify(serviceMock, Mockito.times(1))
                .addTodo(Mockito.eq(requestBody));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void removeTodoTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Long> requestBody = List.of(1L);
        HttpEntity<List<Long>> entity = new HttpEntity<>(requestBody, headers);

        String url = getUriBuilder()
                .path("/remove")
                .toUriString();

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        Mockito.verify(serviceMock, Mockito.times(1))
                .removeTodo(Mockito.eq(requestBody));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void markTodoTest() {
        String url = getUriBuilder()
                .path("/mark")
                .queryParam("id", 1L)
                .queryParam("status", TodoStatus.DONE)
                .toUriString();

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        Mockito.verify(serviceMock, Mockito.times(1))
                .markTodo(1L, TodoStatus.DONE);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private UriComponentsBuilder getUriBuilder() {
        return UriComponentsBuilder.fromUriString(BASE_URL)
                .port(port)
                .path(API_URL);
    }
}
