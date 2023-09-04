package io.edpn.backend.trade.application.dto.web.object;

import java.util.List;

public interface PagedResultDto<T> {
    List<T> result();
    <F extends PageInfoDto> F pageInfo();
}
