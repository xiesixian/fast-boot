package com.xiesx.gotv.core.json.serializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value != null) {
			BigDecimal number = value.setScale(2, RoundingMode.HALF_UP);
			gen.writeNumber(number);
		} else {
			gen.writeNumber(value);
		}
	}
}