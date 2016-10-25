package co.kereta.artslv;

import co.id.artslv.lib.PageImplBean;
import co.id.artslv.lib.availability.AvailabilityData;
import co.id.artslv.lib.inventory.Inventory;
import co.id.artslv.lib.responses.MessageWrapper;
import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.transactions.Transactiondet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.junit.runner.Result;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void testString() throws IOException {

        String test = "[\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1A\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1B\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1C\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1D\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2A\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2B\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2C\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2D\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3A\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3B\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3C\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3D\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4A\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4B\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4C\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4D\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5A\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5B\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5C\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-1\",\n" +
                "          \"stamformdetcode\": \"CAR-1\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5D\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1A\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1B\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1C\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1D\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2A\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2B\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2C\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2D\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3A\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3B\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3C\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3D\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4A\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4B\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4C\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4D\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5A\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5B\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5C\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-2\",\n" +
                "          \"stamformdetcode\": \"CAR-2\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5D\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1A\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1B\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1C\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1D\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2A\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2B\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2C\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2D\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3A\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3B\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3C\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3D\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4A\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4B\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4C\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4D\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5A\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5B\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5C\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-3\",\n" +
                "          \"stamformdetcode\": \"CAR-3\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5D\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1A\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1B\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1C\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1D\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2A\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2B\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2C\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2D\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3A\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3B\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3C\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3D\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4A\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4B\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4C\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4D\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5A\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5B\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5C\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-4\",\n" +
                "          \"stamformdetcode\": \"CAR-4\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5D\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1A\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1B\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1C\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-1D\",\n" +
                "          \"wagondetrow\": 1,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2A\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2B\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2C\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-2D\",\n" +
                "          \"wagondetrow\": 2,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3A\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3B\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3C\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-3D\",\n" +
                "          \"wagondetrow\": 3,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4A\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4B\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4C\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-4D\",\n" +
                "          \"wagondetrow\": 4,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5A\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"A\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5B\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"B\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5C\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"C\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"schedulenoka\": \"A001\",\n" +
                "          \"tripdate\": \"2017-01-01\",\n" +
                "          \"stoporder\": 0,\n" +
                "          \"stamformdetid\": \"SFD-A001-170101-EKS-5\",\n" +
                "          \"stamformdetcode\": \"CAR-5\",\n" +
                "          \"stamformdetorder\": 0,\n" +
                "          \"wagondetid\": \"WGD-EKSA-5D\",\n" +
                "          \"wagondetrow\": 5,\n" +
                "          \"wagondetcol\": \"D\",\n" +
                "          \"bookstat\": \"0\",\n" +
                "          \"status\": \"1\",\n" +
                "          \"wagondetcolnum\": 0\n" +
                "        }\n" +
                "      ]";
        ObjectMapper objectMapper = new ObjectMapper();
        List<Inventory> inventories = objectMapper.readValue(test, new com.fasterxml.jackson.core.type.TypeReference<List<Inventory>>() {
        });

        inventories.sort((o1, o2) -> {
            String comb1 = o1.getStamformdetcode()+"-"+o1.getWagondetrow()+"-"+o1.getWagondetcolnum();
            String comb2 = o2.getStamformdetcode()+"-"+o2.getWagondetrow()+"-"+o2.getWagondetcolnum();

            return comb1.compareTo(comb2);
        });


        String result = objectMapper.writeValueAsString(inventories.stream().limit(2).collect(Collectors.toList()));

        System.out.println(result);
    }

    @Test
    public void testJustMessage() throws IOException {
        String s = "{\n" +
                "  \"status\": \"04\",\n" +
                "  \"message\": \"Transaction not found\"\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        MessageWrapper<Object> messageWrapper = objectMapper.readValue(s,MessageWrapper.class);

        String result = messageWrapper.getStatus();
        System.out.println(result);


        MessageWrapper<Object> objectMessageWrapper = new MessageWrapper<>();
        AvailabilityData ava  =objectMessageWrapper.getValue(s, "availabilitydatalist", new com.fasterxml.jackson.core.type.TypeReference<AvailabilityData>() {
        });

        if(ava==null){
            System.out.println("TEST");
        }


    }


}

