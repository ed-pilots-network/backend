module edpn.commodity {
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
    requires edpn.lib;
    requires org.mybatis.spring;
    requires org.apache.ibatis;
    requires spring.beans;
    requires kafka.clients;
}