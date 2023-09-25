package io.edpn.backend.trade.application.dto.persistence.entity;

public interface PersistencePageInfo {

    Integer getPageSize();

    Integer getCurrentPage();

    Integer getTotalItems();
}
