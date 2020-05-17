@E:

@mkdir  key

@cd key

@keytool -genkeypair -keysize 1024 -validity 3650 -alias "privateKey" -keystore "privateKeys.store" -storepass "136305973" -keypass "136973" -dname "CN=FAST, OU=JavaSoft, O=XSX, L=WUHAN, ST=HUBEI, C=CN"

@keytool -exportcert -alias "privateKey" -keystore "privateKeys.store" -storepass "zedu@6666" -file "certfile.cer"

echo y | keytool -import -alias "publicCert" -file "certfile.cer" -keystore "publicCerts.store" -storepass "zedu@8888"  

@pause