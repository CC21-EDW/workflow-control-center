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
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping(path = "/api")
@CrossOrigin(origins = "http://localhost:4200") // TODO
public class EdwController {
  private final EdwService service;

  public EdwController(EdwService service) {
    this.service = service;
  }

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = StatusDto.class)),
              mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping(path = "/status", produces = "application/json")
  public List<StatusDto> getEdwStatus() {
    return service.getEdwStatus();
  }
}
