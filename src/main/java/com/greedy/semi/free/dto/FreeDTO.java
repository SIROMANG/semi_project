package com.greedy.semi.free.dto;

import java.util.Date;

import com.greedy.semi.member.dto.MemberDTO;

import lombok.Data;

@Data
public class FreeDTO {

	private Long freeNo;
	private String freeTitle;
	private String freeContent;
	private String freeDelete;
	private Date freeDate;
	private Date freeUpdate;
	private MemberDTO memberId;
	private int freeCount;

}
