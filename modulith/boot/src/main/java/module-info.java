module edpn {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires edpn.commodity;

    opens io.edpn.backend.modulith to spring.core;
}