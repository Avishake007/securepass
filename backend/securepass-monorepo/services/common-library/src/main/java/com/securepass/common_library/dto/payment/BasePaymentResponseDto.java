package com.securepass.common_library.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePaymentResponseDto {
	private String responseCode;
	private String responseStatus;
}
