module io.edpn.backend.trade {
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
    requires io.edpn.backend.util;
    requires io.edpn.backend.mybatis.util;
    requires io.edpn.backend.messageprocessor.lib;
    requires org.mybatis.spring;
    requires spring.beans;
    requires kafka.clients;
    requires spring.web;
    requires org.mybatis;

    exports io.edpn.backend.trade.application.controller;
    exports io.edpn.backend.trade.application.dto;
    exports io.edpn.backend.trade.application.service;
    exports io.edpn.backend.trade.domain.service;
}
