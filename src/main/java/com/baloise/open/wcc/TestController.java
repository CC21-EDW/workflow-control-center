package com.baloise.open.wcc;

import com.baloise.open.wcc.dto.DateDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController()
@RequestMapping(path = "/api")
public class TestController {
  @GetMapping(path = "/get-time")
  public DateDto helloWorld() {
    return DateDto.builder().date(new Date()).build();
  }
}
