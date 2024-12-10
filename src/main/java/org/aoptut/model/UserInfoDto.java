package org.aoptut.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.aoptut.entities.UserInfo;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto extends UserInfo {

    private String firstName;

    private String lastName; // user_name
    private Long phoneNumber;

    private String email;


}
