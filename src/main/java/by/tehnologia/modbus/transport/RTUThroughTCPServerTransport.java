package by.tehnologia.modbus.transport;

import by.tehnologia.exception.IncorrectCRCException;
import by.tehnologia.exception.NoResponseException;
import by.tehnologia.exception.UnexpectedLengthException;
import by.tehnologia.modbus.util.ModbusUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Выполняет запросы к устройству, поддерживающему протокол Modbus и работающему через TCP туннель в режиме
 * сервера
 * @see <a href="http://www.modbus.org/specs.php">Описание протокола</a>
 * @author Aleksandr Golovnyov
 */
public class RTUThroughTCPServerTransport implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RTUThroughTCPServerTransport.class);

    private static final int TIMEOUT = 10000;
    private static final int ATTEMPTS_NUMBER = 3;

    private Socket socket;

    public RTUThroughTCPServerTransport(InetSocketAddress socketAddress) throws IOException {

        String host = socketAddress.getHostString();
        int port = socketAddress.getPort();
        int attemptsConnection = ATTEMPTS_NUMBER;
        while (attemptsConnection > 0) {
            attemptsConnection--;
            try {
                socket = new Socket();
                socket.setSoTimeout(TIMEOUT);
                socket.connect(socketAddress, TIMEOUT);
                LOGGER.debug("Connected to server {}:{}", host, port);
                break;
            } catch (SocketTimeoutException e) {
                if (attemptsConnection == 0) {
                    throw e;
                }
                LOGGER.error("Connect to server {}:{} timed out", host, port);
            } catch (UnknownHostException e) {
                if (attemptsConnection == 0) {
                    throw e;
                }
                LOGGER.error("Unknown host", e);
            } catch (IOException e) {
                if (attemptsConnection == 0) {
                    throw e;
                }
                LOGGER.error("Failed connect to sensor. Reason: " + ExceptionUtils.getRootCauseMessage(e));
            }
        }
    }

    /**
     * Выполняет произвольный запрос к устройству
     * @param unitId сетевой номер устройства
     * @param function номер функции
     * @param data произвольные данные
     * @throws IOException ошибка ввода-вывода
     * @return возвращает полученные данные
     * @throws NoResponseException
     * @throws UnexpectedLengthException
     * @throws IncorrectCRCException
     */
    public byte[] execute(int unitId, int function, byte[] data)
            throws IOException, IncorrectCRCException, UnexpectedLengthException, NoResponseException {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) unitId);
        buffer.put((byte) function);
        buffer.put(data);
        int[] crc = ModbusUtil.calculateCRC(buffer.array(), 0, 2 + data.length);
        buffer.put((byte) crc[0]);
        buffer.put((byte) crc[1]);

        printRequest(unitId, buffer.array(), 4 + data.length);
        socket.getOutputStream().write(buffer.array(), 0, 4 + data.length);

        return recieve(unitId, function);
    }

    /**
     * Выполняет запрос к устройству
     * @param unitId сетевой номер устройства
     * @param function номер функции
     * @param address адрес записи
     * @param length длина записи
     * @return возвращает null, если данных не было полученно
     * @throws NoResponseException
     * @throws IncorrectCRCException
     * @throws UnexpectedLengthException
     * @throws IOException
     */
    public byte[] execute(int unitId, int function, int address, int length)
            throws NoResponseException, IncorrectCRCException, UnexpectedLengthException, IOException {

        byte[] request = {
                ModbusUtil.hiByte(address),
                ModbusUtil.lowByte(address),
                ModbusUtil.hiByte(length),
                ModbusUtil.lowByte(length) };

        return execute(unitId, function, request);
    }

    private byte[] recieve(int unitId, int function)
            throws IncorrectCRCException, UnexpectedLengthException, IOException, NoResponseException {

        int attemptsCount = ATTEMPTS_NUMBER;
        InputStream stream = socket.getInputStream();

        while (attemptsCount > 0) {

            LOGGER.debug("Attempt #{}", ATTEMPTS_NUMBER - attemptsCount + 1);

            attemptsCount--;

            try {
                byte[] receivedResponse;
                int receivedUnitId = stream.read();
                int receivedFunction = stream.read();
                byte[] receivedData = null;
                byte[] receivedCRC = new byte[2];
                int received;
                int errorCode = 0;

                if (receivedFunction > 0 && receivedFunction < 5) {
                    int receivedDataLength = stream.read();

                    receivedData = new byte[receivedDataLength];
                    received = stream.read(receivedData);

                    if (!equalsLength(received, receivedData.length, attemptsCount)) {
                        continue;
                    }

                    received = stream.read(receivedCRC);

                    if (!equalsLength(received, receivedCRC.length, attemptsCount)) {
                        continue;
                    }

                    receivedResponse = new byte[3 + receivedData.length + receivedCRC.length];
                    receivedResponse[0] = (byte) receivedUnitId;
                    receivedResponse[1] = (byte) receivedFunction;
                    receivedResponse[2] = (byte) receivedData.length;
                    System.arraycopy(receivedData, 0, receivedResponse, 3, receivedData.length);
                    int crcPos = 3 + receivedData.length;
                    System.arraycopy(receivedCRC, 0, receivedResponse, crcPos, receivedCRC.length);

                } else if (receivedFunction >= 0x80) { // В случае получения кода исключительной ситуации
                    errorCode = stream.read();
                    received = stream.read(receivedCRC);

                    if (!equalsLength(received, receivedCRC.length, attemptsCount)) {
                        continue;
                    }

                    receivedResponse = new byte[3 + receivedCRC.length];
                    receivedResponse[0] = (byte) receivedUnitId;
                    receivedResponse[1] = (byte) receivedFunction;
                    receivedResponse[2] = (byte) errorCode;
                    System.arraycopy(receivedCRC, 0, receivedResponse, 3, receivedCRC.length);
                } else {
                    byte[] buffer = new byte[1024];
                    int n = stream.read(buffer);
                    receivedResponse = new byte[n + 2];
                    receivedResponse[0] = (byte) receivedUnitId;
                    receivedResponse[1] = (byte) receivedFunction;
                    System.arraycopy(buffer, 0, receivedResponse, 2, n);
                    receivedCRC = Arrays.copyOfRange(buffer, n - 2, n);
                }

                printResponse(unitId, receivedResponse);

                int[] crc = ModbusUtil.calculateCRC(receivedResponse, 0, receivedResponse.length - 2);

                if (!equalsCRC(crc, receivedCRC, attemptsCount)) {
                    continue;
                }

                if (receivedUnitId != unitId) {
                    LOGGER.error("Incorrect device id. Expected {}, received {}", unitId, receivedUnitId);
                    continue;
                }

                if ((receivedFunction & 0x7f) != function) {
                    LOGGER.error("Incorrect function. Expected {}, received {}", function, receivedFunction);
                    continue;
                }

                if (receivedFunction >= 0x80) {
                    LOGGER.error("Function " + (receivedFunction & 0x7F) + " returned error code "
                            + errorCode);
                }

                attemptsCount = 0;

                return receivedData;
            } catch (SocketTimeoutException e) {
                LOGGER.error("Socket timeout");
            }
        }

        LOGGER.error("No response from sensor");
        throw new NoResponseException();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        String host = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        LOGGER.debug("Disconnected from server {}:{}", host, port);
    }

    private boolean equalsLength(int received, int expected, int attemptsCount)
            throws UnexpectedLengthException {
        if (received != expected) {
            LOGGER.error("Received {} bytes, expected {} bytes", received, expected);

            if (attemptsCount == 0) {
                throw new UnexpectedLengthException(
                        "Received " + received + " bytes, expected " + expected + " bytes");
            }

            return false;
        }

        return true;
    }

    private boolean equalsCRC(int[] calculatedCRC, byte[] receivedCRC, int attemptsCount)
            throws IncorrectCRCException {
        if (calculatedCRC[0] != (receivedCRC[0] & 0xff) || calculatedCRC[1] != (receivedCRC[1] & 0xff)) {
            LOGGER.error("Received CRC incorrect");

            if (attemptsCount == 0) {
                throw new IncorrectCRCException("Received CRC incorrect");
            }

            return false;
        }
        return true;
    }

    private void printRequest(int unitId, byte[] array, int size) {
        printData("REQ", unitId, array, size);
    }

    private void printResponse(int unitId, byte[] array) {
        printResponse(unitId, array, array.length);
    }

    private void printResponse(int unitId, byte[] array, int size) {
        printData("RES", unitId, array, size);
    }

    private void printData(String type, int unitId, byte[] array, int size) {
        if (LOGGER.isDebugEnabled()) {
            StringBuilder output = new StringBuilder(type + " (" + unitId + "): ");

            for (int i = 0; i < size; i++) {
                output.append(String.format("%02X ", array[i]));
            }

            LOGGER.debug(output.toString());
        }
    }
}
