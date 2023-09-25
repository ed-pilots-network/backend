package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.PagedResultDto;

import java.util.List;
import java.util.Objects;

public abstract class RestPagedResultDto<T> implements PagedResultDto<T, RestPageInfoDto> {
    private final List<T> result;
    private final RestPageInfoDto pageInfo;

    public RestPagedResultDto(
            List<T> result,
            RestPageInfoDto pageInfo
    ) {
        this.result = result;
        this.pageInfo = pageInfo;
    }

    @Override
    public List<T> result() {
        return result;
    }

    @Override
    public RestPageInfoDto pageInfo() {
        return pageInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RestPagedResultDto<?>) obj;
        return Objects.equals(this.result, that.result) &&
                Objects.equals(this.pageInfo, that.pageInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, pageInfo);
    }

    @Override
    public String toString() {
        return "RestPagedResultDto[" +
                "result=" + result + ", " +
                "pageInfo=" + pageInfo + ']';
    }

}
