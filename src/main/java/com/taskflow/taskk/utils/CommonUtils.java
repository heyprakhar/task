package com.taskflow.taskk.utils;

import com.taskflow.taskk.request.ParamRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.taskflow.taskk.common.utils.Constants.DEFAULT_ORDER;
import static com.taskflow.taskk.common.utils.Constants.DEFAULT_SORT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {
    public static Pageable buildPageRequest(ParamRequest request){

        int page = Math.max(request.getPage()-1,0);
        int size = request.getSize();

        if(StringUtils.isNotBlank(request.getSortBy())){

            Sort sort = DEFAULT_ORDER.equalsIgnoreCase(request.getDirection())
                        ? Sort.by(request.getSortBy()).descending()
                        : Sort.by(request.getSortBy()).ascending();

            return PageRequest.of(page, size, sort);
        }

        return PageRequest.of(page, size, Sort.by(DEFAULT_SORT).descending());
    }
}
