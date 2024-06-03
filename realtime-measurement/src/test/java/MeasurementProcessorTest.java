import com.example.realtimemeasurement.dto.Avg;
import com.example.realtimemeasurement.dto.BleMeasurement;
import com.example.realtimemeasurement.service.MeasurementProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MeasurementProcessorTest {
    private RabbitTemplate rabbitTemplate;
    private final MeasurementProcessor measurementProcessor = new MeasurementProcessor(rabbitTemplate);
    @Test
    void testZeroReadings(){

        //     Test Results:
        //          sumTemperature = 0.0
        //          avgTemperature = 0.0
        //          sumHumidity = 0.0
        //          avgHumidity = 0.0
        //          readingCount = 3

        BleMeasurement.Data data = new BleMeasurement.Data(0,0);
        BleMeasurement.Measurement measurementObj = new BleMeasurement.Measurement("test","test",0, data);
        BleMeasurement.BleReporter reporter = new BleMeasurement.BleReporter("test", "test", "test", "test", "test", "test");
        List<BleMeasurement.Measurement> measurementArr = new ArrayList<>();
        measurementArr.add(measurementObj);

        HashMap<String, Avg> devices = new HashMap<>();

        BleMeasurement measurement = new BleMeasurement(reporter,measurementArr);

        measurementProcessor.calculateAverage(measurement);
        measurementProcessor.calculateAverage(measurement);
        measurementProcessor.calculateAverage(measurement);

        devices = measurementProcessor.getDevices();

        int test = 0;
    }
    @Test
    void testEmptyObject(){

        BleMeasurement measurement = new BleMeasurement();

        HashMap<String, Avg> devices = new HashMap<>();

        measurementProcessor.calculateAverage(measurement);
        measurementProcessor.calculateAverage(measurement);
        measurementProcessor.calculateAverage(measurement);

        devices = measurementProcessor.getDevices();

        int test = 0;
    }
    @Test
    void testEmptyValue(){

        //Automatically handled as 0
    }
    @Test
    void testLargeNumber(){

        BleMeasurement.Data data = new BleMeasurement.Data(1000993993,485884858);
        BleMeasurement.Measurement measurementObj = new BleMeasurement.Measurement("test","test",0, data);
        BleMeasurement.BleReporter reporter = new BleMeasurement.BleReporter("test", "test", "test", "test", "test", "test");
        List<BleMeasurement.Measurement> measurementArr = new ArrayList<>();
        measurementArr.add(measurementObj);

        HashMap<String, Avg> devices = new HashMap<>();

        BleMeasurement measurement = new BleMeasurement(reporter,measurementArr);

        measurementProcessor.calculateAverage(measurement);
        measurementProcessor.calculateAverage(measurement);
        measurementProcessor.calculateAverage(measurement);

        devices = measurementProcessor.getDevices();

        Assertions.assertEquals(3, devices.get("test").getReadingCount());

    }
    @Test
    void testNegativeNumber(){

        BleMeasurement.Data data = new BleMeasurement.Data(-1000993993,-485884858);
        BleMeasurement.Measurement measurementObj = new BleMeasurement.Measurement("test","test",0, data);
        BleMeasurement.BleReporter reporter = new BleMeasurement.BleReporter("test", "test", "test", "test", "test", "test");
        List<BleMeasurement.Measurement> measurementArr = new ArrayList<>();
        measurementArr.add(measurementObj);

        HashMap<String, Avg> devices = new HashMap<>();

        BleMeasurement measurement = new BleMeasurement(reporter,measurementArr);

        measurementProcessor.calculateAverage(measurement);
        measurementProcessor.calculateAverage(measurement);
        measurementProcessor.calculateAverage(measurement);

        devices = measurementProcessor.getDevices();

        int test = 0;
    }
}
