package co.kereta.artslv;

import co.id.artslv.lib.PageImplBean;
import co.id.artslv.lib.responses.MessageWrapper;
import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.transactions.Transactiondet;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 03/10/16.
 */
public class TransactionServiceTest {
    @Test
    public void deserialize() throws IOException {
        String jsondata = "{\n" +
                "\t\"response\": {\n" +
                "\t\t\"transactionlist\": [{\n" +
                "\t\t\t\"id\": \"14208695-2135-4b2f-afdd-9f416174fe1d\",\n" +
                "\t\t\t\"date\": \"2016-07-24 00:00:00\",\n" +
                "\t\t\t\"departdate\": \"2016-10-05\",\n" +
                "\t\t\t\"paycode\": \"1218722620638\",\n" +
                "\t\t\t\"totamount\": 1200000,\n" +
                "\t\t\t\"status\": \"1\",\n" +
                "\t\t\t\"domain\": \"4c112a65-e6f2-4b0d-bfef-0912748bdb76\",\n" +
                "\t\t\t\"modifiedby\": \"GEO TESTING\",\n" +
                "\t\t\t\"modifiedon\": \"2016-07-24 00:00:00\",\n" +
                "\t\t\t\"tripid\": \"c244424c-f77a-4bd9-bf73-14c37b662009\",\n" +
                "\t\t\t\"bookcode\": \"V0F85RC\",\n" +
                "\t\t\t\"phonenum\": \"1234\",\n" +
                "\t\t\t\"stasiunidorg\": \"326a1c93-97ce-e1d9-e053-0b0610ac5298\",\n" +
                "\t\t\t\"stasiuniddest\": \"326a1c93-97ce-e1d9-e053-0b0610ac5502\",\n" +
                "\t\t\t\"subclassid\": \"b25bdd33-e4cd-4f04-a393-af11e70d6b4f\",\n" +
                "\t\t\t\"useridbook\": \"c199ef09-2d04-434e-92db-874d25451411\",\n" +
                "\t\t\t\"unitidbook\": \"91241cc2-1848-40f9-9833-1258ff0bedf1\",\n" +
                "\t\t\t\"shiftid\": \"eed4d4e9-f274-41f5-a4ce-a32f8ad34a25\",\n" +
                "\t\t\t\"totpsgadult\": 2,\n" +
                "\t\t\t\"totpsgchild\": 0,\n" +
                "\t\t\t\"totpsginfant\": 0,\n" +
                "\t\t\t\"createdon\": \"2016-07-24 00:00:00\",\n" +
                "\t\t\t\"booktimeouton\": \"2016-07-24 00:00:00\",\n" +
                "\t\t\t\"paytimeouton\": \"2016-10-15 00:00:00\",\n" +
                "\t\t\t\"paidon\": \"2016-09-29 10:41:49\",\n" +
                "\t\t\t\"scheduleid\": \"1c21110f-87cd-4997-8284-84084c60235d\",\n" +
                "\t\t\t\"noka\": \"99\",\n" +
                "\t\t\t\"trainname\": \"MALABAR\",\n" +
                "\t\t\t\"channelid\": \"6a242b7d-afe3-472d-8130-46ace3aad91f\",\n" +
                "\t\t\t\"extrafee\": 7500,\n" +
                "\t\t\t\"paidamount\": 0,\n" +
                "\t\t\t\"reroutestat\": \"0\",\n" +
                "\t\t\t\"localstat\": \"1\"\n" +
                "\t\t}]\n" +
                "\t},\n" +
                "\t\"status\": \"00\",\n" +
                "\t\"message\": \"SUCCESS\"\n" +
                "}";


        MessageWrapper<Object> messageWrapper = new MessageWrapper<>();
        List<Transaction> transactionList = messageWrapper.getValue(jsondata,"transactionlist",List.class);

    }
    @Test
    public void deserializeComposit() throws IOException {
        String message = "{\n" +
                "  \"response\": {\n" +
                "    \"transactiondetlist\": [\n" +
                "      {\n" +
                "        \"id\": \"28519ad1-2003-4a12-9990-84efb1586d18\",\n" +
                "        \"psgtype\": \"A\",\n" +
                "        \"psgname\": \"TESTCRON31\",\n" +
                "        \"transactionid\": \"14208695-2135-4b2f-afdd-9f416174fe1d\",\n" +
                "        \"psgid\": \"123465\",\n" +
                "        \"amount\": 600000,\n" +
                "        \"status\": \"4\",\n" +
                "        \"domain\": \"4c112a65-e6f2-4b0d-bfef-0912748bdb76\",\n" +
                "        \"modifiedby\": \"e00531d2-30f7-401b-9548-39b67652a2eb\",\n" +
                "        \"modifiedon\": \"2016-07-24 00:00:00\",\n" +
                "        \"rateid\": \"c39bff57-3d34-44fb-971a-0698df6a696e\",\n" +
                "        \"wagondetid\": \"dabdc7f2-c9c6-4f27-aeb5-6b30fa971f5b\",\n" +
                "        \"stamformdetid\": \"SFD-1-161101-EKS1\",\n" +
                "        \"ticketnum\": \"KP160724000005\",\n" +
                "        \"stasiunidbook\": \"32799263-011d-f8e9-e053-0a0610acdefc\",\n" +
                "        \"bookedon\": \"2016-07-24 00:00:00\",\n" +
                "        \"discamount\": 0,\n" +
                "        \"redeemvalue\": 0,\n" +
                "        \"rateamount\": 600000,\n" +
                "        \"redamount\": 0,\n" +
                "        \"tmaxprint\": 99,\n" +
                "        \"cmaxprint\": 99,\n" +
                "        \"releaseon\": \"2016-07-24 00:00:00\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"bd313cc3-24b7-423d-97dd-0ec01113be8b\",\n" +
                "        \"psgtype\": \"A\",\n" +
                "        \"psgname\": \"TESTCRON32\",\n" +
                "        \"transactionid\": \"14208695-2135-4b2f-afdd-9f416174fe1d\",\n" +
                "        \"psgid\": \"123465\",\n" +
                "        \"amount\": 600000,\n" +
                "        \"status\": \"4\",\n" +
                "        \"domain\": \"4c112a65-e6f2-4b0d-bfef-0912748bdb76\",\n" +
                "        \"modifiedby\": \"e00531d2-30f7-401b-9548-39b67652a2eb\",\n" +
                "        \"modifiedon\": \"2016-07-24 00:00:00\",\n" +
                "        \"rateid\": \"c39bff57-3d34-44fb-971a-0698df6a696e\",\n" +
                "        \"wagondetid\": \"a96f013a-518f-4941-bc5d-c6f86f10d473\",\n" +
                "        \"stamformdetid\": \"SFD-1-161101-EKS1\",\n" +
                "        \"ticketnum\": \"KP160724000006\",\n" +
                "        \"stasiunidbook\": \"32799263-011d-f8e9-e053-0a0610acdefc\",\n" +
                "        \"bookedon\": \"2016-07-24 00:00:00\",\n" +
                "        \"discamount\": 0,\n" +
                "        \"redeemvalue\": 0,\n" +
                "        \"rateamount\": 600000,\n" +
                "        \"redamount\": 0,\n" +
                "        \"tmaxprint\": 99,\n" +
                "        \"cmaxprint\": 99,\n" +
                "        \"releaseon\": \"2016-07-24 00:00:00\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"transaction\": {\n" +
                "      \"id\": \"14208695-2135-4b2f-afdd-9f416174fe1d\",\n" +
                "      \"date\": \"2016-07-24 00:00:00\",\n" +
                "      \"departdate\": \"2016-10-05\",\n" +
                "      \"paycode\": \"1218722620638\",\n" +
                "      \"totamount\": 1200000,\n" +
                "      \"status\": \"1\",\n" +
                "      \"domain\": \"4c112a65-e6f2-4b0d-bfef-0912748bdb76\",\n" +
                "      \"modifiedby\": \"GEO TESTING\",\n" +
                "      \"modifiedon\": \"2016-07-24 00:00:00\",\n" +
                "      \"tripid\": \"c244424c-f77a-4bd9-bf73-14c37b662009\",\n" +
                "      \"bookcode\": \"V0F85RC\",\n" +
                "      \"phonenum\": \"1234\",\n" +
                "      \"stasiunidorg\": \"326a1c93-97ce-e1d9-e053-0b0610ac5298\",\n" +
                "      \"stasiuniddest\": \"326a1c93-97ce-e1d9-e053-0b0610ac5502\",\n" +
                "      \"subclassid\": \"b25bdd33-e4cd-4f04-a393-af11e70d6b4f\",\n" +
                "      \"useridbook\": \"c199ef09-2d04-434e-92db-874d25451411\",\n" +
                "      \"unitidbook\": \"91241cc2-1848-40f9-9833-1258ff0bedf1\",\n" +
                "      \"shiftid\": \"eed4d4e9-f274-41f5-a4ce-a32f8ad34a25\",\n" +
                "      \"totpsgadult\": 2,\n" +
                "      \"totpsgchild\": 0,\n" +
                "      \"totpsginfant\": 0,\n" +
                "      \"createdon\": \"2016-07-24 00:00:00\",\n" +
                "      \"booktimeouton\": \"2016-07-24 00:00:00\",\n" +
                "      \"paytimeouton\": \"2016-10-15 00:00:00\",\n" +
                "      \"paidon\": \"2016-09-29 10:41:49\",\n" +
                "      \"scheduleid\": \"1c21110f-87cd-4997-8284-84084c60235d\",\n" +
                "      \"noka\": \"99\",\n" +
                "      \"trainname\": \"MALABAR\",\n" +
                "      \"channelid\": \"6a242b7d-afe3-472d-8130-46ace3aad91f\",\n" +
                "      \"extrafee\": 7500,\n" +
                "      \"paidamount\": 0,\n" +
                "      \"reroutestat\": \"0\",\n" +
                "      \"localstat\": \"1\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"status\": \"00\",\n" +
                "  \"message\": \"SUCCESS\"\n" +
                "}";

        MessageWrapper<Object> messageWrapper = new MessageWrapper<>();
        List<Transactiondet> transactiondetList = messageWrapper.getValue(message,"transactiondetlist",List.class);
        Transaction transaction = messageWrapper.getValue(message,"transaction",Transaction.class);
    }

    @Test
    public void deserializePaging() throws IOException {
        String jsonData = "{\n" +
                "  \"response\": {\n" +
                "    \"pageimplbean\": {\n" +
                "      \"content\": [\n" +
                "        {\n" +
                "          \"id\": \"28519ad1-2003-4a12-9990-84efb1586d18\",\n" +
                "          \"status\": \"4\",\n" +
                "          \"domain\": \"4c112a65-e6f2-4b0d-bfef-0912748bdb76\",\n" +
                "          \"modifiedby\": \"e00531d2-30f7-401b-9548-39b67652a2eb\",\n" +
                "          \"modifiedon\": \"2016-07-24 00:00:00\",\n" +
                "          \"totpsgadult\": 0,\n" +
                "          \"totpsgchild\": 0,\n" +
                "          \"totpsginfant\": 0,\n" +
                "          \"paidamount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"bd313cc3-24b7-423d-97dd-0ec01113be8b\",\n" +
                "          \"status\": \"4\",\n" +
                "          \"domain\": \"4c112a65-e6f2-4b0d-bfef-0912748bdb76\",\n" +
                "          \"modifiedby\": \"e00531d2-30f7-401b-9548-39b67652a2eb\",\n" +
                "          \"modifiedon\": \"2016-07-24 00:00:00\",\n" +
                "          \"totpsgadult\": 0,\n" +
                "          \"totpsgchild\": 0,\n" +
                "          \"totpsginfant\": 0,\n" +
                "          \"paidamount\": 0\n" +
                "        }\n" +
                "      ],\n" +
                "      \"number\": 0,\n" +
                "      \"size\": 2000,\n" +
                "      \"totalPages\": 1,\n" +
                "      \"numberOfElements\": 2,\n" +
                "      \"totalElements\": 2,\n" +
                "      \"first\": true,\n" +
                "      \"last\": true,\n" +
                "      \"sort\": null\n" +
                "    }\n" +
                "  },\n" +
                "  \"status\": \"00\",\n" +
                "  \"message\": \"SUCCESS\"\n" +
                "}";

        MessageWrapper<Object> messageWrapper = new MessageWrapper<>();
        PageImplBean<Transactiondet> pageImplBean = messageWrapper.getValue(jsonData,"pageimplbean",PageImplBean.class);
    }
}
