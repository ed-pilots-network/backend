package io.edpn.backend.trade.application.dto.web.object;

import java.util.List;

public interface PagedResultDto<T, F extends PageInfoDto> {
    List<T> result();
    F pageInfo();
}
