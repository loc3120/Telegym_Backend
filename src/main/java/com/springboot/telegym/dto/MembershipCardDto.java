package com.springboot.telegym.dto;

import com.springboot.telegym.entity.MembershipCard;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembershipCardDto {

    String id;

    String cardname;

    int level_card;

    String description;

    long minprice;

    boolean is_deleted;

    public MembershipCardDto(MembershipCard membershipCard) {
        this.id = membershipCard.getId();
        this.cardname = membershipCard.getCardname();
        this.level_card = membershipCard.getLevel_card();
        this.description = membershipCard.getDescription();
        this.minprice = membershipCard.getMinprice();
        this.is_deleted = membershipCard.is_deleted();
    }
}
