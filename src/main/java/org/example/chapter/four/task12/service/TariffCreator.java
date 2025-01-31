package org.example.chapter.four.task12.service;

import org.example.chapter.four.task12.entity.Tariff;
import org.example.chapter.four.task12.entity.impl.BusinessTariff;
import org.example.chapter.four.task12.entity.impl.ChildTariff;
import org.example.chapter.four.task12.entity.impl.RegularTariff;
import org.example.util.create.Creator;
import org.example.util.exception.CreatorException;
import org.example.util.exception.ReaderException;
import org.example.util.read.Reader;
import org.example.util.read.impl.ReaderJSON;

import java.util.List;

public class TariffCreator extends Creator<Tariff> {

    public TariffCreator(String fileName) {
        super(fileName);
    }

    @Override
    public List<Tariff> createList() throws CreatorException {
        List<Tariff> tariffs;
        try {
            TariffDeserializer td = new TariffDeserializer();
            td.registerTariff(5, RegularTariff.class);
            td.registerTariff(3, BusinessTariff.class);
            td.registerTariff(4, ChildTariff.class);
            Reader<Tariff> tariffReader = new ReaderJSON<>(Tariff.class, td);
            tariffs = tariffReader.readListFromFile(fileName);
        } catch (ReaderException e) {
            LOGGER.error(e);
            throw new CreatorException(e);
        }
        return tariffs;
    }
}
