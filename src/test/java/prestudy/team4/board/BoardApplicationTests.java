package prestudy.team4.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import prestudy.team4.board.dto.JoinDto;
import prestudy.team4.board.repository.UserRepository;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void 회원가입_테스트() throws Exception {
        // given
        JoinDto request = new JoinDto();
        request.setUserid("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setNickname("테스트닉네임");
        request.setName("테스트유저");

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 로그인_테스트() throws Exception {
        // given - 먼저 회원가입
        JoinDto joinRequest = new JoinDto();
        joinRequest.setUserid("testuser");
        joinRequest.setEmail("test@example.com");
        joinRequest.setPassword("password123");
        joinRequest.setNickname("테스트닉네임");
        joinRequest.setName("테스트유저");

        mockMvc.perform(post("/api/v1/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joinRequest)));

        // 로그인 요청
        Map<String, String> loginRequest = Map.of(
            "username", "testuser",
            "password", "password123"
        );

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void 로그아웃_테스트() throws Exception {
        // when
        ResultActions result = mockMvc.perform(post("/api/v1/auth/logout"));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("로그아웃 성공"));
    }
}
