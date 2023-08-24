module edpn.backend.boot {
    requires static lombok;
    requires spring.boot;
    requires org.mybatis.spring;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires spring.beans;
    requires edpn.backend.exploration;
    requires edpn.backend.trade;

    opens io.edpn.backend.application.controller to spring.core;
}
