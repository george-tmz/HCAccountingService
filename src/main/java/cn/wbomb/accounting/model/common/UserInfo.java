package cn.wbomb.accounting.model.common;


import lombok.Builder;
import lombok.Data;

/**
 * @author George
 */
@Data
@Builder
public class UserInfo {
    private Long id;
    private String username;
    private String password;
}
