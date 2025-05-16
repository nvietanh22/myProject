package vn.lottefinance.pdms_core.config;

import lombok.Data;

@Data
public abstract class SearchRequestBase {
	private int page;
	private int rowsPerPage;
}
