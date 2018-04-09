package com.pdp.interop.demo;

import com.ilp.conditions.impl.IlpConditionHandlerImpl;
import com.ilp.conditions.models.pdp.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class BuildIlpPacketAndCondition {

    protected Logger logger = Logger.getLogger(BuildIlpPacketAndCondition.class.getName());

    public void build() throws IOException {

        //TODO: Obtain ilpAddress, amount and transaction
        String ilpAddress = "private.bob";
        long amount = 100L;

        Transaction transaction = getMockTransaction();

        logger.info("IlpAddress: " + ilpAddress + " Amount: " + amount + " and Transaction: " + transaction.toString());

        //Call interop-ilp-conditions jar getIlpPacket()
        IlpConditionHandlerImpl ilpConditionHandlerImpl = new IlpConditionHandlerImpl();
        String ilpPacket = ilpConditionHandlerImpl.getILPPacket(ilpAddress, amount, transaction);

        //TOTO: Obtain secret and replace hardcoded value
        byte[] secret = new byte[32];
        secret = Base64.getEncoder().encode("secret".getBytes());

        //Call interop-ilp-conditions jar generateCondition()
        String ilpCondition = ilpConditionHandlerImpl.generateCondition(ilpPacket, secret);

        //Call interop-ilp-conditions jar generateFulfillment()
        String fulfillment = ilpConditionHandlerImpl.generateFulfillment(ilpPacket, secret);

        logger.info("ilpPacket: " + ilpPacket);
        logger.info("ilpCondition: " + ilpCondition);
        logger.info("ilpFulfillment: " + fulfillment);

        //Call interop-ilp-conditions jar validateFulfillmentAgainstCondition
        boolean validation = ilpConditionHandlerImpl.validateFulfillmentAgainstCondition(fulfillment, ilpCondition);

        logger.info("validating fulfillment against Condition returned: " + validation);
    }

    public Transaction getMockTransaction() {

        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransactionId("a8323bc6-c228-4df2-ae82-e5a997baf899");
        mockTransaction.setQuoteId("b51ec534-ee48-4575-b6a9-ead2955b8069");

        Party pe = new Party();
        pe.setMerchantClassificationCode("1234");
        pe.setName("Justin Trudeau");
        PartyIdInfo pii1 = new PartyIdInfo();
        pii1.setFspId("1234");
        pii1.setPartyIdentifier("16135551212");
        pii1.setPartyIdType("MSISDN");
        pii1.setPartySubIdOrType("PASSPORT");
        pe.setPartyIdInfo(pii1);
        PartyPersonalInfo ppi1 = new PartyPersonalInfo();
        PartyComplexName pcn1 = new PartyComplexName();
        pcn1.setFirstName("Justin");
        pcn1.setLastName("Trudeau");
        pcn1.setMiddleName("Pierre");
        ppi1.setComplexName(pcn1);
        ppi1.setDateOfBirth("1971-12-25");
        pe.setPersonalInfo(ppi1);
        mockTransaction.setPayee(pe);

        Party pr = new Party();
        pr.setMerchantClassificationCode("5678");
        pr.setName("Michael Jordan");
        PartyIdInfo pii2 = new PartyIdInfo();
        pii2.setFspId("1234");
        pii2.setPartyIdentifier("16135551212");
        pii2.setPartyIdType("MSISDN");
        pii2.setPartySubIdOrType("PASSPORT");
        pr.setPartyIdInfo(pii2);
        PartyPersonalInfo ppi2 = new PartyPersonalInfo();
        PartyComplexName pcn2 = new PartyComplexName();
        pcn2.setFirstName("Michael");
        pcn2.setLastName("Jordan");
        pcn2.setMiddleName("Jeffrey");
        ppi2.setComplexName(pcn2);
        ppi2.setDateOfBirth("1963-02-17");
        pr.setPersonalInfo(ppi2);
        mockTransaction.setPayer(pr);

        Money m = new Money();
        m.setAmount(String.valueOf(100L));
        m.setCurrency(Currency.USD.getValue());
        mockTransaction.setAmount(m);

        TransactionType tt = new TransactionType();
        tt.setBalanceOfPayments("123");
        tt.setInitiator(TransactionInitiator.PAYEE.getValue());
        tt.setInitiatorType(TransactionInitiatorType.CONSUMER.getValue());
        Refund ri = new Refund();
        ri.setOriginalTransactionId("b51ec534-ee48-4575-b6a9-ead2955b8069");
        tt.setRefundInfo(ri);
        tt.setScenario(TransactionScenario.TRANSFER.getValue());
        tt.setSubScenario("locally defined sub-scenario");
        mockTransaction.setTransactionType(tt);

        mockTransaction.setNote("Some note.");

        ExtensionList el = new ExtensionList();
        Extension e1 = new Extension();
        e1.setKey("key1");
        e1.setValue("value1");
        Extension e2 = new Extension();
        e2.setKey("key2");
        e2.setValue("value2");
        List<Extension> le = new ArrayList<>();
        le.add(e1);
        le.add(e2);
        el.setExtension(le);
        mockTransaction.setExtensionList(el);

        return mockTransaction;
    }


}
