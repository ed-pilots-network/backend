module edpn.commodity.finder {
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
    requires edpn.mybatis.util;
    requires edpn.util;
    requires edpn.messageprocessor.lib;
    requires org.mybatis.spring;
    requires spring.beans;
    requires kafka.clients;
    requires spring.web;
    requires org.mybatis;

    exports io.edpn.backend.commodityfinder.application.controller;
    exports io.edpn.backend.commodityfinder.application.dto;
    exports io.edpn.backend.commodityfinder.application.service;
    exports io.edpn.backend.commodityfinder.domain.service;
}
