/*
 * Copyright 2008-2010 the T2 Project ant the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.t2framework.confeito.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.t2framework.confeito.Constants;

/**
 * <#if locale="en">
 * <p>
 * ConverterUtil is an utility class that converts object.
 * </p>
 * 
 * <br/>
 * 
 * <#else>
 * <p>
 * オブジェクトの変換ユーティリティクラスです.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ConverterUtil {

	private static Logger logger = Logger.getLogger(ConverterUtil.class
			.getName());

	public static final String NULL_PATTERN = null;

	public static final Pattern YES_PATTERN = PatternUtil.getPattern(
			"(yes|true|y|1)", Pattern.CASE_INSENSITIVE);

	private static Map<Class<?>, Converter<?>> map = new HashMap<Class<?>, Converter<?>>();

	private static boolean initialized = false;

	public static void init() {
		if (!initialized) {
			synchronized (ConverterUtil.class) {
				init0();
				initialized = true;
			}
		}
	}

	private static void init0() {
		// primitives
		map.put(BigDecimal.class, BIGDECIMAL_CONVERTER);
		map.put(BigInteger.class, BIGINTEGER_CONVERTER);
		map.put(Byte.class, BYTE_CONVERTER);
		map.put(byte.class, PRIMITIVE_BYTE_CONVERTER);
		map.put(byte[].class, BINARY_CONVERTER);
		map.put(Boolean.class, BOOLEAN_CONVERTER);
		map.put(boolean.class, PRIMITIVE_BOOLEAN_CONVERTER);
		// map.put(Character.class, CHARACTER_CONVERTER);
		// map.put(char.class, PRIMITIVE_CHARACTER_CONVERTER);
		map.put(Double.class, DOUBLE_CONVERTER);
		map.put(double.class, PRIMITIVE_DOUBLE_CONVERTER);
		map.put(Float.class, FLOAT_CONVERTER);
		map.put(float.class, PRIMITIVE_FLOAT_CONVERTER);
		map.put(Integer.class, INTEGER_CONVERTER);
		map.put(int.class, PRIMITIVE_INTEGER_CONVERTER);
		map.put(Long.class, LONG_CONVERTER);
		map.put(long.class, PRIMITIVE_LONG_CONVERTER);
		map.put(Short.class, SHORT_CONVERTER);
		map.put(short.class, PRIMITIVE_SHORT_CONVERTER);
		map.put(String.class, STRING_CONVERTER);

		// atomics
		map.put(AtomicInteger.class, ATOMIC_INTEGER_CONVERTER);
		map.put(AtomicLong.class, ATOMIC_LONG_CONVERTER);
		map.put(AtomicBoolean.class, ATOMIC_BOOLEAN_CONVERTER);

		// times
		map.put(Calendar.class, CALENDAR_CONVERTER);
		map.put(Date.class, DATE_CONVERTER);
		map.put(java.sql.Date.class, SQLDATE_CONVERTER);
		map.put(Time.class, TIME_CONVERTER);
		map.put(Timestamp.class, TIMESTAMP_CONVERTER);

		// url and others
		map.put(URL.class, URL_CONVERTER);
		map.put(URI.class, URI_CONVERTER);
		map.put(InputStream.class, INPUTSTREAM_CONVERTER);
		map.put(Reader.class, READER_CONVERTER);
	}

	public synchronized static void clear() {
		map.clear();
		initialized = false;
		init();
	}

	public static <T> T convert(Object target, Class<T> convertClass) {
		return convert(target, convertClass, NULL_PATTERN);
	}

	@SuppressWarnings("unchecked")
	public static <T> T convert(Object target, Class<T> convertClass,
			String pattern) {
		init();
		Converter<T> converter = getConverter(convertClass);
		if (converter == null) {
			if (convertClass.isInstance(target)) {
				return (T) target;
			} else {
				return null;
			}
		}
		return converter.convert(target, pattern);
	}

	@SuppressWarnings("unchecked")
	private static <T> Converter<T> getConverter(Class<T> convertClass) {
		Assertion.notNull(convertClass);
		if (convertClass.isEnum()) {
			return new EnumConverter(convertClass);
		} else {
			return (Converter<T>) map.get(convertClass);
		}
	}

	public static String convertAsString(Object o) {
		return convertAsString(o, NULL_PATTERN);
	}

	public static String convertAsString(Object o, String pattern) {
		return STRING_CONVERTER.convert(o, pattern);
	}

	public static BigDecimal convertAsBigDecimal(Object o) {
		return convertAsBigDecimal(o, NULL_PATTERN);
	}

	public static BigDecimal convertAsBigDecimal(Object o, String pattern) {
		return BIGDECIMAL_CONVERTER.convert(o, pattern);
	}

	public static BigInteger convertAsBigInteger(Object o) {
		return convertAsBigInteger(o, NULL_PATTERN);
	}

	public static BigInteger convertAsBigInteger(Object o, String pattern) {
		return BIGINTEGER_CONVERTER.convert(o, pattern);
	}

	public static byte[] convertAsBinary(Object o) {
		return convertAsBinary(o, NULL_PATTERN);
	}

	public static byte[] convertAsBinary(Object o, String pattern) {
		return BINARY_CONVERTER.convert(o, pattern);
	}

	public static Boolean convertAsBoolean(Object o) {
		return convertAsBoolean(o, NULL_PATTERN);
	}

	public static Boolean convertAsBoolean(Object o, String pattern) {
		return BOOLEAN_CONVERTER.convert(o, pattern);
	}

	public static Boolean convertAsPrimitiveBoolean(Object o) {
		return convertAsPrimitiveBoolean(o, NULL_PATTERN);
	}

	public static Boolean convertAsPrimitiveBoolean(Object o, String pattern) {
		return PRIMITIVE_BOOLEAN_CONVERTER.convert(o, pattern);
	}

	public static Byte convertAsByte(Object o) {
		return convertAsByte(o, NULL_PATTERN);
	}

	public static Byte convertAsByte(Object o, String pattern) {
		return BYTE_CONVERTER.convert(o, pattern);
	}

	public static Byte convertAsPrimitiveByte(Object o) {
		return convertAsPrimitiveByte(o, NULL_PATTERN);
	}

	public static Byte convertAsPrimitiveByte(Object o, String pattern) {
		return PRIMITIVE_BYTE_CONVERTER.convert(o, pattern);
	}

	public static Double convertAsDouble(Object o) {
		return convertAsDouble(o, NULL_PATTERN);
	}

	public static Double convertAsDouble(Object o, String pattern) {
		return DOUBLE_CONVERTER.convert(o, pattern);
	}

	public static Double convertAsPrimitiveDouble(Object o) {
		return convertAsPrimitiveDouble(o, NULL_PATTERN);
	}

	public static Double convertAsPrimitiveDouble(Object o, String pattern) {
		return PRIMITIVE_DOUBLE_CONVERTER.convert(o, pattern);
	}

	public static Float convertAsFloat(Object o) {
		return convertAsFloat(o, NULL_PATTERN);
	}

	public static Float convertAsFloat(Object o, String pattern) {
		return FLOAT_CONVERTER.convert(o, pattern);
	}

	public static Float convertAsPrimitiveFloat(Object o) {
		return convertAsPrimitiveFloat(o, NULL_PATTERN);
	}

	public static Float convertAsPrimitiveFloat(Object o, String pattern) {
		return PRIMITIVE_FLOAT_CONVERTER.convert(o, pattern);
	}

	public static Integer convertAsInteger(Object o) {
		return convertAsInteger(o, NULL_PATTERN);
	}

	public static Integer convertAsInteger(Object o, String pattern) {
		return INTEGER_CONVERTER.convert(o, pattern);
	}

	public static Integer convertAsPrimitiveInteger(Object o) {
		return convertAsPrimitiveInteger(o, NULL_PATTERN);
	}

	public static Integer convertAsPrimitiveInteger(Object o, String pattern) {
		return PRIMITIVE_INTEGER_CONVERTER.convert(o, pattern);
	}

	public static Long convertAsLong(Object o) {
		return convertAsLong(o, NULL_PATTERN);
	}

	public static Long convertAsLong(Object o, String pattern) {
		return LONG_CONVERTER.convert(o, pattern);
	}

	public static Long convertAsPrimitiveLong(Object o) {
		return convertAsPrimitiveLong(o, NULL_PATTERN);
	}

	public static Long convertAsPrimitiveLong(Object o, String pattern) {
		return PRIMITIVE_LONG_CONVERTER.convert(o, pattern);
	}

	public static Short convertAsShort(Object o) {
		return convertAsShort(o, NULL_PATTERN);
	}

	public static Short convertAsShort(Object o, String pattern) {
		return SHORT_CONVERTER.convert(o, pattern);
	}

	public static Short convertAsPrimitiveShort(Object o) {
		return convertAsPrimitiveShort(o, NULL_PATTERN);
	}

	public static Short convertAsPrimitiveShort(Object o, String pattern) {
		return PRIMITIVE_SHORT_CONVERTER.convert(o, pattern);
	}

	public static java.sql.Date convertAsSqlDate(Object o) {
		return convertAsSqlDate(o, NULL_PATTERN);
	}

	public static java.sql.Date convertAsSqlDate(Object o, String pattern) {
		return SQLDATE_CONVERTER.convert(o, pattern);
	}

	public static Timestamp convertAsTimestamp(Object o) {
		return convertAsTimestamp(o, NULL_PATTERN);
	}

	public static Timestamp convertAsTimestamp(Object o, String pattern) {
		return TIMESTAMP_CONVERTER.convert(o, pattern);
	}

	public static Time convertAsTime(Object o) {
		return convertAsTime(o, NULL_PATTERN);
	}

	public static Time convertAsTime(Object o, String pattern) {
		return TIME_CONVERTER.convert(o, pattern);
	}

	public static Calendar convertAsCalendar(Object o) {
		return convertAsCalendar(o, NULL_PATTERN);
	}

	public static Calendar convertAsCalendar(Object o, String pattern) {
		return CALENDAR_CONVERTER.convert(o, pattern);
	}

	public static Date convertAsDate(Object o) {
		return convertAsDate(o, NULL_PATTERN);
	}

	public static Date convertAsDate(Object o, String pattern) {
		return DATE_CONVERTER.convert(o, pattern);
	}

	public static URL convertAsURL(Object o) {
		return URL_CONVERTER.convert(o, NULL_PATTERN);
	}

	public static URI convertAsURI(Object o) {
		return URI_CONVERTER.convert(o, NULL_PATTERN);
	}

	public static <T extends Enum<T>> Object convertAsEnum(Object o, Class<T> c) {
		if (o == null) {
			return null;
		}
		if (c.isEnum() == false || o.getClass().isEnum()) {
			return o;
		}
		EnumConverter<T> enumConverter = new EnumConverter<T>(c);
		return enumConverter.convert(o);
	}

	public static interface Converter<T> {

		T convert(Object o);

		T convert(Object o, String pattern);

	}

	public static Converter<BigDecimal> BIGDECIMAL_CONVERTER = new Converter<BigDecimal>() {

		@Override
		public BigDecimal convert(Object o, String pattern) {
			if (o == null) {
				return null;
			}
			if (BigDecimal.class.isInstance(o)) {
				return (BigDecimal) o;
			} else if (o instanceof Date) {
				if (pattern != null) {
					return new BigDecimal(new SimpleDateFormat(pattern)
							.format(o));
				} else {
					return new BigDecimal(((Date) o).getTime());
				}
			} else if (Integer.class.isInstance(o)) {
				int i = Integer.class.cast(o).intValue();
				return new BigDecimal(i);
			} else if (Double.class.isInstance(o)) {
				double d = Double.class.cast(o).doubleValue();
				return new BigDecimal(d);
			} else if (Long.class.isInstance(o)) {
				long l = Long.class.cast(o).longValue();
				return new BigDecimal(l);
			} else if (Float.class.isInstance(o)) {
				float f = Float.class.cast(o).floatValue();
				return new BigDecimal(f);
			} else if (Byte.class.isInstance(o)) {
				byte b = Byte.class.cast(o).byteValue();
				return new BigDecimal(b);
			} else if (BigInteger.class.isInstance(o)) {
				BigInteger bi = BigInteger.class.cast(o);
				return new BigDecimal(bi);
			} else if (String.class.isInstance(o)) {
				String s = DecimalFormatUtil.normalize((String) o);
				if (StringUtil.isEmpty(s)) {
					return null;
				} else {
					return new BigDecimal(s);
				}
			} else {
				return null;
			}

		}

		@Override
		public BigDecimal convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

	};

	public static Converter<BigInteger> BIGINTEGER_CONVERTER = new Converter<BigInteger>() {

		@Override
		public BigInteger convert(Object o, String pattern) {
			if (o == null) {
				return null;
			} else if (o instanceof BigInteger) {
				return (BigInteger) o;
			} else if (o instanceof String) {
				String s = DecimalFormatUtil.normalize(String.class.cast(o));
				return (StringUtil.isEmpty(s) == false) ? new BigInteger(s)
						: null;
			} else if (o instanceof byte[]) {
				byte[] bytes = (byte[]) o;
				return new BigInteger(bytes);
			} else {
				String s = DecimalFormatUtil.normalize(o.toString());
				return (StringUtil.isEmpty(s) == false) ? new BigInteger(s)
						: null;
			}
		}

		@Override
		public BigInteger convert(Object o) {
			return convert(o, null);
		}

	};

	public static Converter<Byte> BYTE_CONVERTER = new Converter<Byte>() {

		@Override
		public Byte convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

		protected Byte toByte(String s) {
			if (StringUtil.isEmpty(s)) {
				return null;
			}
			final String normalize = DecimalFormatUtil.normalize(s);
			return Byte.valueOf(normalize);
		}

		@Override
		public Byte convert(Object o, String pattern) {
			if (o == null) {
				return null;
			} else if (o instanceof Byte) {
				return (Byte) o;
			} else if (o instanceof Number) {
				return new Byte(((Number) o).byteValue());
			} else if (o instanceof String) {
				return toByte((String) o);
			} else if (o instanceof Date) {
				if (pattern != null) {
					return new Byte(new SimpleDateFormat(pattern).format(o));
				}
				return new Byte((byte) ((Date) o).getTime());
			} else if (o instanceof Boolean) {
				return ((Boolean) o).booleanValue() ? new Byte((byte) 1)
						: new Byte((byte) 0);
			} else {
				return null;
			}
		}
	};

	public static Converter<Byte> PRIMITIVE_BYTE_CONVERTER = new Converter<Byte>() {

		@Override
		public Byte convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

		@Override
		public Byte convert(Object o, String pattern) {
			Byte convert = BYTE_CONVERTER.convert(o, pattern);
			if (convert == null) {
				return Constants.BYTE_DEFAULT_VALUE;
			}
			return convert;
		}

	};

	public static Converter<byte[]> BINARY_CONVERTER = new Converter<byte[]>() {

		@Override
		public byte[] convert(Object o) {
			if (o instanceof byte[]) {
				return (byte[]) o;
			} else if (o == null) {
				return null;
			} else if (o instanceof String) {
				return Base64Util.decode((String) o);
			} else {
				return null;
			}
		}

		@Override
		public byte[] convert(Object o, String pattern) {
			return convert(o);
		}

	};

	public static Converter<Boolean> BOOLEAN_CONVERTER = new Converter<Boolean>() {

		@Override
		public Boolean convert(Object value) {
			if (value == null) {
				return null;
			}
			if (Boolean.class.isInstance(value)) {
				return Boolean.class.cast(value);
			} else if (String.class.isInstance(value)) {
				String s = String.class.cast(value);
				final boolean matches = YES_PATTERN.matcher(s).matches();
				return Boolean.valueOf(matches);
			} else if (Number.class.isInstance(value)) {
				Number n = Number.class.cast(value);
				return Boolean.valueOf(n.intValue() != 0);
			} else {
				return null;
			}
		}

		@Override
		public Boolean convert(Object o, String pattern) {
			return convert(o);
		}

	};

	public static Converter<Boolean> PRIMITIVE_BOOLEAN_CONVERTER = new Converter<Boolean>() {

		@Override
		public Boolean convert(Object o) {
			Boolean convert = BOOLEAN_CONVERTER.convert(o);
			if (convert == null) {
				return Constants.BOOLEAN_DEFAULT_VALUE;
			}
			return convert;
		}

		@Override
		public Boolean convert(Object o, String pattern) {
			return convert(o);
		}

	};

	public static Converter<AtomicBoolean> ATOMIC_BOOLEAN_CONVERTER = new Converter<AtomicBoolean>() {

		@Override
		public AtomicBoolean convert(Object o) {
			Boolean b = BOOLEAN_CONVERTER.convert(o);
			return toAtomicBoolean(b);
		}

		@Override
		public AtomicBoolean convert(Object o, String pattern) {
			Boolean b = BOOLEAN_CONVERTER.convert(o, pattern);
			return toAtomicBoolean(b);
		}

		protected AtomicBoolean toAtomicBoolean(Boolean b) {
			if (b != null) {
				return new AtomicBoolean(b.booleanValue());
			} else {
				return null;
			}
		}

	};

	public static Converter<Calendar> CALENDAR_CONVERTER = new Converter<Calendar>() {

		@Override
		public Calendar convert(Object o, String pattern) {
			if (o instanceof Calendar) {
				return (Calendar) o;
			}
			Date date = DATE_CONVERTER.convert(o, pattern);
			if (date != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				return cal;
			}
			return null;
		}

		@Override
		public Calendar convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

	};

	public abstract static class DateConverter implements Converter<Date> {

		public Date toDate(String s, String pattern) {
			return toDate(s, pattern, Locale.getDefault());
		}

		public Date toDate(String s, String pattern, Locale locale) {
			SimpleDateFormat sdf = getDateFormat(s, pattern, locale);
			try {
				return sdf.parse(s);
			} catch (ParseException ex) {
				// TODO
				throw new RuntimeException(ex);
			}
		}

		public SimpleDateFormat getDateFormat(String s, String pattern,
				Locale locale) {
			if (pattern != null) {
				return createSimpleDateFormat(pattern);
			}
			return getDateFormat(s, locale);
		}

		public SimpleDateFormat getDateFormat(String s, Locale locale) {
			String pattern = getPattern(locale);
			String shortPattern = removeDelimiter(pattern);
			String delimitor = findDelimiter(s);
			if (delimitor == null) {
				if (s.length() == shortPattern.length()) {
					return createSimpleDateFormat(shortPattern);
				}
				if (s.length() == shortPattern.length() + 2) {
					return createSimpleDateFormat(StringUtil.replace(
							shortPattern, "yy", "yyyy"));
				}
			} else {
				String[] array = StringUtil.split(s, delimitor);
				for (int i = 0; i < array.length; ++i) {
					if (array[i].length() == 4) {
						pattern = StringUtil.replace(pattern, "yy", "yyyy");
						break;
					}
				}
				return createSimpleDateFormat(pattern);
			}
			return createSimpleDateFormat();
		}

		public SimpleDateFormat getDateFormat(Locale locale) {
			return createSimpleDateFormat(getPattern(locale));
		}

		public SimpleDateFormat getY4DateFormat(Locale locale) {
			return createSimpleDateFormat(getY4Pattern(locale));
		}

		public String getY4Pattern(Locale locale) {
			String pattern = getPattern(locale);
			if (pattern.indexOf("yyyy") < 0) {
				pattern = StringUtil.replace(pattern, "yy", "yyyy");
			}
			return pattern;
		}

		public String getPattern(Locale locale) {
			SimpleDateFormat df = (SimpleDateFormat) DateFormat
					.getDateInstance(DateFormat.SHORT, locale);
			String pattern = df.toPattern();
			int index = pattern.indexOf(' ');
			if (index > 0) {
				pattern = pattern.substring(0, index);
			}
			if (pattern.indexOf("MM") < 0) {
				pattern = StringUtil.replace(pattern, "M", "MM");
			}
			if (pattern.indexOf("dd") < 0) {
				pattern = StringUtil.replace(pattern, "d", "dd");
			}
			return pattern;
		}

		public String findDelimiter(String value) {
			for (int i = 0; i < value.length(); ++i) {
				char c = value.charAt(i);
				if (Character.isDigit(c)) {
					continue;
				}
				return Character.toString(c);
			}
			return null;
		}

		public String removeDelimiter(String pattern) {
			StringBuilder builder = new StringBuilder(pattern.length());
			for (int i = 0; i < pattern.length(); ++i) {
				char c = pattern.charAt(i);
				if (c == 'y' || c == 'M' || c == 'd') {
					builder.append(c);
				}
			}
			return builder.toString();
		}

		protected SimpleDateFormat createSimpleDateFormat(String pattern) {
			return new SimpleDateFormat(pattern);
		}

		protected SimpleDateFormat createSimpleDateFormat() {
			return new SimpleDateFormat();
		}

	}

	public static DateConverter DATE_CONVERTER = new DateConverter() {

		@Override
		public Date convert(Object o, String pattern) {
			if (o == null) {
				return null;
			} else if (o instanceof String) {
				return toDate((String) o, pattern);
			} else if (o instanceof Date) {
				return (Date) o;
			} else if (o instanceof Calendar) {
				return ((Calendar) o).getTime();
			} else if (o instanceof Number) {
				return new Date(((Number) o).longValue());
			}
			return null;
		}

		@Override
		public Date convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

	};

	public static Converter<Double> DOUBLE_CONVERTER = new Converter<Double>() {

		@Override
		public Double convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

		@Override
		public Double convert(Object o, String pattern) {
			if (o == null) {
				return null;
			} else if (o instanceof Double) {
				return (Double) o;
			} else if (o instanceof Number) {
				return ((Number) o).doubleValue();
			} else if (o instanceof java.util.Date) {
				if (pattern != null) {
					return new Double(new SimpleDateFormat(pattern).format(o));
				}
				return new Double(((java.util.Date) o).getTime());
			} else if (o instanceof String) {
				String s = DecimalFormatUtil.normalize((String) o);
				if (StringUtil.isEmpty(s)) {
					return null;
				}
				return Double.valueOf(s);
			} else {
				return null;
			}
		}

	};

	public static Converter<Double> PRIMITIVE_DOUBLE_CONVERTER = new Converter<Double>() {

		@Override
		public Double convert(Object o, String pattern) {
			Double convert = DOUBLE_CONVERTER.convert(o, pattern);
			if (convert == null) {
				return Constants.DOUBLE_DEFAULT_VALUE;
			}
			return convert;
		}

		@Override
		public Double convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

	};

	public static Converter<Float> FLOAT_CONVERTER = new Converter<Float>() {

		@Override
		public Float convert(Object o, String pattern) {
			if (o == null) {
				return null;
			} else if (o instanceof Float) {
				return (Float) o;
			} else if (o instanceof Number) {
				return ((Number) o).floatValue();
			} else if (o instanceof java.util.Date) {
				if (pattern != null) {
					return new Float(new SimpleDateFormat(pattern).format(o));
				}
				return new Float(((java.util.Date) o).getTime());
			} else if (o instanceof String) {
				String s = DecimalFormatUtil.normalize((String) o);
				if (StringUtil.isEmpty(s)) {
					return null;
				}
				return Float.valueOf(s);
			} else {
				return null;
			}
		}

		@Override
		public Float convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

	};

	public static Converter<Float> PRIMITIVE_FLOAT_CONVERTER = new Converter<Float>() {

		@Override
		public Float convert(Object o, String pattern) {
			Float convert = FLOAT_CONVERTER.convert(o, pattern);
			if (convert == null) {
				return Constants.FLOAT_DEFAULT_VALUE;
			}
			return convert;
		}

		@Override
		public Float convert(Object o) {
			return convert(o, NULL_PATTERN);
		}
	};

	public static Converter<Integer> INTEGER_CONVERTER = new Converter<Integer>() {

		@Override
		public Integer convert(Object o, String pattern) {
			if (o == null) {
				return null;
			} else if (o instanceof Integer) {
				return (Integer) o;
			} else if (o instanceof Number) {
				return ((Number) o).intValue();
			} else if (o instanceof java.util.Date) {
				if (pattern != null) {
					return new Integer(new SimpleDateFormat(pattern).format(o));
				}
				return new Integer(Long.valueOf(((java.util.Date) o).getTime())
						.intValue());
			} else if (o instanceof String) {
				String s = DecimalFormatUtil.normalize((String) o);
				if (StringUtil.isEmpty(s)) {
					return null;
				}
				return Integer.valueOf(s);
			} else if (o instanceof Boolean) {
				return ((Boolean) o).booleanValue() ? 1 : 0;
			} else {
				return null;
			}
		}

		@Override
		public Integer convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

	};

	public static Converter<Integer> PRIMITIVE_INTEGER_CONVERTER = new Converter<Integer>() {

		@Override
		public Integer convert(Object o, String pattern) {
			Integer convert = INTEGER_CONVERTER.convert(o, pattern);
			if (convert == null) {
				return Constants.INT_DEFAULT_VALUE;
			}
			return convert;
		}

		@Override
		public Integer convert(Object o) {
			return convert(o, NULL_PATTERN);
		}
	};

	public static Converter<AtomicInteger> ATOMIC_INTEGER_CONVERTER = new Converter<AtomicInteger>() {

		@Override
		public AtomicInteger convert(Object o) {
			Integer i = INTEGER_CONVERTER.convert(o);
			return toAtomicInteger(i);
		}

		@Override
		public AtomicInteger convert(Object o, String pattern) {
			Integer i = INTEGER_CONVERTER.convert(o, pattern);
			return toAtomicInteger(i);
		}

		protected AtomicInteger toAtomicInteger(Integer i) {
			if (i != null) {
				return new AtomicInteger(i.intValue());
			} else {
				return null;
			}
		}

	};

	public static Converter<Long> LONG_CONVERTER = new Converter<Long>() {

		@Override
		public Long convert(Object o, String pattern) {
			if (o == null) {
				return null;
			} else if (o instanceof Long) {
				return (Long) o;
			} else if (o instanceof Number) {
				return ((Number) o).longValue();
			} else if (o instanceof String) {
				String s = DecimalFormatUtil.normalize((String) o);
				if (StringUtil.isEmpty(s)) {
					return null;
				}
				return Long.valueOf(s);
			} else if (o instanceof Date) {
				return ((Date) o).getTime();
			} else if (o instanceof Boolean) {
				return ((Boolean) o).booleanValue() ? 1L : 0L;
			} else {
				return null;
			}
		}

		@Override
		public Long convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

	};

	public static Converter<Long> PRIMITIVE_LONG_CONVERTER = new Converter<Long>() {

		@Override
		public Long convert(Object o, String pattern) {
			Long convert = LONG_CONVERTER.convert(o, pattern);
			if (convert == null) {
				return Constants.LONG_DEFAULT_VALUE;
			}
			return convert;
		}

		@Override
		public Long convert(Object o) {
			return convert(o, NULL_PATTERN);
		}
	};

	public static Converter<AtomicLong> ATOMIC_LONG_CONVERTER = new Converter<AtomicLong>() {

		@Override
		public AtomicLong convert(Object o) {
			Long l = LONG_CONVERTER.convert(o);
			return toAtomicLong(l);
		}

		@Override
		public AtomicLong convert(Object o, String pattern) {
			Long l = LONG_CONVERTER.convert(o, pattern);
			return toAtomicLong(l);
		}

		protected AtomicLong toAtomicLong(Long l) {
			if (l != null) {
				return new AtomicLong(l.longValue());
			} else {
				return null;
			}
		}

	};

	public static Converter<Short> SHORT_CONVERTER = new Converter<Short>() {

		@Override
		public Short convert(Object o, String pattern) {
			if (o == null) {
				return null;
			} else if (o instanceof Short) {
				return (Short) o;
			} else if (o instanceof Number) {
				return ((Number) o).shortValue();
			} else if (o instanceof java.util.Date) {
				if (pattern != null) {
					return new Short(new SimpleDateFormat(pattern).format(o));
				}
				return new Short(Long.valueOf(((java.util.Date) o).getTime())
						.shortValue());
			} else if (o instanceof String) {
				String s = DecimalFormatUtil.normalize((String) o);
				if (StringUtil.isEmpty(s)) {
					return null;
				}
				return Short.valueOf(s);
			} else if (o instanceof Boolean) {
				return ((Boolean) o).booleanValue() ? ((short) 1) : ((short) 0);
			} else {
				return null;
			}
		}

		@Override
		public Short convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

	};

	public static Converter<Short> PRIMITIVE_SHORT_CONVERTER = new Converter<Short>() {

		@Override
		public Short convert(Object o, String pattern) {
			Short convert = SHORT_CONVERTER.convert(o, pattern);
			if (convert == null) {
				return Constants.SHORT_DEFAULT_VALUE;
			}
			return convert;
		}

		@Override
		public Short convert(Object o) {
			return convert(o, NULL_PATTERN);
		}
	};

	public static Converter<java.sql.Date> SQLDATE_CONVERTER = new Converter<java.sql.Date>() {

		@Override
		public java.sql.Date convert(Object o, String pattern) {
			if (o instanceof java.sql.Date) {
				return (java.sql.Date) o;
			}
			Date date = DATE_CONVERTER.convert(o, pattern);
			if (date != null) {
				return new java.sql.Date(date.getTime());
			}
			return null;
		}

		@Override
		public java.sql.Date convert(Object o) {
			return convert(o, NULL_PATTERN);
		}
	};

	public static Converter<String> STRING_CONVERTER = new Converter<String>() {

		@Override
		public String convert(Object value, String pattern) {
			if (value == null) {
				return null;
			} else if (value instanceof String) {
				return (String) value;
			} else if (value instanceof Date) {
				return toString((Date) value, pattern);
			} else if (value instanceof Number) {
				return toString((Number) value, pattern);
			} else if (value instanceof byte[]) {
				return Base64Util.encode((byte[]) value);
			} else if (value instanceof URL) {
				return ((URL) value).toExternalForm();
			} else if (value instanceof URI) {
				try {
					return ((URI) value).toURL().toExternalForm();
				} catch (MalformedURLException e) {
					logger.log(Level.INFO, "URI conversion error:"
							+ e.getMessage());
					return null;
				}
			} else {
				return null;
			}
		}

		protected String toString(Number value, String pattern) {
			if (value != null) {
				if (pattern != null) {
					return new DecimalFormat(pattern).format(value);
				}
				return value.toString();
			}
			return null;
		}

		protected String toString(Date value, String pattern) {
			if (value != null) {
				if (pattern != null) {
					return new SimpleDateFormat(pattern).format(value);
				}
				return value.toString();
			}
			return null;
		}

		@Override
		public String convert(Object o) {
			return convert(o, NULL_PATTERN);
		}
	};

	public static Converter<Time> TIME_CONVERTER = new Converter<Time>() {

		@Override
		public Time convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

		@Override
		public Time convert(Object o, String pattern) {
			if (o instanceof Time) {
				return (Time) o;
			} else if (o instanceof String) {
				return toTime((String) o, pattern);
			} else if (o instanceof Number) {
				Number n = (Number) o;
				return new Time(n.longValue());
			} else {
				return null;
			}
		}

		protected Time toTime(String s, String pattern) {
			return toTime(s, pattern, Locale.getDefault());
		}

		public Time toTime(String s, String pattern, Locale locale) {
			SimpleDateFormat sdf = getTimeFormat(s, pattern, locale);
			try {
				Date date = sdf.parse(s);
				return new Time(date.getTime());
			} catch (ParseException ex) {
				// TODO
				throw new RuntimeException(ex);
			}
		}

		public SimpleDateFormat getTimeFormat(String s, String pattern,
				Locale locale) {
			if (pattern != null) {
				return createSimpleDateFormat(pattern);
			} else {
				return getTimeFormat(s, locale);
			}
		}

		public SimpleDateFormat getTimeFormat(String s, Locale locale) {
			String pattern = toPattern(locale);
			String shortPattern = removeDelimiter(pattern);
			if (s.length() == pattern.length()) {
				return createSimpleDateFormat(pattern);
			} else if (s.length() == shortPattern.length()) {
				return createSimpleDateFormat(shortPattern);
			} else {
				return createSimpleDateFormat(pattern);
			}
		}

		protected String toPattern(Locale locale) {
			return "HH:mm:ss";
			// SimpleDateFormat df = (SimpleDateFormat) DateFormat
			// .getDateInstance(DateFormat.DATE_FIELD, locale);
			// String pattern = df.toPattern();
			// int index = pattern.indexOf(' ');
			// if (index > 0) {
			// pattern = pattern.substring(index, pattern.length());
			// }
			// if (pattern.indexOf("HH") < 0) {
			// pattern = StringUtil.replace(pattern, "H", "HH");
			// }
			// if (pattern.indexOf("mm") < 0) {
			// pattern = StringUtil.replace(pattern, "m", "mm");
			// }
			// if (pattern.indexOf("ss") < 0) {
			// pattern = StringUtil.replace(pattern, "s", "ss");
			// }
			// return pattern;
		}

		public String removeDelimiter(String pattern) {
			StringBuilder builder = new StringBuilder(pattern.length());
			for (int i = 0; i < pattern.length(); ++i) {
				char c = pattern.charAt(i);
				if (c == 'h' || c == 'H' || c == 'm' || c == 's') {
					builder.append(c);
				}
			}
			return builder.toString();
		}

		protected SimpleDateFormat createSimpleDateFormat(String pattern) {
			return new SimpleDateFormat(pattern);
		}

	};

	public static Converter<Timestamp> TIMESTAMP_CONVERTER = new Converter<Timestamp>() {

		@Override
		public Timestamp convert(Object o) {
			return convert(o, NULL_PATTERN);
		}

		@Override
		public Timestamp convert(Object o, String pattern) {
			if (o instanceof Timestamp) {
				return (Timestamp) o;
			}
			Date date = DATE_CONVERTER.convert(o, pattern);
			if (date != null) {
				return new Timestamp(date.getTime());
			}
			return null;
		}
	};

	public static Converter<URL> URL_CONVERTER = new Converter<URL>() {
		@Override
		public URL convert(Object o) {
			if (o instanceof URL) {
				return (URL) o;
			}
			try {
				String url = STRING_CONVERTER.convert(o);
				if (url != null) {
					return new URL(url);
				}
				return null;
			} catch (MalformedURLException e) {
				logger.log(Level.SEVERE, "URL conversion error:"
						+ e.getMessage(), e.getMessage());
				return null;
			}
		}

		@Override
		public URL convert(Object o, String pattern) {
			return convert(o);
		}
	};

	public static Converter<URI> URI_CONVERTER = new Converter<URI>() {
		@Override
		public URI convert(Object o) {
			try {
				if (o instanceof URI) {
					return (URI) o;
				} else if (o instanceof URL) {
					return new URI(((URL) o).toExternalForm());
				}
				String url = STRING_CONVERTER.convert(o);
				if (url != null) {
					return new URI(url);
				}
				return null;
			} catch (URISyntaxException e) {
				logger.log(Level.SEVERE, "URI conversion error:"
						+ e.getMessage(), e.getMessage());
				return null;
			}
		}

		@Override
		public URI convert(Object o, String pattern) {
			return convert(o);
		}
	};

	public static Converter<InputStream> INPUTSTREAM_CONVERTER = new Converter<InputStream>() {

		@Override
		public InputStream convert(Object o) {
			if (o == null) {
				return null;
			}
			if (o instanceof InputStream) {
				return (InputStream) o;
			}
			try {
				if (o instanceof File) {
					return new FileInputStream((File) o);
				}
				URL url = URL_CONVERTER.convert(o);
				if (url != null) {
					return url.openStream();
				}
			} catch (IOException ex) {
				logger.log(Level.SEVERE, ex.getMessage(), ex);
			}
			return null;
		}

		@Override
		public InputStream convert(Object o, String pattern) {
			return convert(o);
		}
	};

	public static Converter<Reader> READER_CONVERTER = new Converter<Reader>() {
		@Override
		public Reader convert(Object o) {
			if (o == null) {
				return null;
			}
			if (o instanceof Reader) {
				return (Reader) o;
			}
			InputStream in = INPUTSTREAM_CONVERTER.convert(o);
			if (in != null) {
				return new InputStreamReader(in);
			}
			return null;
		}

		@Override
		public Reader convert(Object o, String pattern) {
			return convert(o);
		}
	};

	public static class EnumConverter<T extends Enum<T>> implements
			Converter<Enum<T>> {

		protected final Class<T> enumClass;

		public EnumConverter(Class<T> enumClassCandidate) {
			Assertion.notNull(enumClassCandidate);
			if (enumClassCandidate.isEnum() == false) {
				throw new IllegalArgumentException("not enum class : "
						+ this.enumClass.getName());
			}
			this.enumClass = enumClassCandidate;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Enum<T> convert(Object o) {
			if (o == null) {
				return null;
			}
			if (o.getClass().isEnum()) {
				return (Enum<T>) o;
			}
			final String s = o.toString();
			return Enum.valueOf(this.enumClass, s);
		}

		@Override
		public Enum<T> convert(Object o, String pattern) {
			return convert(o);
		}

	}

	public static <T> void addConverter(Class<T> clazz, Converter<T> converter) {
		map.put(clazz, converter);
	}

}
