package com.greedy.semi.free.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class FreeDTO {

	private Long freeNo;
	private String freeTitle;
	private String freeContent;
	private String freeDelete;
	private Date freeDate;
	private Date freeUpdate;
	private int freeCount;
	private Integer freeType;
	private String boardStatus;
}