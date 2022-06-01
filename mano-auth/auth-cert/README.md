# Auth cert component

A simple component to use TLS certificate as authentication.
Server have it's own sertificate and sign client certificate.
- `CN` is the username
- `O` is the role (without `ROLE_`) and can be separated by comma.

## Setup

Edit `~/.mano.configuration.properties` and add those lines:

```
server.ssl.enabled=true
server.ssl.client-auth=need
server.ssl.key-store=keystore.jks
server.ssl.key-store-password=changeit
server.ssl.trust-store=truststore.jks
server.ssl.trust-store-password=changeit
```
