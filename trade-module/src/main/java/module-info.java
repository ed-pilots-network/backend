module edpn.backend.trade {
    requires static lombok;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.jdbc;
    requires spring.tx;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires spring.kafka;
    requires java.sql;
    requires org.slf4j;
    requires edpn.backend.util;
    requires edpn.backend.mybatisutil;
    requires edpn.backend.messageprocessorlib;
    requires org.mybatis.spring;
    requires spring.beans;
    requires kafka.clients;
    requires spring.web;
    requires org.mybatis;
    requires jakarta.validation;

    exports io.edpn.backend.trade.application.controller;
    exports io.edpn.backend.trade.application.dto;
    exports io.edpn.backend.trade.application.service;
    exports io.edpn.backend.trade.domain.service;
}
