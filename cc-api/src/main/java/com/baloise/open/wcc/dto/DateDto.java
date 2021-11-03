package com.baloise.open.wcc.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DateDto {
  private Date date;
}
