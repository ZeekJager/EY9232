package com.trade.tradelicense.infrastructure.common.serialization;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import com.trade.tradelicense.domain.valueobjects.Money;

/**
 * Jackson {@link ValueSerializer} for the {@link Money} value object.
 *
 * <p>Serializes a {@code Money} instance as a JSON object with two fields:
 * <pre>
 * {
 *   "amount": 100.00,
 *   "currency": "AED"
 * }
 * </pre>
 *
 * <p>Register this serializer via a {@code @JsonSerialize} annotation on the
 * {@link Money} class or through a Spring Boot {@code Jackson2ObjectMapperBuilderCustomizer}
 * bean.
 */
public class MoneySerializer extends ValueSerializer<Money> {

    /**
     * Serializes the given {@link Money} instance.
     *
     * @param money    the value to serialize; must not be {@code null}
     * @param gen      the JSON generator to write to
     * @param context  the serialization context
     */
    @Override
    public void serialize(Money money, JsonGenerator gen, SerializationContext context) {
        gen.writeStartObject();
        gen.writeNumberProperty("amount", money.getAmount());
        gen.writeStringProperty("currency", money.getCurrency());
        gen.writeEndObject();
    }
}
