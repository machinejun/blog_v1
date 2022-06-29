package com.tencoding.blog.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoProfile {
	private long id;
	private String connectat;
	private Properties properties;
	private Account kakaoAccount;
	

	@Data
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public class Properties{
		private String nickname;
		private String profileImage;
		private String thumbnailImage;
	}
	
	@Data
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public class Account{
		private boolean profileNicknameNeedsAgreement;
		private boolean hasEmail;
		private boolean emailNeedsAgreement;
		private boolean isEmailValid;
		private boolean isEmailVerified;
		private String email;
	}

}
