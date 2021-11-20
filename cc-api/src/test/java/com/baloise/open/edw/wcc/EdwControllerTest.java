/*
 * Copyright 2021 Baloise Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baloise.open.edw.wcc;

import com.baloise.open.edw.wcc.dto.StatusDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class EdwControllerTest {
  @Test
  void getCurrentServerTime() {
    EdwService serviceMock = mock(EdwService.class);
    when(serviceMock.getEdwStatus()).thenReturn(Collections.singletonList(new StatusDto(1L, "cid", "eventType", "value")));
    EdwController edwController = new EdwController(serviceMock);

    List<StatusDto> statusList = edwController.getEdwStatus();

    assertNotNull(statusList);
    assertEquals(1, statusList.size());

    StatusDto status = statusList.get(0);
    assertAll(() -> {
      assertEquals("cid", status.getCid());
      assertEquals("value", status. getValue());
      assertEquals("eventType", status.getEventType());
      assertEquals(1, status.getTimestamp());
    });
  }
}
