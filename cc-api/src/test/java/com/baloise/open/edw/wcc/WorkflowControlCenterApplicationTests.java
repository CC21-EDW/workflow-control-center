package com.baloise.open.edw.wcc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class WorkflowControlCenterApplicationTests {
  @Test
  void test() {
    assertDoesNotThrow(() -> WorkflowControlCenterApplication.main(new String[]{
        "--spring.main.web-environment=false",
        // Override any other environment properties according to needs
    }));
  }
}
