module edpn.backend.trade {
    requires static lombok;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.jdbc;
    requires spring.tx;
    requires spring.web;
    requires spring.kafka;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires org.slf4j;
    requires edpn.backend.util;
    requires edpn.backend.mybatisutil;
    requires edpn.backend.messageprocessorlib;
    requires org.mybatis.spring;
    requires kafka.clients;
    requires org.mybatis;
    requires jakarta.validation;

    exports io.edpn.backend.trade.domain.service.v1;
    exports io.edpn.backend.trade.domain.controller.v1;
    exports io.edpn.backend.trade.application.service.v1;
    exports io.edpn.backend.trade.application.controller.v1;
    exports io.edpn.backend.trade.application.dto.v1;
}
