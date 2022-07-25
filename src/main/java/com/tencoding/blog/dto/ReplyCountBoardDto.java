package com.tencoding.blog.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyCountBoardDto {
	private int id;
	private String content;
//	private Timestamp createDate;
//	private Integer boardId;
//	private Integer userId;
	private int replyCount;
	
	// 커스텀 
	public ReplyCountBoardDto(Object[] objs){
		this.id = ((Integer)objs[0]).intValue();
		this.content = ((String) objs[1]);
		this.replyCount= ((BigInteger) objs[2]).intValue();
	}
	
	public ReplyCountBoardDto(Integer id, String content, BigInteger replyCount) {
		this.id = id.intValue();
		this.content = content;
		this.replyCount = replyCount.intValue();
		
	}
	
}
