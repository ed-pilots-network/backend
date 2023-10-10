module edpn.backend.exploration {
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
    requires io.swagger.v3.oas.annotations;
    requires liquibase.core;
    requires spring.retry;
    requires spring.core;

    exports io.edpn.backend.exploration.application.port.incomming;
    exports io.edpn.backend.exploration.application.dto;
    exports io.edpn.backend.exploration.adapter.web;
    exports io.edpn.backend.exploration.application.domain;
    exports io.edpn.backend.exploration.application.dto.persistence.entity;
    exports io.edpn.backend.exploration.application.dto.web.object;
    exports io.edpn.backend.exploration.application.dto.persistence.filter;
}
