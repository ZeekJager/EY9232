package com.trade.tradelicense.infrastructure.common.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.trade.tradelicense.domain.valueobjects.Money;

import java.io.IOException;

/**
 * Jackson {@link JsonSerializer} for the {@link Money} value object.
 *
 * <p>Serializes a {@code Money} instance as a JSON object with two fields:
 * <pre>
 * {
 *   "amount": 100.00,
 *   "currency": "AED"
 * }
 * </pre>
 *
 * <p>Register this serializer via a Jackson {@code @JsonSerialize} annotation on the
 * {@link Money} class or through a Spring Boot {@code Jackson2ObjectMapperBuilderCustomizer}
 * bean.
 */
public class MoneySerializer extends JsonSerializer<Money> {

    /**
     * Serializes the given {@link Money} instance.
     *
     * @param money    the value to serialize; must not be {@code null}
     * @param gen      the JSON generator to write to
     * @param provider the serializer provider
     * @throws IOException if a JSON I/O error occurs
     */
    @Override
    public void serialize(Money money, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("amount", money.getAmount());
        gen.writeStringField("currency", money.getCurrency());
        gen.writeEndObject();
    }
}
