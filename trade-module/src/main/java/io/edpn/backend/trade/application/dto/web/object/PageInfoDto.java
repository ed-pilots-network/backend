package io.edpn.backend.trade.application.dto.web.object;

public interface PageInfoDto {
    int pageSize();
    int currentPage();
    int totalItems();
}
