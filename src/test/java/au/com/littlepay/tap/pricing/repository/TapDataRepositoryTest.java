package au.com.littlepay.tap.pricing.repository;

import au.com.littlepay.tap.pricing.model.TapDataInput;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TapDataRepositoryTest {

    private TapDataRepository tapDataRepository;

    @BeforeAll
    public void setUp() {
        tapDataRepository = new TapDataRepository();
        List<TapDataInput> tapDataInputs = Arrays.asList(TapDataInput.builder().id("1").pan("12030000000").busId("Bus01").build(),
                TapDataInput.builder().id("2").pan("12040000000").busId("Bus02").build(),
                TapDataInput.builder().id("3").pan("12050000000").busId("Bus03").build(),
                TapDataInput.builder().id("4").pan("12040000000").busId("Bus02").build());
        tapDataInputs.stream().forEach(tapDataInput -> tapDataRepository.save(tapDataInput));
    }

    @Test
    void findAll() {
        List<TapDataInput> data = tapDataRepository.findAll();
        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.size(), 4);
    }

    @Test
    void findById() {
        TapDataInput tapDataInput = tapDataRepository.findById("2");
        Assertions.assertNotNull(tapDataInput);
        Assertions.assertEquals(tapDataInput.getId(), "2");
        Assertions.assertEquals(tapDataInput.getPan(), "12040000000");
    }

    @Test
    void findByPan() {
        List<TapDataInput> tapDataInputs = tapDataRepository.findByPan("12040000000");
        Assertions.assertNotNull(tapDataInputs);
        Assertions.assertEquals(tapDataInputs.size(), 2);
    }

    @Test
    void getMapByPAN() {
        Map<String, List<TapDataInput>> mapByPAN = tapDataRepository.getMapByPAN();
        Assertions.assertNotNull(mapByPAN);
        Assertions.assertEquals(mapByPAN.size(), 3);

    }
}