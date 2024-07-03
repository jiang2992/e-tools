package org.jiang.tools.sql;

import lombok.Getter;
import org.jiang.tools.data.EasyData;
import org.jiang.tools.exception.DataStatusException;
import org.jiang.tools.exception.SystemException;

import java.io.*;
import java.nio.file.Files;
import java.util.function.Function;

/**
 * class description
 *
 * @author Bin
 * @since 2024/7/2 09:15
 */
public class SqlParser implements Closeable {

    private static final long STATEMENT_MAX_LENGTH = 1024 * 1024 * 10;

    @Getter
    private InputStream inputStream;

    private BufferedReader reader;
    private String residual;

    public SqlParser(InputStream inputStream) {
        this.inputStream = inputStream;
        if (inputStream.markSupported()) {
            inputStream.mark(0);
        }
    }

    public static SqlParser of(InputStream inputStream) {
        return new SqlParser(inputStream);
    }

    public static SqlParser of(String str) {
        return of(EasyData.of(str).streamValue());
    }

    public static SqlParser of(File file) throws IOException {
        return of(Files.newInputStream(file.toPath()));
    }

    private BufferedReader getReader() throws IOException {
        if (inputStream == null) {
            throw new DataStatusException("parser is closed");
        }
        if (reader != null && reader.ready()) {
            return reader;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader;
    }

    public String read() throws IOException {
        reader = this.getReader();
        String line;
        StringBuilder sqlBuilder = new StringBuilder();
        long statementLength = 0;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            sqlBuilder.append(line);
            if (line.endsWith(";")) {
                return sqlBuilder.toString();
            }
            statementLength += line.getBytes().length;
            if (statementLength > STATEMENT_MAX_LENGTH) {
                throw new SystemException("sql statement length exceed the limit");
            }
        }
        return null;
    }

    public SqlParser read(Function<String, Boolean> fun) throws IOException {
        String sql;
        while ((sql = read()) != null) {
            if(!fun.apply(sql)){
                break;
            }
        }
        return this;
    }

    public SqlParser reset() throws IOException {
        inputStream.reset();
        reader = null;
        return this;
    }

    @Override
    public void close() throws IOException {
        if (inputStream == null) {
            return;
        }
        try {
            inputStream.close();
        } finally {
            inputStream = null;
            reader = null;
        }
    }

}
