package com.spring.example.d1;

import com.spring.example.model.User;
import lombok.*;

import java.util.List;

/**
 * Created by adnenehamdouni on 20/07/2018.
 */

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CalculateResult {

    private ProfessionStatus professionStatus;

    List<User> listPolicies;

    public boolean hasProfession() {
        return professionStatus != null && professionStatus.equals(ProfessionStatus.WITH_PROFESSION);
    }

}
