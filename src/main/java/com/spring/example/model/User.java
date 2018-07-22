package com.spring.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Wither;

/**
 * Created by adnenehamdouni on 19/07/2018.
 */

@Setter
@Getter
@NoArgsConstructor
@Builder
@Wither
public class User extends MaSerializable {

    private long id;
    private String name;
    private boolean isProfessionnal;

    public User(long id, String name, boolean isProfessionnal) {
        this.id = id;
        this.name = name;
        this.isProfessionnal = isProfessionnal;
    }
}
