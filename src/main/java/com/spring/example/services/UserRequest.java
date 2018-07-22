package com.spring.example.services;

import lombok.*;
import lombok.experimental.Wither;

/**
 * Created by adnenehamdouni on 20/07/2018.
 */

@Builder
@Wither
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserRequest {

    private long id;
    private String name;
    private boolean isProfessionnal;
}
