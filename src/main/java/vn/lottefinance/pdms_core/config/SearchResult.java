package vn.lottefinance.pdms_core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResult<T> {
	private T data;
	private Long totalElements;
	private Integer totalPages;
	private String status = "200";
	private String message;
}
