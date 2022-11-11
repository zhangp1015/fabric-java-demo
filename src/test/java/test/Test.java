package test;

import cc.taketo.util.PathUtil;
import org.hyperledger.fabric.gateway.*;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * @Title: Test
 * @Package: test
 * @Description:
 * @Author: zhangp
 * @Date: 2022/11/11 - 17:14
 */

public class Test {

    public Gateway gateway = null;

    @org.junit.Test
    @Before
    public void init() throws Exception {
        BufferedReader certificateReader = Files.newBufferedReader(Paths.get(PathUtil.getCertificatePath()), StandardCharsets.UTF_8);

        X509Certificate certificate = Identities.readX509Certificate(certificateReader);

        BufferedReader privateKeyReader = Files.newBufferedReader(Paths.get(PathUtil.getPrivateKeyPath()), StandardCharsets.UTF_8);

        PrivateKey privateKey = Identities.readPrivateKey(privateKeyReader);

        Wallet wallet = Wallets.newInMemoryWallet();
        wallet.put("user1", Identities.newX509Identity("Org1MSP", certificate, privateKey));


        Gateway.Builder builder = Gateway.createBuilder()
                .identity(wallet, "user1")
                .networkConfig(Paths.get("src/main/resources/networkConnection.json"));

        gateway = builder.connect();
    }

    @org.junit.Test
    public void query() throws ContractException {
        Network mychannel = gateway.getNetwork("mychannel");
        System.out.println("mychannel = " + mychannel);
        Contract mycc = mychannel.getContract("mycc");
        System.out.println("mycc = " + mycc);
        byte[] bytes = mycc.evaluateTransaction("query", "a");
        System.out.println("result = " + new String(bytes));
    }
}
