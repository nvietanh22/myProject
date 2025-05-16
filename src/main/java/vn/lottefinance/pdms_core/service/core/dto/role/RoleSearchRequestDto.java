package vn.lottefinance.pdms_core.service.core.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.lottefinance.pdms_core.config.SearchRequestBase;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSearchRequestDto extends SearchRequestBase {
    private String roleName;
}
