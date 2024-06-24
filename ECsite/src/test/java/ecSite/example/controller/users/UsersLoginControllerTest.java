package ecSite.example.controller.users;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ecSite.example.model.entity.UsersEntity;
import ecSite.example.service.UsersService;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersLoginControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UsersService usersService;
	
	// usersEntityの情報を作成
	UsersEntity usersEntity = new UsersEntity("test", "test@test.com", "1234");
	
	// サービスクラスを使ったデータ作成
	@BeforeEach
	public void prepareDate() {
		// ログイン成功： メールアドレス"test@test.com", パスワード"1234"  usersEntityを返す
		when(usersService.loginCheck("test@test.com", "1234")).thenReturn(usersEntity);
		// ログイン失敗： メールアドレス"123@test.com", パスワード"1234"  null
        when(usersService.loginCheck("123@test.com", "1234")).thenReturn(null);
        // ログイン失敗： メールアドレス"test@test.com", パスワード"1234abcd"  null
        when(usersService.loginCheck("test@test.com", "1234abcd")).thenReturn(null);
        // ログイン失敗： メールアドレス"123@test.com", パスワード"1234abcd"  null
        when(usersService.loginCheck("123@test.com", "1234abcd")).thenReturn(null);
	}
	
    // ユーザーログイン画面が正常に表示できるテスト
    @Test
    public void testGetUsersLoginPage() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
        		.get("/user/login");
        mockMvc.perform(request)
                .andExpect(view().name("user/user_login.html"))
                .andExpect(model().attribute("error", false));
    }

    // 正しいメールアドレス及びパスワードでログインが成功するかのテスト
    @Test
    public void testLoginWithValidCredentials() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
        		.post("/user/login/process")
                .param("userEmail", "test@test.com")
                .param("userPassword", "1234");
        mockMvc.perform(request)
                .andExpect(redirectedUrl("/user/product/list"));
    }

    // 間違ったメールアドレスでログインが成功するかのテスト
    @Test
    public void testLoginWithInvalidEmail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
        		.post("/user/login/process")
                .param("userEmail", "123@test.com")
                .param("userPassword", "1234");
        mockMvc.perform(request)
                .andExpect(view().name("user/user_login.html"))
                .andExpect(model().attribute("error", true));
    }

    // 間違ったパスワードでログインが成功するかのテスト
    @Test
    public void testLoginWithInvalidPassword() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
        		.post("/user/login/process")
                .param("userEmail", "test@test.com")
                .param("userPassword", "1234abcd");
        mockMvc.perform(request)
                .andExpect(view().name("user/user_login.html"))
                .andExpect(model().attribute("error", true));
    }

    // 間違ったメールアドレス及びパスワードでログイン
    @Test
    public void testLoginWithInvalidEmailAndPassword() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
        		.post("/user/login/process")
                .param("userEmail", "123@test.com")
                .param("userPassword", "1234abcd");
        mockMvc.perform(request)
                .andExpect(view().name("user/user_login.html"))
                .andExpect(model().attribute("error", true));
    }

}
