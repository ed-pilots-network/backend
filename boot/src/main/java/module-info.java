module edpn.backend.boot {
    requires static lombok;
    requires spring.boot;
    requires org.mybatis.spring;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires spring.beans;
    requires spring.boot.starter.security;
    requires spring.security.crypto;
    requires spring.security.core;

    requires edpn.backend.trade;
    requires edpn.backend.user;

    opens io.edpn.backend.application.controller to spring.core;
}
