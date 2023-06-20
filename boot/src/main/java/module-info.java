module edpn.boot {
    requires static lombok;
    requires spring.boot;
    requires org.mybatis.spring;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires io.edpn.backend.trade;
    requires spring.web;
    requires spring.beans;

    opens io.edpn.backend.application.controller to spring.core;
}
