package com.baloise.open.edw.wcc.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class StatusDto implements Serializable {
  Long timestamp;
  String cid;
  String eventType;
  String value;
}
