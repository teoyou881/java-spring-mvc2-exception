package hello.exception.api;

import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class ApiExceptionController {

  @GetMapping("/api/members/{id}")
  public MemberDto getMember(@PathVariable String id) {
    if (id.equals("ex")) {
      throw new RuntimeException("wrong user");
    }
    if (id.equals("bad")) {
      throw new IllegalArgumentException("wrong value");
    }
    if (id.equals("user-ex")) {
      throw new UserException("user error");
    }
    return new MemberDto(id, "Hi " + id);
  }

  @GetMapping("/api/response-status-ex1")
  public String responseStatusEx1() throws BadRequestException {
    throw new BadRequestException();
  }

  @GetMapping("/api/response-status-ex2")
  public ResponseEntity<?> responseStatusEx2() {
    log.info("/api/response-status-ex2");
    throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
  }

  @GetMapping("/api/default-handler-ex")
  public String defaultException(@RequestParam Integer data) {
    return "ok";
  }

  @Data
  @AllArgsConstructor
  static class MemberDto {

    private String memberId;
    private String name;
  }

}
