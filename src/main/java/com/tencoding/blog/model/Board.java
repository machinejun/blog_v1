package com.tencoding.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 100)
	private String title;

	@Lob // 대용량 데이터를 뜻한다.
	private String content;

	@ColumnDefault("0") // 인트형이다 >>> String을 사용하고 싶을때는 "'안녕'" 이렇게 써야한다.
	private int count; // 조회수

	@CreationTimestamp
	private Timestamp createDate;

	// Many <-- 자기자신 , One <-- User >>> 여러개의 게시글은 하나의 유저를 가진다.
	// 하나의 작성글에 두개 이상의 유저를 가질수가 없다.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User userId;
	// EAGER 전략 >> Board select 할때 한번에 데이터를 가지고 온다.
	// LAZY 전략 >> 필요할 떄 다시 요청해서 이놈만 들고 올 수 있다.

	// 댓글 정보 << 하나의 게시글에 여러개의 리플이 있을 수 있다.
	// one = Board, Many = reply
	// mappedBy = "board" 는 reply 테이블에 필드 이름이다.
	// mappedBy = 연관관계의 주인이 아니다. (FK가 아니다)
	// DB에 컬럼을 만들지 마세요

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private List<Reply> reply;

}
