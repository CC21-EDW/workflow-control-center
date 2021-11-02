package com.baloise.open.wcc;

import com.baloise.open.wcc.dto.DateDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WorkflowControlCenterApplicationTests {
  @Test
  void getCurrentServerTime() {
    TestController tc = new TestController();
    DateDto currentServerTime = tc.getCurrentServerTime();
    assertNotNull(currentServerTime);
    assertNotNull(currentServerTime.getDate());
  }
}
