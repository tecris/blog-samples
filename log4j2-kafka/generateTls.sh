#! /bin/bash

echo "Generate tls certs and self-signed CA"
KEYTOOL=/usr/lib/jvm/default-java/bin/keytool

CA_CERT_DIR=./tls_certs/ca
CLIENT_CERT_DIR=./tls_certs/client
SERVER_CERT_DIR=./tls_certs/server

mkdir -p $CA_CERT_DIR $CLIENT_CERT_DIR $SERVER_CERT_DIR

# ### Creating your own CA #######################################################
# subj "/C=AT/ST=Vienna/L=Vienna/O=Cumulus/OU=Cloud/CN=mozart"
openssl req -new -x509 -days 365 \
	-passout pass:salzburg -out ./$CA_CERT_DIR/ca-cert -keyout ./$CA_CERT_DIR/ca-key \
	-subj "/C=AT/ST=Vienna/L=Vienna/O=Cumulus/OU=Cloud/CN=mozart"


#### BROKER ####################################################################
# === add (own generated) CA to the **brokers truststore** =========================
$KEYTOOL \
	-keystore ./$SERVER_CERT_DIR/server.truststore.jks -alias CARoot \
	-import -file ./$CA_CERT_DIR/ca-cert -storepass salzburg \
	-noprompt

# === Generate SSL key and certificate (for each Kafka broker) ===================
$KEYTOOL -genkey \
	-alias localhost -storepass salzburg -keystore ./$SERVER_CERT_DIR/server.keystore.jks \
	-validity 365 -keyalg RSA -dname "cn=mozart, ou=Cloud, o=Cumulus, c=AT" \
	 -keypass salzburg

#=== export the certificate from the keystore ===================================
$KEYTOOL -certreq -file ./$SERVER_CERT_DIR/cert-file \
	-alias localhost -storepass salzburg -keystore ./$SERVER_CERT_DIR/server.keystore.jks

# === sign it with the CA ========================================================
openssl x509 -req -CA ./$CA_CERT_DIR/ca-cert -CAkey ./$CA_CERT_DIR/ca-key \
	-in ./$SERVER_CERT_DIR/cert-file -out ./$SERVER_CERT_DIR/cert-signed -days 365 \
	-CAcreateserial -passin pass:salzburg

# === import the certificate of the CA into the keystore =========================
$KEYTOOL -keystore ./$SERVER_CERT_DIR/server.keystore.jks \
	-alias CARoot -storepass salzburg \
	-import -file ./$CA_CERT_DIR/ca-cert -noprompt

# === import the signed certificate into the keystore ============================
$KEYTOOL -import -file ./$SERVER_CERT_DIR/cert-signed \
	-storepass salzburg -keystore ./$SERVER_CERT_DIR/server.keystore.jks -alias localhost


# ### Client #####################################################################
# === add (own generated) CA to the **clients truststore** =========================
$KEYTOOL -import -file ./$CA_CERT_DIR/ca-cert \
	-storepass salzburg -keystore ./$CLIENT_CERT_DIR/client.truststore.jks -alias CARoot \
	-noprompt

# ===  Generate SSL key and certificate for client ===============================
$KEYTOOL -genkey \
	-alias localhost -storepass salzburg -keystore ./$CLIENT_CERT_DIR/client.keystore.jks \
	-validity 365 -keyalg RSA -dname "cn=mozart, ou=Cloud, o=Cumulus, c=AT" \
	-keypass salzburg

# === export the certificate from the keystore ===================================
$KEYTOOL -certreq -file ./$CLIENT_CERT_DIR/cert-file \
	-alias localhost -storepass salzburg -keystore ./$CLIENT_CERT_DIR/client.keystore.jks

# === sign it with the CA ========================================================
openssl x509 -req -CA ./$CA_CERT_DIR/ca-cert -CAkey ./$CA_CERT_DIR/ca-key \
	-in ./$CLIENT_CERT_DIR/cert-file -out ./$CLIENT_CERT_DIR/cert-signed -days 365 \
	-CAcreateserial -passin pass:salzburg

# === add the generated CA to the **clients keystore** =========================
$KEYTOOL -import -file ./$CA_CERT_DIR/ca-cert \
	-storepass salzburg -keystore ./$CLIENT_CERT_DIR/client.keystore.jks -alias CARoot \
	-noprompt

# === import the signed certificate into the keystore ============================
$KEYTOOL -import -file ./$CLIENT_CERT_DIR/cert-signed \
	-alias localhost -storepass salzburg -keystore ./$CLIENT_CERT_DIR/client.keystore.jks
