package co.kereta.artslv;

import co.id.artslv.lib.PageImplBean;
import co.id.artslv.lib.availability.AvailabilityData;
import co.id.artslv.lib.responses.MessageWrapper;
import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.transactions.Transactiondet;
import org.codehaus.jackson.type.TypeReference;
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
                "  \"response\": {\n" +
                "    \"availabilitydatalist\": [\n" +
                "      {\n" +
                "        \"id\": \"PRO003\",\n" +
                "        \"tripdate\": \"2017-01-01\",\n" +
                "        \"stasiunnameorg\": \"MANGGARAI\",\n" +
                "        \"stasiuncodeorg\": \"MRI\",\n" +
                "        \"stasiuncodedes\": \"BPR\",\n" +
                "        \"scheduleDatas\": [\n" +
                "          {\n" +
                "            \"id\": \"SCH-A001-001\",\n" +
                "            \"noka\": \"A001\",\n" +
                "            \"trainname\": \"AIRPORT RAILINK SERVICES\",\n" +
                "            \"stopdeparture\": \"0500\",\n" +
                "            \"stoparrival\": \"0540\",\n" +
                "            \"allocationDatas\": [\n" +
                "              {\n" +
                "                \"subclasscode\": \"A\",\n" +
                "                \"seatavailable\": 100,\n" +
                "                \"faretotamount\": 100000\n" +
                "              },\n" +
                "              {\n" +
                "                \"subclasscode\": \"J\",\n" +
                "                \"seatavailable\": 100,\n" +
                "                \"faretotamount\": 90000\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"status\": \"00\",\n" +
                "  \"message\": \"SUCCESS\"\n" +
                "}";


        MessageWrapper<Object> messageWrapper = new MessageWrapper<>();
        List<AvailabilityData> transactionList = messageWrapper.getValue(jsondata, "availabilitydatalist", new com.fasterxml.jackson.core.type.TypeReference<List<AvailabilityData>>() {
        });

        AvailabilityData availabilityData = transactionList.get(0);

    }


}

