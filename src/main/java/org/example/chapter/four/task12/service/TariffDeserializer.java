package org.example.chapter.four.task12.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.example.chapter.four.task12.entity.Tariff;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TariffDeserializer extends StdDeserializer<Tariff> {
    private final Map<Integer, Class<? extends Tariff>> registry = new HashMap<>();

    public TariffDeserializer() {
        super(Tariff.class);
    }

    public void registerTariff(int numberOfFields, Class<? extends Tariff> clazz) {
        registry.put(numberOfFields, clazz);
    }

    @Override
    public Tariff deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        TreeNode root = mapper.readTree(jsonParser);
        int k = root.size();
        if (!registry.containsKey(k)) { return null; }
        return mapper.readValue(root.traverse(), registry.get(k));
    }
}
