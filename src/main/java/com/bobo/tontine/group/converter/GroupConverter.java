package com.bobo.tontine.group.converter;

import com.bobo.tontine.group.dto.GroupDto;
import com.bobo.tontine.group.entity.Group;

/**
 * @author Mamadou Bobo on 28/10/2022
 * @Project Tontine
 */
public class GroupConverter {

    public static Group convertGroupDtoToEntity(GroupDto groupDto) {
        return Group
                .builder()
                .name(groupDto.getName())
                .build();
    }

}
