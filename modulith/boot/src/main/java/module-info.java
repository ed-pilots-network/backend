module edpn {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires edpn.commodity.finder;

    opens io.edpn.backend.modulith to spring.core;
}