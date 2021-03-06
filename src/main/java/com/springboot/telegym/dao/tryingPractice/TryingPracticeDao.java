package com.springboot.telegym.dao.tryingPractice;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.TryingPracticeDto;
import org.springframework.data.domain.Pageable;

public interface TryingPracticeDao {

    PageData<TryingPracticeDto> getAllTP(Pageable pageable, String search);

    int createTP(TryingPracticeDto tryingPracticeDto);

    String contactCustomer(String id, boolean is_contacted);
}
