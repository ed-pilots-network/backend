module io.edpn.backend.user {
    requires static lombok;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.jdbc;
    requires spring.tx;
    requires java.sql;
    requires org.slf4j;
    requires org.mybatis.spring;
    requires spring.beans;
    requires org.mybatis;
    requires io.jsonwebtoken;
    requires jakarta.servlet;
    requires spring.boot.starter.security;
    requires io.github.bucket4j.core;
    requires spring.security.core;
    requires io.edpn.backend.mybatis.util;
    requires spring.security.crypto;
    requires spring.security.web;
    requires spring.security.config;
    requires spring.web;

    exports io.edpn.backend.user.application.controller;
    exports io.edpn.backend.user.application.dto;
    exports io.edpn.backend.user.application.service;
    exports io.edpn.backend.user.domain.service;
}
